package org.tiaa.warehouse;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.tiaa.rawmaterial.Bolts;
import org.tiaa.rawmaterial.Machine;
import org.tiaa.rawmaterial.RawMaterial;
import org.tiaa.rawmaterial.RawMaterialRegistry;

public class Warehouse {

	private static final Logger logger = Logger.getLogger(Warehouse.class);
	private Map<RawMaterialRegistry, Queue<RawMaterial>> inventory = new ConcurrentHashMap<>();
	private static Warehouse warehouse = new Warehouse();

	private Warehouse() {
		super();
	}

	public static Warehouse getInstance() {
		if (warehouse == null) {
			return new Warehouse();
		}
		return warehouse;
	}

	public void addInventoryItems(RawMaterial... rawMaterials) {
		for (RawMaterial rawMaterial : rawMaterials) {
			if (rawMaterial instanceof Bolts) {
				Queue<RawMaterial> bolts = inventory.get(RawMaterialRegistry.BOLTS);
				if (bolts == null) {
					bolts = new LinkedList<>();
					bolts.add(rawMaterial);
				} else {
					bolts.add(rawMaterial);
				}
				inventory.put(RawMaterialRegistry.BOLTS, bolts);
			} else if (rawMaterial instanceof Machine) {
				Queue<RawMaterial> machine = inventory.get(RawMaterialRegistry.MACHINE);
				if (machine == null) {
					machine = new LinkedList<>();
					machine.add(rawMaterial);
				} else {
					machine.add(rawMaterial);
				}
				inventory.put(RawMaterialRegistry.MACHINE, machine);
			}
		}
	}

	public void addInventory(int noOfMachines, int noOfBolts) {
		RegistryBook registryBook = RegistryBook.getInstance();
		String batch = "BATCH_" + new Date().toString();
		for (int i = 0; i < noOfBolts; i++) {
			String boltId = "Bolt_" + (i + 1);
			warehouse.addInventoryItems(new Bolts(boltId, batch));
			registryBook.register(boltId, batch, "BOLTS ....");
		}

		for (int i = 0; i < noOfMachines; i++) {
			String machineId = "Machine_" + (i + 1);
			warehouse.addInventoryItems(new Machine(machineId, batch));
			registryBook.register(machineId, batch, "Machine ....");
		}
		warehouse.showItems(RawMaterialRegistry.BOLTS, RawMaterialRegistry.MACHINE);
		registryBook.printRegistry();
	}
	
	public int getRawMaterialSize(RawMaterialRegistry registry) {
		if (registry == RawMaterialRegistry.BOLTS)
			return inventory.get(registry).size();
		else if (registry == RawMaterialRegistry.MACHINE)
			return inventory.get(registry).size();
		else
			return 0;
	}

	public Queue<RawMaterial> getRawMaterials(RawMaterialRegistry rawMaterialRegistry, int quantity) {
		RegistryBook registryBook = RegistryBook.getInstance();
		Queue<RawMaterial> items = new LinkedList<>();
		while (quantity > 0) {
			RawMaterial material = inventory.get(rawMaterialRegistry).poll();
			if (material instanceof Bolts) {

				registryBook.register(((Bolts) material).getBoltId(), ((Bolts) material).getBatch(), "Bolts ...",
						"Unavailable/Consumed");
			} else if (material instanceof Machine) {

				registryBook.register(((Machine) material).getMachineId(), ((Machine) material).getBatch(),
						"Machine ...", "Unavailable/Consumed");
			}
			items.add(material);
			quantity--;
		}
		return items;
	}

	public void showItems(RawMaterialRegistry... materialRegistries) {
		logger.debug("**************** ITEMS ****************");
		for (RawMaterialRegistry registry : materialRegistries) {
			if (registry == RawMaterialRegistry.BOLTS)
				logger.debug("BOLTS = " + inventory.get(registry).size());
			else if (registry == RawMaterialRegistry.MACHINE)
				logger.debug("MACHINES = " + inventory.get(RawMaterialRegistry.MACHINE).size());
		}
		logger.debug("**************** ITEMS ****************");
	}

}
