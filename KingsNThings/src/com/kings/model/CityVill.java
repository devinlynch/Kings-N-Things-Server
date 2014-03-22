package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;


public class CityVill extends SpecialIncomeCounter {
	private int combatValue;
	private int restoredCombatValue;
	
	public CityVill(String id, String name, int goldValue, int combatValue) {
		super(id, name, goldValue);
		this.combatValue=combatValue;
		this.restoredCombatValue=combatValue;
	}

	public int getCombatValue() {
		return combatValue;
	}

	public void setCombatValue(int combatValue) {
		this.combatValue = combatValue;
	}
	
	public void reduceLevel() {
		combatValue--;
	}
	
	public void restoreLevel() {
		this.combatValue = restoredCombatValue;
	} 

	@Override
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		return combatValue;
	}
}
