package com.kings.model;


public class CityVill extends SpecialIncomeCounter {
	private int combatValue;
	
	public CityVill(String id, String name, int goldValue, int combatValue) {
		super(id, name, goldValue);
		this.combatValue=combatValue;
	}

	public int getCombatValue() {
		return combatValue;
	}

	public void setCombatValue(int combatValue) {
		this.combatValue = combatValue;
	}
}
