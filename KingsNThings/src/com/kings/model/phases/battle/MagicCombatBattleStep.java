package com.kings.model.phases.battle;

import java.util.HashSet;
import java.util.Set;

import com.kings.model.CombatType;
import com.kings.model.Counter;
import com.kings.model.Creature;
import com.kings.model.Fort;
import com.kings.model.Player;
import com.kings.model.SpecialCharacter;

public class MagicCombatBattleStep extends CombatBattleStep {

	public MagicCombatBattleStep(CombatBattleRound round) {
		super("magicStep", round);
	}

	@Override
	public Set<Counter> getAttackerCountersOnThisLocationForStep() {
		return getMagicPiecesForPlayer(getAttacker());
	}

	@Override
	public Set<Counter> getDefenderCountersOnThisLocationForStep() {
		return getMagicPiecesForPlayer(getDefender());
	}
	
	private Set<Counter> getMagicPiecesForPlayer(Player p) {
		Set<Counter> magicCounters =  new HashSet<Counter>();
		
		Set<Counter> counters = getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(p);
		for(Counter c : counters) {
			if(c instanceof Creature) {
				Creature cre = (Creature) c;
				if(cre.getCombatType() == CombatType.MAGIC) {
					magicCounters.add(c);
				}
			} else if(c instanceof Fort) {
				if(((Fort)c).getCombatType() == CombatType.MAGIC) {
					
				}
			} else if (c instanceof SpecialCharacter) {
				if(((SpecialCharacter)c).getCombatType() == CombatType.MAGIC) {
					magicCounters.add(c);
				}
			}
		}
		
		return magicCounters;
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
