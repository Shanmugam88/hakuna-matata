package org.tiaa.supervisor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.tiaa.rawmaterial.Bolts;
import org.tiaa.rawmaterial.Machine;
import org.tiaa.rawmaterial.RawMaterial;
import org.tiaa.rawmaterial.RawMaterialRegistry;
import org.tiaa.warehouse.Product;
import org.tiaa.warehouse.RegistryBook;
import org.tiaa.warehouse.Warehouse;
import org.tiaa.worker.MachineWithBoltsTask;
import org.tiaa.worker.Worker;

public class Supervisor {

	private static final Logger logger = Logger.getLogger(Supervisor.class);
	private final int numOfEmployees;
	private ExecutorService workerPool;
	
	
	public Supervisor(int numOfEmployees) {
		super();
		this.numOfEmployees = numOfEmployees;
		workerPool = Executors.newFixedThreadPool(numOfEmployees);
	}

	public String[] manageRawMaterials(int[] args) {
		String[] output = new String[2];
		output[0] = assembleProduct(totalProduct(args[0], args[1], args[2]));
		output[1] = String.valueOf(args[2]);
		logger.info("Total Products = " + output[0]);
		logger.info("Total Time Taken = " + output[1]);
		return output;
	}

	private int totalProduct(int machines, int bolts, int timeTaken) {
		
		Warehouse warehouse = Warehouse.getInstance();
		int quantityByTime = (timeTaken/60) * numOfEmployees;
		int minMachines = Math.min(machines, warehouse.getRawMaterialSize(RawMaterialRegistry.MACHINE));
		int minBolts = Math.min(bolts, warehouse.getRawMaterialSize(RawMaterialRegistry.BOLTS));
		int quantityByCriteria = Math.min(minMachines, minBolts/2);
		return Math.min(quantityByCriteria, quantityByTime);
	}

	private Queue<MachineWithBoltsTask> workerTask(int quantity) {
		Queue<MachineWithBoltsTask> tasks = new LinkedList<>();
		Warehouse warehouse = Warehouse.getInstance();
		Queue<RawMaterial> machines = warehouse.getRawMaterials(RawMaterialRegistry.MACHINE, quantity);
		Queue<RawMaterial> bolts = warehouse.getRawMaterials(RawMaterialRegistry.BOLTS, quantity * 2);
		while (quantity > 0) {
			tasks.add(new MachineWithBoltsTask(new Bolts[] { (Bolts) bolts.poll(), (Bolts) bolts.poll() },
					(Machine) machines.poll()));
			quantity--;
		}
		return tasks;
	}

	private String assembleProduct(Integer quantity) {
		Queue<Future<Product>> products = new LinkedList<>();
		for (MachineWithBoltsTask task : workerTask(quantity)) {
			products.add(workerPool.submit(new Worker(task)));
		}
		for (Future<Product> product : products) {
			try {
				Product prd = product.get();
				logger.info(prd.toString());
			} catch (ExecutionException e) {
				logger.error(e.getMessage());
			}catch(InterruptedException e) {
				logger.error("Interrupted!", e);
			    // Restore interrupted state...
			    Thread.currentThread().interrupt();
			}
		}
		if (!workerPool.isShutdown()) {
			workerPool.shutdown();
		}
		RegistryBook.getInstance().printRegistry();
		return quantity.toString();

	}
}
