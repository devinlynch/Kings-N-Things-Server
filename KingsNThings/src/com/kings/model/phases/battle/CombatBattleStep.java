package com.kings.model.phases.battle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.Creature;
import com.kings.model.Fort;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.phases.exceptions.NotYourTurnException;

public abstract class CombatBattleStep {
	private boolean started;
	private boolean ended;
	private CombatBattleRound round;
	private String stepName;
	
	private Map<GamePiece, Integer> attackingPiecesToRollNumbers;
	private Map<GamePiece, Integer> defendingPiecesToRollNumbers;
	private int attackerHitCount;
	private int defenderHitCount;

	
	public CombatBattleStep(String stepName) {
		this.stepName=stepName;
	}
	
	public void start() {
		if(ended)
			return;
		started=true;
		handleSetDefendingPiecesToRollNumbers();
		handleSetAttackingPiecesToRollNumbers();
		
		sendStepStartedMessage();
	}
	
	public Map<GamePiece, Integer> handleSetDefendingPiecesToRollNumbers() {
		Map<GamePiece, Integer> map = new HashMap<Creature, Integer>();
		Set<GamePiece> pieces = locationOfBattle.getCreaturePiecesForPlayerIncludingCreaturesInStack(defender);
		
		for(GamePiece piece: pieces) {
			Integer randomnum = getGameState().rollDice(1);
			
			if(randomnum <= piece.getCombatValue()) {
				defenderHitCount++;
			}
			
			map.put(piece, randomnum);
		}
		return map;
	}
	
	
	public abstract Map<GamePiece, Integer> handleSetAttackingPiecesToRollNumbers();
	
	
	public void sendStepStartedMessage() {
		GameMessage message = newStepGameMessageForAllPlayers("combatStepStarted");
		
		// Add attackers pieces to rolls map
		Set<Map<String,Object>> attackerPiecesSet = new HashSet<Map<String,Object>>();
		for (GamePiece key : attackingPiecesToRollNumbers.keySet()) {
		    Integer roll = attackingPiecesToRollNumbers.get(key);
		    Map<String, Object> map = key.toSerializedFormat();
		    map.put("roll", roll);
		    attackerPiecesSet.add(map);
		}
		message.addToData("attackerGamePiecesToRolls", attackerPiecesSet);
		
		// Add defender pieces to rolls map
		Set<Map<String,Object>> defenderPiecesSet = new HashSet<Map<String,Object>>();
		for (GamePiece key : defendingPiecesToRollNumbers.keySet()) {
		    Integer roll = defendingPiecesToRollNumbers.get(key);
		    Map<String, Object> map = key.toSerializedFormat();
		    map.put("roll", roll);
		    defenderPiecesSet.add(map);
		}
		message.addToData("defenderGamePiecesToRolls", defenderPiecesSet);
				
		
		message.addToData("attackerHitCount", attackerHitCount);
		message.addToData("definderHitCount", defenderHitCount);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public synchronized void playerDidRollAndTookDamage(Player p, Set<String> gamePiecesTakingHits) {
		boolean isAttacker = false;
		if(p.getPlayerId().equals(getAttacker().getPlayerId())) {
			isAttacker = true;
		} else if(p.getPlayerId().equals(getDefender().getPlayerId())) {
			isAttacker = false;
		} else{
			throw new NotYourTurnException();
		}
		
		int numHitsLeftToBeTaken;
		if(isAttacker)
			numHitsLeftToBeTaken = attackerHitCount;
		else
			numHitsLeftToBeTaken = defenderHitCount;
		
		for(String gamePieceId : gamePiecesTakingHits) {
			GamePiece gp = getGameState().getGamePiece(gamePieceId);
			if(gp != null) {
				getGameState().gamePieceTookDamage(gp);
				numHitsLeftToBeTaken--;
			}
		}
		
		
		

		GameMessage msg = getCombatPhase().newGameMessageForAllPlayers("playerTookDamageInBattle");
		msg.addToData("damage", isAttacker ? attackerHitCount : defenderHitCount);
		msg.addToData("playerId", p.getPlayerId());
		msg.addToData("neutralizedPiecesIds", gamePiecesTakingHits);
		msg.addToData("battle", this.toSerializedFormat());
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
		
		
		if(attackerDidTakeDamage && defenderDidTakeDamage) {
			GameMessage msg2 = new GameMessage("timeForPostBattleResolution");
			msg2.addPlayerToSendTo(attacker);
			msg2.addPlayerToSendTo(defender);
			msg2.addToData("battle", this.toSerializedFormat());
			getGameState().queueUpGameMessageToSendToAllPlayers(msg2);
		}
	}
	
	public GameMessage newStepGameMessageForAllPlayers(String type) {
		GameMessage msg = round.newRoundMessageForAllPlayers(type);
		msg.addToData("stepName", getStepName());
		return msg;
	}
	
	public void end() {
		ended=true;
	}
	
	public abstract void handleStart();
	public abstract void handleEnd();

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
