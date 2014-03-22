package com.kings.model.phases.battle;

import java.util.ArrayList;
import java.util.List;

import com.kings.http.GameMessage;
import com.kings.util.Utils;

public class CombatBattleRound {
	private boolean started;
	private boolean ended;
	private CombatBattle battle;
	private List<CombatBattleStep> steps;
	private String roundId;
	
	public CombatBattleRound(CombatBattle battle) {
		setBattle(battle);
		roundId = Utils.generateRandomId("battle_"+battle.getBattleId()+"_round");
		steps = new ArrayList<CombatBattleStep>();
	}
	
	public void start() {
		started=true;
		ended=false;
		
		steps.clear();
		
	}
	
	public void end() {
		ended=true;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public CombatBattle getBattle() {
		return battle;
	}

	public void setBattle(CombatBattle battle) {
		this.battle = battle;
	}

	public List<CombatBattleStep> getSteps() {
		return steps;
	}

	public void setSteps(List<CombatBattleStep> steps) {
		this.steps = steps;
	}

	public String getRoundId() {
		return roundId;
	}

	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}
	
	public GameMessage newRoundMessageForAllPlayers(String type) {
		GameMessage message = getBattle().getCombatPhase().newGameMessageForAllPlayers(type);
		message.addToData("battleId", battle.getBattleId());
		message.addToData("roundId", roundId);
		message.addToData("attackerId", battle.getAttacker().getPlayerId());
		message.addToData("defenderId", battle.getDefender().getPlayerId());
		return message;
	}
	
}
