package com.kings.model;


public class Treasure extends Thing {
	public int goldValue;

	public Treasure(String id, String name, int goldValue) {
		super(id, name);
		this.goldValue=goldValue;
	}
	
	public int getGoldValue() {
		return goldValue;
	}

	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
	}
}
