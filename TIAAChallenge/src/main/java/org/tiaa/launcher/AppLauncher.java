package org.tiaa.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.tiaa.supervisor.Supervisor;
import org.tiaa.warehouse.Warehouse;

public class AppLauncher {

	private static final Logger LOGGER = Logger.getLogger(AppLauncher.class);
	private static final Properties PROPERTIES = new Properties();
	
	public static void main(String[] args) throws Exception {
		PROPERTIES.load(new FileInputStream(new File("./src/main/resources/inventory.properties")));
		int[] input = { 4, 16, 120 };
		
		LOGGER.info("Input :");
		LOGGER.info("No of Machines = "+input[0]);
		LOGGER.info("No of Bolts    = "+input[1]);
		LOGGER.info("Time           = "+input[2]);
		Supervisor supervisor = new Supervisor(Integer.parseInt(PROPERTIES.getProperty("noOfEmployees")));
		Warehouse warehouse = Warehouse.getInstance();
		warehouse.addInventory(Integer.parseInt(PROPERTIES.getProperty("noOfMachines")), Integer.parseInt(PROPERTIES.getProperty("noOfBolts")));
		if(input[2] % 60 != 0)
		{
			LOGGER.error("Time Unit should be multiples of 60(s)");
			throw new IllegalStateException("Time Unit should be multiples of 60(s)");
		}
		supervisor.manageRawMaterials(input);
	}

}
