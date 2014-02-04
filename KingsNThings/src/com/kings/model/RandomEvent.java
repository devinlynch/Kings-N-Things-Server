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
		map.put("RandomEvent_01", new RandomEvent("RandomEvent_01", "bidJuJu"));
		map.put("RandomEvent_02", new RandomEvent("RandomEvent_02", "darkPlague"));
		map.put("RandomEvent_03", new RandomEvent("RandomEvent_03", "defection"));
		map.put("RandomEvent_04", new RandomEvent("RandomEvent_04", "goodHarvest"));
		map.put("RandomEvent_05", new RandomEvent("RandomEvent_05", "motherLode"));
		map.put("RandomEvent_06", new RandomEvent("RandomEvent_06", "teeniepox"));
		map.put("RandomEvent_07", new RandomEvent("RandomEvent_07", "terrainDisaster"));
		map.put("RandomEvent_08", new RandomEvent("RandomEvent_08", "vandals"));
		map.put("RandomEvent_09", new RandomEvent("RandomEvent_09", "weatherControl"));
		map.put("RandomEvent_10", new RandomEvent("RandomEvent_10", "willingWorkers"));
		return map;
	}

}
