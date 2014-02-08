package com.kings.model;

import java.util.HashMap;
import java.util.Map;


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
	public Map<String,Object> toSerializedFormat() {
		Map<String,Object> map = super.toSerializedFormat();
		map.put("terrainId", terrain.getId());
		return map;
	}
}
