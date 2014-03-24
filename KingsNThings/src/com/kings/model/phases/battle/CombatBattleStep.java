package com.kings.model.phases.battle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.Counter;
import com.kings.model.Creature;
import com.kings.model.Fort;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.phases.exceptions.DoesNotSupportCombatException;
import com.kings.model.phases.exceptions.NotYourTurnException;

public abstract class CombatBattleStep {
	private boolean started;
	private boolean ended;
	private CombatBattleRound round;
	private String stepName;
	
	private Map<Counter, Integer> attackingPiecesToRollNumbers;
	private Map<Counter, Integer> defendingPiecesToRollNumbers;
	private int attackerHitCount;
	private int defenderHitCount;
	
	// Once the player tells the server which pieces they want to eliminate, this is set to true
	private boolean attackerDidRoll;
	private boolean defenderDidRoll;
	private Set<String> attackerPiecesTakingHits;
	private Set<String> defenderPiecesTakingHits;
	private boolean wereHitsApplied;
	
	
	public CombatBattleStep(String stepName) {
		this.stepName=stepName;
	}
	
	public void start() {
		if(ended)
			return;
		started=true;
		handleSetPiecesToRollNumbersAndIncrementHitCounter(true);
		handleSetPiecesToRollNumbersAndIncrementHitCounter(false);

		sendStepStartedMessage();
	}
	
	protected abstract Set<Counter> getAttackerCountersOnThisLocationForStep();
	protected abstract Set<Counter> getDefenderCountersOnThisLocationForStep();
	
