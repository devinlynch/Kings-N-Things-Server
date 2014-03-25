package com.kings.model.phases.battle;

import java.util.HashSet;
import java.util.Set;

import com.kings.model.CityVill;
import com.kings.model.CombatType;
import com.kings.model.Counter;
import com.kings.model.Creature;
import com.kings.model.Fort;
import com.kings.model.Player;
import com.kings.model.SpecialCharacter;

public class MeleeCombatBattleStep extends CombatBattleStep {

	public MeleeCombatBattleStep(CombatBattleRound round) {
		super("meleeStep", round);
	}

	@Override
	public Set<Counter> getAttackerCountersOnThisLocationForStep() {
		return getMeleePiecesForPlayer(getAttacker());
	}

	@Override
	public Set<Counter> getDefenderCountersOnThisLocationForStep() {
		return getMeleePiecesForPlayer(getDefender());
	}
	
	private Set<Counter> getMeleePiecesForPlayer(Player p) {
		Set<Counter> meleeCounters =  new HashSet<Counter>();
		
		Set<Counter> counters = getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(p);
		for(Counter c : counters) {
			if(c instanceof Creature) {
				Creature cre = (Creature) c;
				if(cre.getCombatType() == CombatType.MELEE) {
					meleeCounters.add(c);
				}
			} else if(c instanceof Fort) {
				if(((Fort)c).getCombatType() == CombatType.MELEE) {
					
				}
			} else if (c instanceof SpecialCharacter) {
				if(((SpecialCharacter)c).getCombatType() == CombatType.MELEE) {
					meleeCounters.add(c);
				}
			} else if(c instanceof CityVill) {
				meleeCounters.add(c);
			}
		}
		
		return meleeCounters;
	}

	@Override
	public void handleStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEnd() {
		// TODO Auto-generated method stub

	}

}
