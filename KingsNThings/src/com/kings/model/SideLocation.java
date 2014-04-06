package com.kings.model;

import java.util.HashSet;
import java.util.Set;

public class SideLocation extends BoardLocation {

	public SideLocation(String id, String name) {
		super(id, name);
	}
	
	public SpecialCharacter getAnySpecialCharacter() {
		for(SpecialCharacter sc : getSpecialCharacters())
			return sc;
		return null;
	}
	
	public Set<HexTile> getHexTiles() {
		Set<HexTile> things = new HashSet<HexTile>();
		for(GamePiece p: getGamePieces()) {
			if(p instanceof HexTile) {
				things.add((HexTile)p);
			}
		}
		return things;
	}

}
