package com.kings.model.phases.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.Creature;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.Stack;
import com.kings.model.phases.CombatPhase;
import com.kings.util.Utils;

public class CombatBattle {
	public enum BattleResolution{
		RETREAT,
		BATTLE_AGAIN
	};
	
	
	private HexLocation locationOfBattle;
	private GameState gameState;
	private CombatPhase combatPhase;
	private boolean isOver;
	private Player attacker;
	private Player defender;
	private String battleId;
	
	int attackerHitCount;
	int defenderHitCount;
	
	private boolean attackerDidRoll;
	private boolean defenderDidRoll;
	
 	private boolean resolutionWasMade;
	
	public CombatBattle(HexLocation locationOfBattle, CombatPhase combatPhase) {
		this.locationOfBattle = locationOfBattle;
		this.combatPhase = combatPhase;
		this.gameState = combatPhase.getGameState();
		this.battleId = Utils.generateRandomId("battle");
		
		defender = locationOfBattle.getOwner();
		List<Player> playersOnHex = locationOfBattle.getPlayersWhoAreOnMe();
		for(Player p: playersOnHex) {
			if(!p.equals(defender)) {
				setAttacker(p);
				break;
			}
		}
	}
	
	public void start() {
		System.out.println("CombatBattle started id=["+battleId+"]");
		informAttackerAndDefenderOfBattle();
		informAttackerItsTheirTurnToFight();
		informDefenderItsTheirTurnToFight();
	}
	
	
	public void informAttackerItsTheirTurnToFight() {
		GameMessage message = new GameMessage("yourTurnToAttack");
		Map<Creature, Integer> creaturesToRolls = getCreatureToRollNumbersForAttacker();
		
		Set<Map<String,Object>> creaturesSet = new HashSet<Map<String,Object>>();
		for (Creature key : creaturesToRolls.keySet()) {
		    Integer roll = creaturesToRolls.get(key);
		    Map<String, Object> map = key.toSerializedFormat();
		    map.put("roll", roll);
		    creaturesSet.add(map);
		}
		message.addToData("creaturesToRolls", creaturesSet);
		message.addToData("attackerHitCount", attackerHitCount);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public void informDefenderItsTheirTurnToFight() {
		GameMessage message = new GameMessage("yourTurnToDefend");
		Map<Creature, Integer> creaturesToRolls = getDefendingPiecesToRollNumbersForDefender();
		
		Set<Map<String,Object>> creaturesSet = new HashSet<Map<String,Object>>();
		for (Creature key : creaturesToRolls.keySet()) {
		    Integer roll = creaturesToRolls.get(key);
		    Map<String, Object> map = key.toSerializedFormat();
		    map.put("roll", roll);
		    creaturesSet.add(map);
		}
		message.addToData("creaturesToRolls", creaturesSet);
		message.addToData("defenderHitCount", defenderHitCount);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public void informAttackerAndDefenderOfBattle() {
		GameMessage msg = getCombatPhase().newGameMessageForAllPlayers("battleStarted");
		msg.addToData("battle", this.toSerializedFormat());
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
	}
	
	protected Map<Creature, Integer> getCreatureToRollNumbersForAttacker() {
		Map<Creature, Integer> map = new HashMap<Creature, Integer>();
		Set<Creature> creatures = locationOfBattle.getCreaturePiecesForPlayerIncludingCreaturesInStack(attacker);
		
		for(Creature creature: creatures) {
			Integer randomnum = getGameState().rollDice(1);
			
			if(randomnum <= creature.getCombatValue()) {
				attackerHitCount++;
			}
			
			map.put(creature, randomnum);
		}
		return map;
	}
	
	protected Map<Creature, Integer> getDefendingPiecesToRollNumbersForDefender() {
		//TODO for it2: need to have forts, cities, etc. 
		
		Map<Creature, Integer> map = new HashMap<Creature, Integer>();
		Set<Creature> creatures = locationOfBattle.getCreaturePiecesForPlayerIncludingCreaturesInStack(defender);
		
		for(Creature creature: creatures) {
			Integer randomnum = getGameState().rollDice(1);
			
			if(randomnum <= creature.getCombatValue()) {
				defenderHitCount++;
			}
			
			map.put(creature, randomnum);
		}
		return map;
	}
	
	public synchronized void playerDidRoll(Player p) {
		if(p.getPlayerId().equals(attacker.getPlayerId())) {
			attackerDidRoll();
		} else if(p.getPlayerId().equals(defender.getPlayerId())) {
			defenderDidRoll();
		}
	}
	
	public void attackerDidRoll() {
		attackerDidRoll = true;
		handleStartResolutionIfNeeded();
	}
	
	public void defenderDidRoll() {
		defenderDidRoll = true;
		handleStartResolutionIfNeeded();
	}
	
	protected synchronized void handleStartResolutionIfNeeded() {
		if(defenderDidRoll && attackerDidRoll) {
			GameMessage msg = new GameMessage("timeForBattleResolution");
			msg.addPlayerToSendTo(attacker);
			msg.addPlayerToSendTo(defender);
			
			msg.addToData("battle", this.toSerializedFormat());
			msg.addUserSpecificData(attacker.getPlayerId(), "damageYouTook", defenderHitCount);
			msg.addUserSpecificData(defender.getPlayerId(), "damageYouTook", attackerHitCount);
			getGameState().queueUpGameMessageToSendToAllPlayers(msg);
		}
	}
	
	private boolean attackerDidTakeDamage;
	private boolean defenderDidTakeDamage;
	public synchronized void playerTookDamage(Player p, Set<String> gamePiecesTakingHits) {
		for(String gamePieceId : gamePiecesTakingHits) {
			GamePiece gp = getGameState().getGamePiece(gamePieceId);
			if(gp != null) {
				gp.neutralize();
			}
		}
		
		boolean isAttacker =false;
		if(p.getPlayerId().equals(attacker.getPlayerId())) {
			attackerDidTakeDamage = true;
			isAttacker=true;
		} else{
			defenderDidTakeDamage = true;
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
	
	public synchronized void playerMadeResolution(Player p, BattleResolution resolution) {
		if(resolutionWasMade) {
			// TODO
		}
		
		if(resolution == BattleResolution.RETREAT) {
			System.out.println("Player retreated");
			// Retreat
			if(p.getPlayerId().equals(attacker.getPlayerId())) {
				handleRetreat(attacker);
			} else if(p.getPlayerId().equals(defender.getPlayerId())) {
				handleRetreat(defender);
			}
		} else {
			// Battle again
			System.out.println("Player wants to battle again, not supported right now");
		}
		
		resolutionWasMade = true;
		isOver = true;
		getCombatPhase().handleStartNextBattle();
	}
	
	protected void handleRetreat(Player player){
		List<GamePiece> pieces = new ArrayList<GamePiece>(locationOfBattle.getPiecesForPlayer(player));
		List<Stack> stacks = new ArrayList<Stack>(locationOfBattle.getStacksForPlayer(player));
		HexLocation nearestHex = player.getNearestHexTo(locationOfBattle);
		
		Set<Map<String, Object>> retreatedPieces = new HashSet<Map<String,Object>>();
		Set<Map<String, Object>> retreatedStacks = new HashSet<Map<String,Object>>();

		for(GamePiece piece : pieces) {
			nearestHex.addGamePieceToLocation(piece);
			retreatedPieces.add(piece.toSerializedFormat());
		}
		
		for(Stack stack : stacks) {
			nearestHex.addStack(stack);
			retreatedStacks.add(stack.toSerializedFormat());
		}
		
		GameMessage msg = getCombatPhase().newGameMessageForAllPlayers("playerRetreatedFromBattle");
		msg.addToData("battle", this.toSerializedFormat());
		msg.addToData("retreatedPlayerId", player.getPlayerId());
		msg.addToData("retreatedToHexId", nearestHex.toSerializedFormat());
		msg.addToData("piecesRetreated", retreatedPieces);
		msg.addToData("stacksRetreated", retreatedStacks);
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
	}
	
	
	public HashMap<String, Object> toSerializedFormat() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("locationOfBattle", locationOfBattle.toSerializedFormat());
		map.put("attacker", attacker.toSerializedFormat());
		map.put("defender", defender.toSerializedFormat());
		map.put("isOver", isOver);
		return map;
	}
	
	public HexLocation getLocationOfBattle() {
		return locationOfBattle;
	}
	public void setLocationOfBattle(HexLocation locationOfBattle) {
		this.locationOfBattle = locationOfBattle;
	}
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	public CombatPhase getCombatPhase() {
		return combatPhase;
	}
	public void setCombatPhase(CombatPhase combatPhase) {
		this.combatPhase = combatPhase;
	}
	public boolean isOver() {
		return isOver;
	}
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}


	public Player getAttacker() {
		return attacker;
	}


	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}


	public String getBattleId() {
		return battleId;
	}


	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public Player getDefender() {
		return defender;
	}

	public void setDefender(Player defender) {
		this.defender = defender;
	}

	public int getAttackerHitCount() {
		return attackerHitCount;
	}

	public void setAttackerHitCount(int attackerHitCount) {
		this.attackerHitCount = attackerHitCount;
	}

	public int getDefenderHitCount() {
		return defenderHitCount;
	}

	public void setDefenderHitCount(int defenderHitCount) {
		this.defenderHitCount = defenderHitCount;
	}

	public boolean isAttackerDidRoll() {
		return attackerDidRoll;
	}

	public void setAttackerDidRoll(boolean attackerDidRoll) {
		this.attackerDidRoll = attackerDidRoll;
	}

	public boolean isDefenderDidRoll() {
		return defenderDidRoll;
	}

	public void setDefenderDidRoll(boolean defenderDidRoll) {
		this.defenderDidRoll = defenderDidRoll;
	}

	public boolean isResolutionWasMade() {
		return resolutionWasMade;
	}

	public void setResolutionWasMade(boolean resolutionWasMade) {
		this.resolutionWasMade = resolutionWasMade;
	}

	public boolean isAttackerDidTakeDamage() {
		return attackerDidTakeDamage;
	}

	public void setAttackerDidTakeDamage(boolean attackerDidTakeDamage) {
		this.attackerDidTakeDamage = attackerDidTakeDamage;
	}

	public boolean isDefenderDidTakeDamage() {
		return defenderDidTakeDamage;
	}

	public void setDefenderDidTakeDamage(boolean defenderDidTakeDamage) {
		this.defenderDidTakeDamage = defenderDidTakeDamage;
	}

	
}
