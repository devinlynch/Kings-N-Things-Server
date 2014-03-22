package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;


public class Fort extends Counter {
	private int actualLevelNumWhenRestored;
	private int levelNum;
	private int cost;
	private CombatType combatType;
	
	public Fort(String id, String name, int levelNum, int cost, CombatType combatType) {
		super(id, name);
		this.levelNum=levelNum;
		this.actualLevelNumWhenRestored=levelNum;
		this.combatType=combatType;
		this.cost=cost;
	}
	
	public int getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public CombatType getCombatType() {
		return combatType;
	}
	public void setCombatType(CombatType combatType) {
		this.combatType = combatType;
	}
	
	public boolean isTower(){
		return getName().equalsIgnoreCase("Tower");
	}
	public boolean isKeep(){
		return getName().equalsIgnoreCase("Keep");
	}
	public boolean isCastle(){
		return getName().equalsIgnoreCase("Castle");
	}
	public boolean isCitadel(){
		return getName().equalsIgnoreCase("Citadel");
	}
	
	public void reduceLevel() {
		levelNum--;
	}
	
	public void restoreLevel() {
		this.levelNum = actualLevelNumWhenRestored;
	}

	@Override
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		return levelNum;
	}
}
