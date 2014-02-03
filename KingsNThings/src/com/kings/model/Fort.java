package com.kings.model;

import java.util.HashMap;

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
	
	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO Auto-generated method stub
		//To get a citadel it costs 5 gold but you must have a castle and you have more than 20 gold
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("Tower", new Fort("Fort_01-01", "Fort",1,5,CombatType.MELEE));
		map.put("Tower2", new Fort("Fort_01-02", "Fort",1,5,CombatType.MELEE));
		map.put("Tower3", new Fort("Fort_01-03", "Fort",1,5,CombatType.MELEE));
		map.put("Tower4", new Fort("Fort_01-04", "Fort",1,5,CombatType.MELEE));
		map.put("Keep", new Fort("Fort_02-01", "Fort",2,5,CombatType.MELEE));
		map.put("Keep2", new Fort("Fort_02-02", "Fort",2,5,CombatType.MELEE));
		map.put("Keep3", new Fort("Fort_02-03", "Fort",2,5,CombatType.MELEE));
		map.put("Keep4", new Fort("Fort_02-04", "Fort",2,5,CombatType.MELEE));
		map.put("Castle", new Fort("Fort_03-01", "Fort",3,5,CombatType.RANGE));
		map.put("Castle2", new Fort("Fort_03-02", "Fort",3,5,CombatType.RANGE));
		map.put("Castle3", new Fort("Fort_03-03", "Fort",3,5,CombatType.RANGE));
		map.put("Castle4", new Fort("Fort_03-04", "Fort",3,5,CombatType.RANGE));
		map.put("Citadel", new Fort("Fort_04-01", "Fort",4,5,CombatType.MAGIC));
		map.put("Citadel2", new Fort("Fort_04-02", "Fort",4,5,CombatType.MAGIC));
		map.put("Citadel3", new Fort("Fort_04-03", "Fort",4,5,CombatType.MAGIC));
		map.put("Citadel4", new Fort("Fort_04-04", "Fort",4,5,CombatType.MAGIC));
		
		return null;
	}
}
