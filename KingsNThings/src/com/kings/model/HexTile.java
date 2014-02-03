package com.kings.model;

import java.util.HashMap;

public class HexTile extends GamePiece {
	private Terrain terrain;
	
	public HexTile(String id, String name, Terrain terrain) {
		super(id, name);
		this.terrain=terrain;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		return null;
	}
}
