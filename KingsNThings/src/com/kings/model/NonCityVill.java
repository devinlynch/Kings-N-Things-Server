package com.kings.model;

import java.util.HashMap;

public class NonCityVill extends SpecialIncomeCounter {
	private Terrain terrainType;

	public NonCityVill(String id, String name, int goldValue, Terrain terrainType) {
		super(id, name, goldValue);
		this.terrainType=terrainType;
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
		map.put("timberland", new NonCityVill("specialIncomeCounter_01", "timberland",1,Terrain.FOREST_TERRAIN));//must have forest to get gold logic later added
		map.put("oilField", new NonCityVill("specialIncomeCounter_02", "oilField",3,Terrain.FROZEN_TERRAIN)); //only if you have frozen waste to get gold
		map.put("peatBog", new NonCityVill("specialIncomeCounter_03", "peatBog",1,Terrain.SWAMP_TERRAIN)); //swamp only
		map.put("farmlands", new NonCityVill("specialIncomeCounter_04", "farmlands",1,Terrain.PLAINS_TERRAIN)); //plains only
		map.put("goldMine", new NonCityVill("specialIncomeCounter_05", "goldMine",3,Terrain.MOUNTAIN_TERRAIN)); //mountain only
		map.put("elephantGraveyard", new NonCityVill("specialIncomeCounter_06", "elephantGraveyard",3,Terrain.JUNGLE_TERRAIN)); //jungle  only
		map.put("diamondField", new NonCityVill("specialIncomeCounter_07", "diamondField",1,Terrain.DESERT_TERRAIN)); //Desert Only
		map.put("copperMine", new NonCityVill("specialIncomeCounter_08", "copperMine",1,Terrain.MOUNTAIN_TERRAIN)); //Mountain Only
		
		return map;
	}
}
