package com.kings.model;


public class Fort extends Counter {
	private int levelNum;
	private int cost;
	private CombatType combatType;
	
	public Fort(String id, String name, int levelNum, int cost, CombatType combatType) {
		super(id, name);
		this.levelNum=levelNum;
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
	
}
