package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;


public class Treasure extends Thing {
	public int goldValue;

	public Treasure(String id, String name, int goldValue) {
		super(id, name);
		this.goldValue=goldValue;
	}
	
	public int getGoldValue() {
		return goldValue;
	}

	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
	}

	@Override
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		throw new DoesNotSupportCombatException();
	}
}