	protected void handleSetPiecesToRollNumbersAndIncrementHitCounter(boolean isAttacker) {
		Map<Counter, Integer> map = new HashMap<Counter, Integer>();
		Set<Counter> pieces;
		
		
		if(isAttacker)
			pieces=getAttackerCountersOnThisLocationForStep();
		else
			pieces=getDefenderCountersOnThisLocationForStep();
		
		for(Counter piece: pieces) {
			Integer randomnum = getGameState().rollDice(1);
			
			try {
				if(randomnum <= piece.getCombatValueForCombat()) {
					if(isAttacker)
						attackerHitCount++;
					else
						defenderHitCount++;
				}
			} catch (DoesNotSupportCombatException e) {
				continue;
			}
			
			map.put(piece, randomnum);
		}
		
		if(isAttacker)
			attackingPiecesToRollNumbers = map;
		else
			defendingPiecesToRollNumbers = map;
	}
		
	
	protected void sendStepStartedMessage() {
		GameMessage message = newStepGameMessageForAllPlayers("combatStepStarted");
		
		// Add attackers pieces to rolls map
		Set<Map<String,Object>> attackerPiecesSet = new HashSet<Map<String,Object>>();
		for (Counter key : attackingPiecesToRollNumbers.keySet()) {
		    Integer roll = attackingPiecesToRollNumbers.get(key);
		    Map<String, Object> map = key.toSerializedFormat();
		    map.put("roll", roll);
		    attackerPiecesSet.add(map);
		}
		message.addToData("attackerGamePiecesToRolls", attackerPiecesSet);
		message.addToData("attackerDamageablePieces", getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(getAttacker()));
		
		// Add defender pieces to rolls map
		Set<Map<String,Object>> defenderPiecesSet = new HashSet<Map<String,Object>>();
		for (Counter key : defendingPiecesToRollNumbers.keySet()) {
		    Integer roll = defendingPiecesToRollNumbers.get(key);
		    Map<String, Object> map = key.toSerializedFormat();
		    map.put("roll", roll);
		    defenderPiecesSet.add(map);
		}
		message.addToData("defenderGamePiecesToRolls", defenderPiecesSet);
		message.addToData("defenderDamageablePieces", getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(getDefender()));
		
		
		message.addToData("attackerHitCount", attackerHitCount);
		message.addToData("definderHitCount", defenderHitCount);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public synchronized void playerLockedInRollAndDamage(Player p, Set<String> gamePiecesTakingHits) throws NotYourTurnException {
		if(p.getPlayerId().equals(getAttacker().getPlayerId())) {
			attackerDidRoll=true;
			attackerPiecesTakingHits=gamePiecesTakingHits;
		} else if(p.getPlayerId().equals(getDefender().getPlayerId())) {
			defenderDidRoll=true;
			defenderPiecesTakingHits=gamePiecesTakingHits;
		} else{
			throw new NotYourTurnException();
		}
		
		handleExecuteDamageTakenIfNeeded();
	}
	
	public synchronized void handleExecuteDamageTakenIfNeeded() {
		if(wereHitsApplied)
			return;
		
		if(didAttackerAndDefenderRoll()) {
			playerDidRollAndTookDamage(getAttacker(), attackerPiecesTakingHits);
			playerDidRollAndTookDamage(getDefender(), defenderPiecesTakingHits);
			wereHitsApplied=true;
		}
		
		if(getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(getAttacker()).size() <= 0
				|| getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(getDefender()).size()<=0){
			round.handlePlayerWon();
			return;
		}
		
		end();
	}
	
	protected void playerDidRollAndTookDamage(Player p, Set<String> gamePiecesTakingHits) {
		boolean isAttacker = false;
		if(p.getPlayerId().equals(getAttacker().getPlayerId())) {
			isAttacker = true;
		} else if(p.getPlayerId().equals(getDefender().getPlayerId())) {
			isAttacker = false;
		}
		
		int numHitsLeftToBeTaken;
		if(isAttacker)
			numHitsLeftToBeTaken = attackerHitCount;
		else
			numHitsLeftToBeTaken = defenderHitCount;
		
		for(String gamePieceId : gamePiecesTakingHits) {
			GamePiece gp = getGameState().getGamePiece(gamePieceId);
			if(gp != null && gp instanceof Counter) {
				getGameState().gamePieceTookDamage((Counter)gp);
				numHitsLeftToBeTaken--;
			}
		}
		
		
		if(numHitsLeftToBeTaken > 0) {
			Set<Counter> countersLeft;
			if(isAttacker)
				countersLeft = getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(getAttacker());
			else
				countersLeft = getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(getDefender());
			
			for(Counter c : countersLeft) {
				getGameState().gamePieceTookDamage(c);
				numHitsLeftToBeTaken--;
				gamePiecesTakingHits.add(c.getId());
				
				if(numHitsLeftToBeTaken <= 0)
					break;
			}
		}
		
		GameMessage msg = newStepGameMessageForAllPlayers("playerTookDamageInBattle");
		msg.addToData("damage", isAttacker ? attackerHitCount : defenderHitCount);
		msg.addToData("playerId", p.getPlayerId());
		msg.addToData("gamePiecesTakingHitsIds", gamePiecesTakingHits);
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
	}
	
	public synchronized boolean didAttackerAndDefenderRoll() {
		return attackerDidRoll && defenderDidRoll;
	}
	
	public GameMessage newStepGameMessageForAllPlayers(String type) {
		GameMessage msg = round.newRoundMessageForAllPlayers(type);
		msg.addToData("stepName", getStepName());
		return msg;
	}
	
	public void end() {
		ended=true;
		round.handleNextStepIfNeeded();
	}
	
	protected abstract void handleStart();
	protected abstract void handleEnd();

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

	public CombatBattleRound getRound() {
		return round;
	}

	public void setRound(CombatBattleRound round) {
		this.round = round;
	}
	
	public HexLocation getLocationOfBattle() {
		return getRound().getBattle().getLocationOfBattle();
	}
	
	public Player getAttacker() {
		return getRound().getBattle().getAttacker();
	}
	
	public Player getDefender() {
		return getRound().getBattle().getDefender();
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public GameState getGameState() {
		return round.getBattle().getGameState();
	}
}
