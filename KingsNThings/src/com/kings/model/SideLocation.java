package com.kings.model;

public class SideLocation extends BoardLocation {

	public SideLocation(String id, String name) {
		super(id, name);
	}
	
	public SpecialCharacter getAnySpecialCharacter() {
		for(SpecialCharacter sc : getSpecialCharacters())
			return sc;
		return null;
	}

}
