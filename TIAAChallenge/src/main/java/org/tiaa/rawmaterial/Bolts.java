package org.tiaa.rawmaterial;

public class Bolts implements RawMaterial {

	private String boltId;
	private String batch;

	public Bolts(String boltId, String batch) {
		super();
		this.boltId = boltId;
		this.batch = batch;
	}

	public String getBoltId() {
		return boltId;
	}

	public String getBatch() {
		return batch;
	}

}
