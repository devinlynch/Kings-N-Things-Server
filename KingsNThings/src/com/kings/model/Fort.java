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
		return actualLevelNumWhenRestored==1;
	}
	public boolean isKeep(){
		return actualLevelNumWhenRestored==2;
	}
	public boolean isCastle(){
		return actualLevelNumWhenRestored==3;
	}

	public void reduceLevel() {
		levelNum--;
	}
	
	public void restoreLevel() {
		this.levelNum = actualLevelNumWhenRestored;
	}
	
	public int getActualLevelNumWhenRestored() {
		return actualLevelNumWhenRestored;
	}

	@Override
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		return levelNum;
	}
	
	public boolean isCitadel() {
		return actualLevelNumWhenRestored == 4;
	}
}
