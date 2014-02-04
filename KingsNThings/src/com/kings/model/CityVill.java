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
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("city_01", new CityVill("city_01", "city",2,2));
		map.put("village_01", new CityVill("village_01", "village",1,1));
		
		return map;
	}
}
