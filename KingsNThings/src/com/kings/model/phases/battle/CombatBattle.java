package com.kings.model.phases.battle;

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
import com.kings.model.phases.CombatPhase;

public class CombatBattle {
	private HexLocation locationOfBattle;
	private GameState gameState;
	private CombatPhase combatPhase;
	private boolean isOver;
	private Player attacker;
	private Player defender;
	
	int attackerHitCount;
	int defenderHitCount;
	
	public CombatBattle(HexLocation locationOfBattle, CombatPhase combatPhase) {
		this.locationOfBattle = locationOfBattle;
		this.combatPhase = combatPhase;
		this.gameState = combatPhase.getGameState();
		
		defender = locationOfBattle.getOwner();
		List<Player> playersOnHex = locationOfBattle.getPlayersWhoAreOnMe();
		for(Player p: playersOnHex) {
			if(!p.equals(defender)) {
				setAttacker(p);
				break;
			}
		}
		
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
	
	public Map<Creature, Integer> getCreatureToRollNumbersForAttacker() {
		Map<Creature, Integer> map = new HashMap<Creature, Integer>();
		Set<Creature> creatures = locationOfBattle.getCreaturePiecesForPlayer(attacker);
		
		for(Creature creature: creatures) {
			Random r = new Random();
			Integer randomnum = r.nextInt(6-1) + 1;
			
			if(randomnum >= creature.getCombatValue()) {
				attackerHitCount++;
			}
			
			map.put(creature, randomnum);
		}
		return map;
	}
	
	public Map<Creature, Integer> getDefendingPiecesToRollNumbersForDefender() {
		//TODO for it2: need to have forts, cities, etc. 
		
		Map<Creature, Integer> map = new HashMap<Creature, Integer>();
		Set<Creature> creatures = locationOfBattle.getCreaturePiecesForPlayer(defender);
		
		for(Creature creature: creatures) {
			Random r = new Random();
			Integer randomnum = r.nextInt(6-1) + 1;
			
			if(randomnum >= creature.getCombatValue()) {
				defenderHitCount++;
			}
			
			map.put(creature, randomnum);
		}
		return map;
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
}
