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
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("desert-tile-01", new HexTile("desert-tile-01", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-02", new HexTile("desert-tile-02", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-03", new HexTile("desert-tile-03", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-04", new HexTile("desert-tile-04", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-05", new HexTile("desert-tile-05", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-06", new HexTile("desert-tile-06", "desertTile",Terrain.DESERT_TERRAIN));
		
		map.put("forest-tile-01", new HexTile("forest-tile-01", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-02", new HexTile("forest-tile-02", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-03", new HexTile("forest-tile-03", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-04", new HexTile("forest-tile-04", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-05", new HexTile("forest-tile-05", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-06", new HexTile("forest-tile-06", "forestTile",Terrain.FOREST_TERRAIN));

		map.put("frozen-tile-01", new HexTile("frozen-tile-01", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-02", new HexTile("frozen-tile-02", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-03", new HexTile("frozen-tile-03", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-04", new HexTile("frozen-tile-04", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-05", new HexTile("frozen-tile-05", "frozenTile",Terrain.FROZEN_TERRAIN));
		
		map.put("jungle-tile-01", new HexTile("jungle-tile-01", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-02", new HexTile("jungle-tile-02", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-03", new HexTile("jungle-tile-03", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-04", new HexTile("jungle-tile-04", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-05", new HexTile("jungle-tile-05", "jungleTile",Terrain.JUNGLE_TERRAIN));

		map.put("mountain-tile-01", new HexTile("mountain-tile-01", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-02", new HexTile("mountain-tile-02", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-03", new HexTile("mountain-tile-03", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-04", new HexTile("mountain-tile-04", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-05", new HexTile("mountain-tile-05", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-06", new HexTile("mountain-tile-06", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		
		map.put("plains-tile-01", new HexTile("plains-tile-01", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-02", new HexTile("plains-tile-02", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-03", new HexTile("plains-tile-03", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-04", new HexTile("plains-tile-04", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-05", new HexTile("plains-tile-05", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-06", new HexTile("plains-tile-06", "plainsTile",Terrain.PLAINS_TERRAIN));
		
		map.put("sea-tile-01", new HexTile("sea-tile-01", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-02", new HexTile("sea-tile-02", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-03", new HexTile("sea-tile-03", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-04", new HexTile("sea-tile-04", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-05", new HexTile("sea-tile-05", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-06", new HexTile("sea-tile-06", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-07", new HexTile("sea-tile-07", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-08", new HexTile("sea-tile-08", "seaTile",Terrain.SEA_TERRAIN));
		
		map.put("swamp-tile-01", new HexTile("swamp-tile-01", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-02", new HexTile("swamp-tile-02", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-03", new HexTile("swamp-tile-03", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-04", new HexTile("swamp-tile-04", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-05", new HexTile("swamp-tile-05", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-06", new HexTile("swamp-tile-06", "swampTile",Terrain.SWAMP_TERRAIN));
		
		return map;
	}
}
