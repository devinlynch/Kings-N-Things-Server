package com.kings.model;

import java.util.HashMap;

public class SpecialCharacter extends Counter {
	private SpecialCharacter otherSide;
	private SpecialAbility specialAbility;
	
	public SpecialCharacter(String id, String name, SpecialAbility specialAbility) {
		super(id, name);
		this.specialAbility=specialAbility;
	}
	
	public SpecialCharacter getOtherSide() {
		return otherSide;
	}
	public void setOtherSide(SpecialCharacter otherSide) {
		this.otherSide = otherSide;
	}
	public SpecialAbility getSpecialAbility() {
		return specialAbility;
	}
	public void setSpecialAbility(SpecialAbility specialAbility) {
		this.specialAbility = specialAbility;
	}
	
	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		return null;
	}
}
