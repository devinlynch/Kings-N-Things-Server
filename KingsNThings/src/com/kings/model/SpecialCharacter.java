package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;

public class SpecialCharacter extends Counter {
	private SpecialAbility specialAbility;
	private int combatValue;
	
	public SpecialCharacter(String id, String name, SpecialAbility specialAbility, int combatValue) {
		super(id, name);
		this.specialAbility=specialAbility;
		this.combatValue=combatValue;
	}
	
	public SpecialAbility getSpecialAbility() {
		return specialAbility;
	}
	public void setSpecialAbility(SpecialAbility specialAbility) {
		this.specialAbility = specialAbility;
	}

	public int getCombatValue() {
		return combatValue;
	}

	public void setCombatValue(int combatValue) {
		this.combatValue = combatValue;
	}

	@Override
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		return combatValue;
	}
}
