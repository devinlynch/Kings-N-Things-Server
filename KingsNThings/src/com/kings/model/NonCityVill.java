package com.kings.model;


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
}
