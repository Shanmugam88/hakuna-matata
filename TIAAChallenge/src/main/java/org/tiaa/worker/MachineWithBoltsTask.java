package org.tiaa.worker;


import org.tiaa.rawmaterial.Bolts;
import org.tiaa.rawmaterial.Machine;

public class MachineWithBoltsTask implements Task{

	private Bolts[] bolts;
	private Machine machine;
	public MachineWithBoltsTask(Bolts[] bolts, Machine machine) {
		super();
		this.bolts = bolts;
		this.machine = machine;
	}
	public Bolts[] getBolts() {
		return bolts;
	}
	public Machine getMachine() {
		return machine;
	}
}
