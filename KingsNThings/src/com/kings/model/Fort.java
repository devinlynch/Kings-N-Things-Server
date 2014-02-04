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
		//To get a citadel it costs 5 gold but you must have a castle and you have more than 20 gold
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("Fort_01-01", new Fort("Fort_01-01", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-02", new Fort("Fort_01-02", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-03", new Fort("Fort_01-03", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-04", new Fort("Fort_01-04", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-05", new Fort("Fort_01-05", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-06", new Fort("Fort_01-06", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-07", new Fort("Fort_01-07", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-08", new Fort("Fort_01-08", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-09", new Fort("Fort_01-09", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-10", new Fort("Fort_01-10", "Fort",1,5,CombatType.MELEE));

		map.put("Fort_02-01", new Fort("Fort_02-01", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-02", new Fort("Fort_02-02", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-03", new Fort("Fort_02-03", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-04", new Fort("Fort_02-04", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-05", new Fort("Fort_02-05", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-06", new Fort("Fort_02-06", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-07", new Fort("Fort_02-07", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-08", new Fort("Fort_02-08", "Fort",2,5,CombatType.MELEE));
		
		map.put("Fort_03-01", new Fort("Fort_03-01", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-02", new Fort("Fort_03-02", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-03", new Fort("Fort_03-03", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-04", new Fort("Fort_03-04", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-05", new Fort("Fort_03-05", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-06", new Fort("Fort_03-06", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-07", new Fort("Fort_03-07", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-08", new Fort("Fort_03-08", "Fort",3,5,CombatType.RANGE));
		
		map.put("Fort_04-01", new Fort("Fort_04-01", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-02", new Fort("Fort_04-02", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-03", new Fort("Fort_04-03", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-04", new Fort("Fort_04-04", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-05", new Fort("Fort_04-05", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-06", new Fort("Fort_04-06", "Fort",4,5,CombatType.MAGIC));
		return map;
	}
}
