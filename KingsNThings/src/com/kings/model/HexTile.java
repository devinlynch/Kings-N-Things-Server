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
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("desertTile", new HexTile("desert-tile", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("forestTile", new HexTile("forest-tile", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("frozenTile", new HexTile("frozen-tile", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("jungleTile", new HexTile("jungle-tile", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("mountainTile", new HexTile("mountain-tile", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("plainsTile", new HexTile("plains-tile", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("seaTile", new HexTile("sea-tile", "seaTile",Terrain.SEA_TERRAIN));
		map.put("swampTile", new HexTile("swamp-tile", "swampTile",Terrain.SWAMP_TERRAIN));
		return null;
	}
}
