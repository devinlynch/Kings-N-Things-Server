package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;

public abstract class Counter extends GamePiece {

	public Counter(String id, String name) {
		super(id, name);
	}
	
	public abstract int getCombatValueForCombat() throws DoesNotSupportCombatException;
}
