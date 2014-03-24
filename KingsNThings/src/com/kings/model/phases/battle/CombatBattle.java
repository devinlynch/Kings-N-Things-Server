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
		ATTACKER_RETREATED,
		DEFENDER_REREATED,
		ATTACKER_WON,
		DEFENDER_WON
	};
	
	
	private HexLocation locationOfBattle;
	private GameState gameState;
	private CombatPhase combatPhase;
	private boolean isOver;
	private Player attacker;
	private Player defender;
	private String battleId;
	private CombatBattleRound round;
	
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
		
		round = new CombatBattleRound(this);
	}
	
	public void start() {
		System.out.println("CombatBattle started id=["+battleId+"]");
		informAttackerAndDefenderOfBattle();
		
		round.start();
	}
	
	public void startNewRound() {
		round = new CombatBattleRound(this);
		round.start();
	}
	
	public void informAttackerAndDefenderOfBattle() {
		GameMessage msg = getCombatPhase().newGameMessageForAllPlayers("battleStarted");
		msg.addToData("battle", this.toSerializedFormat());
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
	}
	
	public void battleDidFinish(Player winner, BattleResolution resolution) {
		this.isOver = true;
		
		GameMessage msg = getCombatPhase().newGameMessageForAllPlayers("battleOver");
		msg.addToData("battle", this.toSerializedFormat());
		msg.addToData("winnerId", winner.getPlayerId());
		msg.addToData("resolution", resolution.toString());
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
		
		getCombatPhase().handleStartNextBattle();
	}
	
	public HashMap<String, Object> toSerializedFormat() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("locationOfBattle", locationOfBattle.toSerializedFormat());
		map.put("attacker", attacker.toSerializedFormat());
		map.put("defender", defender.toSerializedFormat());
		map.put("isOver", isOver);
		map.put("hexLocations", getGameState().getHexLocationsInSerializedFormat());
		map.put("sideLocation", getGameState().getSideLocation().toSerializedFormat());
		map.put("playingCup", getGameState().getPlayingCup().toSerializedFormat());
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
}
