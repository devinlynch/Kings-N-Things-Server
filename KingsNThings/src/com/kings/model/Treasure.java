package com.kings.model;

import java.util.HashMap;

public class Treasure extends Thing {
	public int goldValue;

	public Treasure(String id, String name, int goldValue) {
		super(id, name);
		this.goldValue=goldValue;
	}
	
	public int getGoldValue() {
		return goldValue;
	}

	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
	}

	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("treasureChest", new Treasure("treasure_01-01", "treasureChest",20));
		map.put("ruby", new Treasure("treasure_02-1", "ruby",10));
		map.put("sapphire", new Treasure("treasure_03-01", "sapphire",5));
		map.put("sliverMine", new Treasure("treasure_04-01", "sliverMine",2));
		map.put("sliverMine2", new Treasure("treasure_04-02", "sliverMine2",2));
		map.put("pearl", new Treasure("treasure_06-01", "pearl",5));
		map.put("emerald", new Treasure("treasure_07-01", "emerald",10)); 
		map.put("diamond", new Treasure("treasure_08-01", "diamond",5)); 
		return null;
	}
}
