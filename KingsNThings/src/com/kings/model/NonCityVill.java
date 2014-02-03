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
		return null;
	}
}
