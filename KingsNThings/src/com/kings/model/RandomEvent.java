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
		map.put("bidJuJu", new RandomEvent("RandomEvent_01", "bidJuJu"));
		map.put("darkPlague", new RandomEvent("RandomEvent_02", "darkPlague"));
		map.put("defection", new RandomEvent("RandomEvent_03", "defection"));
		map.put("goodHarvest", new RandomEvent("RandomEvent_04", "goodHarvest"));
		map.put("motherLode", new RandomEvent("RandomEvent_05", "motherLode"));
		map.put("teeniepox", new RandomEvent("RandomEvent_06", "teeniepox"));
		map.put("terrainDisaster", new RandomEvent("RandomEvent_07", "terrainDisaster"));
		map.put("vandals", new RandomEvent("RandomEvent_08", "vandals"));
		map.put("weatherControl", new RandomEvent("RandomEvent_09", "weatherControl"));
		map.put("willingWorkers", new RandomEvent("RandomEvent_10", "willingWorkers"));
		return map;
	}

}
