package com.kings.model;

import java.util.HashMap;

public class Creature extends Thing {
	private int combatValue;
	private CombatType combatType;
	private Terrain terrainType;
	
	public Creature(String id, String name, Terrain terrainType) {
		super(id, name);
		this.terrainType=terrainType;
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

	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		
		//EXAMPLE FOR GABE:
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("c01", new Creature("c01", "Test Creature", Terrain.JUNGLE_TERRAIN));
		
		return map;
	}
}
