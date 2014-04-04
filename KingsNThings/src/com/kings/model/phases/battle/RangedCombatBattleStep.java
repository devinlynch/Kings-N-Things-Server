package com.kings.model.phases.battle;

import java.util.HashSet;
import java.util.Set;

import com.kings.model.CombatType;
import com.kings.model.Counter;
import com.kings.model.Creature;
import com.kings.model.Fort;
import com.kings.model.Player;
import com.kings.model.SpecialCharacter;
import com.kings.model.phases.battle.CombatBattleRound.BattleRoundState;

public class RangedCombatBattleStep extends CombatBattleStep {

	public RangedCombatBattleStep(CombatBattleRound round) {
		super("rangedStep", round);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<Counter> getAttackerCountersOnThisLocationForStep() {
		return getRangedPiecesForPlayer(getAttacker());
	}

	@Override
	public Set<Counter> getDefenderCountersOnThisLocationForStep() {
		return getRangedPiecesForPlayer(getDefender());
	}
	
	private Set<Counter> getRangedPiecesForPlayer(Player p) {
		Set<Counter> rangedCounters =  new HashSet<Counter>();
		
		Set<Counter> counters = getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(p);
		for(Counter c : counters) {
			if(c instanceof Creature) {
				Creature cre = (Creature) c;
				if(cre.getCombatType() == CombatType.RANGE) {
					rangedCounters.add(c);
				}
			} else if(c instanceof Fort) {
				if(((Fort)c).getCombatType() == CombatType.RANGE) {
					rangedCounters.add(c);
				}
			} else if (c instanceof SpecialCharacter) {
				if(((SpecialCharacter)c).getCombatType() == CombatType.RANGE) {
					rangedCounters.add(c);
				}
			}
		}
		
		return rangedCounters;
	}

	@Override
	public void handleStart() {
		getRound().setState(BattleRoundState.RANGE_STEP);

	}

	@Override
	public void handleEnd() {
		// TODO Auto-generated method stub

	}

}
