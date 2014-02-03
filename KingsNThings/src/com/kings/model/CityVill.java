package com.kings.model;

import java.util.HashMap;

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
	
	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		return null;
	}
}
