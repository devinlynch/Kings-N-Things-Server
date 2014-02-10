package com.kings.model.phases.battle;

import java.util.HashMap;
import java.util.List;

import com.kings.http.GameMessage;
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
	}
	
	
	public void informAttackerItsTheirTurnToFight() {
		GameMessage message = new GameMessage("yourTurnToFight");

	}
	
	public void informAttackerAndDefenderOfBattle() {
		GameMessage msg = getCombatPhase().newGameMessageForAllPlayers("battleStarted");
		msg.addToData("battle", this.toSerializedFormat());
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
}
