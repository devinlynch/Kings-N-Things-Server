package com.kings.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PlayingCup extends BoardLocation {

	public PlayingCup(String id) {
		super(id, "Playing Cup");
		setName("Playing Cup");
	}
	
	
	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("locationId", getId());
		Set<Map<String, Object>> pieces = new HashSet<Map<String, Object>>();
		Iterator<GamePiece> it = getGamePieces().iterator();
		while(it.hasNext()) {
			GamePiece piece = it.next();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("id", piece.getId());
			pieces.add(map2);
		}
		map.put("gamePieces", pieces);
		
		return map;
	}
	
}
