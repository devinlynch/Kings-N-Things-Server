package com.kings.model;

import java.util.HashMap;

public class SpecialIncomeCounter extends Thing {
	private Terrain terrainType;
	private int goldValue;
	
	public SpecialIncomeCounter(String id, String name, int goldValue,Terrain terrainType) {
		super(id, name);
		this.goldValue=goldValue;
		this.setTerrainType(terrainType);
	}

	public int getGoldValue() {
		return goldValue;
	}

	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
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
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("timberland", new SpecialIncomeCounter("specialIncomeCounter_01", "timberland",1,Terrain.FOREST_TERRAIN));//must have forest to get gold logic later added
		map.put("oilField", new SpecialIncomeCounter("specialIncomeCounter_02", "oilField",3,Terrain.FROZEN_TERRAIN)); //only if you have frozen waste to get gold
		map.put("peatBog", new SpecialIncomeCounter("specialIncomeCounter_03", "peatBog",1,Terrain.SWAMP_TERRAIN)); //swamp only
		map.put("farmlands", new SpecialIncomeCounter("specialIncomeCounter_04", "farmlands",1,Terrain.PLAINS_TERRAIN)); //plains only
		map.put("goldMine", new SpecialIncomeCounter("specialIncomeCounter_05", "goldMine",3,Terrain.MOUNTAIN_TERRAIN)); //mountain only
		map.put("elephantGraveyard", new SpecialIncomeCounter("specialIncomeCounter_06", "elephantGraveyard",3,Terrain.JUNGLE_TERRAIN)); //jungle  only
		map.put("diamondField", new SpecialIncomeCounter("specialIncomeCounter_07", "diamondField",1,Terrain.DESERT_TERRAIN)); //Desert Only
		map.put("copperMine", new SpecialIncomeCounter("specialIncomeCounter_08", "copperMine",1,Terrain.MOUNTAIN_TERRAIN)); //Mountain Only
		return null;
	}

	
}
