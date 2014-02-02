package com.kings.model;

import java.util.HashMap;

public class Creature extends Thing {
	private int combatValue;
	private CombatType combatType;
	private Terrain terrainType;
	
	
	public HashMap<String, Creature> getCreatureHashMap(String name) {
		HashMap<String, Creature> map = new HashMap<String, Creature>();
		
		
		//EXAMPLE NO FOR USE
		Creature c = new Creature();
		c.setName("Jungle Dinasaur");
		c.setId("t0001");
		c.setTerrainType(Terrain.JUNGLE_TERRAIN);
		c.setCombatValue(2);
		map.put(c.getId(), c);
		
		return map;
	}
	
	public int getCombatValue() {
		return combatValue;
	}
	public void setCombatValue(int combatValue) {
		this.combatValue = combatValue;
	}
	public CombatType getCombatType() {
		return combatType;
	}
	public void setCombatType(CombatType combatType) {
		this.combatType = combatType;
	}
	public Terrain getTerrainType() {
		return terrainType;
	}
	public void setTerrainType(Terrain terrainType) {
		this.terrainType = terrainType;
	}
}
