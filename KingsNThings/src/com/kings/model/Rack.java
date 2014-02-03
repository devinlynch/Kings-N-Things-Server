package com.kings.model;

public class Rack extends BoardLocation {
	//TODO: MAKE SURE CORRECT LIMIT
	public static final int LIMIT = 10;
	
	public Rack(String id) {
		super(id, "Rack");
		setName("Rack");
		this.limit=LIMIT;
	}

	public int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
