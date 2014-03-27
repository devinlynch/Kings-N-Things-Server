package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;


public class RandomEvent extends Thing {

	public RandomEvent(String id, String name) {
		super(id, name);
	}

	@Override
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		throw new DoesNotSupportCombatException();
	}
}
