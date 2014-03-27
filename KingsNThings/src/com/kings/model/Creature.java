package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;


public class Creature extends Thing {
	private int combatValue;
	private CombatType combatType;
	private Terrain terrainType;
	
	public Creature(String id, String name,CombatType combatType,int combatValue, Terrain terrainType) {
		super(id, name);
		this.combatValue=combatValue;
		this.combatType=combatType;
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
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		return combatValue;
	}
}
