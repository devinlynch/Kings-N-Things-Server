package com.kings.model;

import java.util.HashMap;

public class RandomEvent extends Thing {

	public RandomEvent(String id, String name) {
		super(id, name);
	}

	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("desertTile", new HexTile("desert-tile", "desertTile",Terrain.DESERT_TERRAIN));
		return null;
	}

}
