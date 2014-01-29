package com.kings.model;

public class SpecialCharacter extends Counter {
	private SpecialCharacter otherSide;
	private SpecialAbility specialAbility;
	
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
}
