package org.tiaa.rawmaterial;

public class Machine implements RawMaterial {

	private String machineId;
	private String batch;

	public Machine(String machineId, String batch) {
		super();
		this.machineId = machineId;
		this.batch = batch;
	}

	public String getMachineId() {
		return machineId;
	}

	public String getBatch() {
		return batch;
	}

}
