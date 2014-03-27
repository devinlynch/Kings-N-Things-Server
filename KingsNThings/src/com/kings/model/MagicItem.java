package com.kings.model;

import com.kings.model.phases.exceptions.DoesNotSupportCombatException;

public class MagicItem extends Thing {

	public MagicItem(String id, String name) {
		super(id, name);
	}

	@Override
	public int getCombatValueForCombat() throws DoesNotSupportCombatException {
		throw new DoesNotSupportCombatException();
	}
}
