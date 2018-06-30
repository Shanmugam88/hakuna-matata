package org.tiaa.worker;

import java.util.concurrent.Callable;

import org.tiaa.rawmaterial.Bolts;
import org.tiaa.rawmaterial.Machine;
import org.tiaa.warehouse.Product;

public class Worker implements Callable<Product>, IWorker {

	private Task task;

	public Worker(Task task) {
		super();
		this.task = task;
	}

	@Override
	public Product assemble() {
		Product product = null;

		if (task instanceof MachineWithBoltsTask && validateForMakingMachineWithBolts(
				((MachineWithBoltsTask) task).getBolts(), ((MachineWithBoltsTask) task).getMachine())) {
			String prodDesc = String.format("Machine { %s } is successfully assembled with bolts { %s , %s }",
					((MachineWithBoltsTask) task).getMachine().getMachineId(),
					((MachineWithBoltsTask) task).getBolts()[0].getBoltId(),
					((MachineWithBoltsTask) task).getBolts()[1].getBoltId());
			product = new Product(prodDesc, 60);
		}
		return product;
	}

	private boolean validateForMakingMachineWithBolts(Bolts[] bolts, Machine machine) {
		return (bolts.length == 2 && machine != null);
	}

	@Override
	public Product call() throws Exception {
		Product product = assemble();
		if (null == product) {
			return new Product("Machine assembling failed", 0);
		}
		return assemble();
	}
}
