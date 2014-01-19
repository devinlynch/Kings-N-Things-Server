package com.kings.model;

public class Creature extends Thing {
	private int combatValue;
	private CombatType combatType;
	private Terrain terrainType;
	
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
