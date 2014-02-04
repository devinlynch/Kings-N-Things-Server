package com.kings.model;

public abstract class SpecialIncomeCounter extends Thing {
	private int goldValue;
	
	public SpecialIncomeCounter(String id, String name, int goldValue) {
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
