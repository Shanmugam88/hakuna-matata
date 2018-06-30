package org.tiaa;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.tiaa.supervisor.Supervisor;
import org.tiaa.warehouse.Warehouse;

public class AppTest {

	private Warehouse warehouse = Warehouse.getInstance();
	
	@Before
	public void setup() {
		int noOfBolts = 7;
		int noOfMachines = 4;
		warehouse.addInventory(noOfMachines, noOfBolts);
	}

	@Test
	public void supervisortest() {
		int[] input = {4,16,60};
		Supervisor supervisor = new Supervisor(3);
		String[] output = supervisor.manageRawMaterials(input);
		assertEquals("3", output[0]);
		assertEquals("60", output[1]);
	}
	
	
	@Test
	public void rawMaterialInvalidCount() {
		assertEquals(0,warehouse.getRawMaterialSize(null));
	}
}
