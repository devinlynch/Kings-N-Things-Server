package com.kings.model;

import java.util.HashMap;

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

	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		return null;
	}
}
