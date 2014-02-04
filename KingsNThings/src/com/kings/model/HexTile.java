package com.kings.model;


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
}
