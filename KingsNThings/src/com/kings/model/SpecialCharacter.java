package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;

public class SpecialCharacter extends Counter {
	private SpecialAbility specialAbility;
	private int combatValue;
	private CombatType combatType;
	private boolean isFlyingCreature;
	private boolean canCharge;
	
	public SpecialCharacter(String id, String name, SpecialAbility specialAbility, int combatValue, CombatType combatType) {
		super(id, name);
		this.specialAbility=specialAbility;
		this.combatValue=combatValue;
		this.combatType=combatType;
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

	public CombatType getCombatType() {
		return combatType;
	}

	public void setCombatType(CombatType combatType) {
		this.combatType = combatType;
	}

	public boolean isFlyingCreature() {
		return isFlyingCreature;
	}

	public void setFlyingCreature(boolean isFlyingCreature) {
		this.isFlyingCreature = isFlyingCreature;
	}

	public boolean isCanCharge() {
		return canCharge;
	}

	public void setCanCharge(boolean canCharge) {
		this.canCharge = canCharge;
	}
}
