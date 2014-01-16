package com.kings.model;

public class Creature extends Thing {
	public int CombatValue;
	Thing id;

	public int getCombatValue() {
		return CombatValue;
	}

	public void setCombatValue(int combatValue) {
		CombatValue = combatValue;
	}
}
