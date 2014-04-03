package com.kings.model.phases.battle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kings.http.GameMessage;
import com.kings.model.AIPlayer;
import com.kings.model.CityVill;
import com.kings.model.Fort;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.phases.CombatPhase;
import com.kings.model.phases.battle.CombatBattleRound.PostBattlePieceStatus;
import com.kings.util.Utils;

public class CombatBattle {
	public enum BattleResolution{
		IN_PROGRESS,
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
	private boolean started;
	private BattleResolution resolution;
	private boolean isAIDefender;
	
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
		
		if(defender == null) {
			isAIDefender = true;
			defender = new AIPlayer(null, gameState, "ai");
		}
		
		resolution = BattleResolution.IN_PROGRESS;
		round = new CombatBattleRound(this);
	}
	
	public void start() {
		setStarted(true);
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
		System.out.println("Combat Battle BattleId=["+getBattleId()+"] ended with resolution " + resolution.toString() + " and winner playerid="+winner.getPlayerId());
		
		Map<String, PostBattlePieceStatus> piecesStatuses =  new HashMap<String, CombatBattleRound.PostBattlePieceStatus>();
		Iterator<GamePiece> it = getLocationOfBattle().getAllPiecesOnHexIncludingPiecesInStacks().iterator();
		while(it.hasNext()) {
			GamePiece piece = it.next();
			
			// For forts, city vills and other special income counters, see if damage took place.  If so, roll a die for the piece.
			// A roll of 1 or 6 destroys the piece or if its a fort its reduced 1 level, otherwise damage from battle is restored
			// (citadels are never reduced or destoroyed)
			if(piece instanceof Fort) {
				Fort fort = (Fort) piece;
				int actualLevel = fort.getActualLevelNumWhenRestored();
				int levelNow = fort.getLevelNum();
				
				if(levelNow < actualLevel) {
					// Damage took place
					int randomRoll = getGameState().rollDice(6);
					
					fort.reduceLevel();
					if((randomRoll == 1 || randomRoll == 6) && actualLevel != 4) {
						fort.setLevelNum(actualLevel-1);
						piecesStatuses.put(fort.getId(), PostBattlePieceStatus.REDUCED_LEVEL);
					} else{
						piecesStatuses.put(fort.getId(), PostBattlePieceStatus.RESTORED);
					}
				}
			} else if (piece instanceof CityVill) {
				int randomRoll = getGameState().rollDice(6);
				
				if(randomRoll == 1 || randomRoll == 6) {
					piecesStatuses.put(piece.getId(), PostBattlePieceStatus.DESTROYED);
					getGameState().getPlayingCup().addGamePieceToLocation(piece);
					continue;
				} else{
					piecesStatuses.put(piece.getId(), PostBattlePieceStatus.RESTORED);
				}
			}
			
			
			// Assign all pieces from opposing player to the winner
			if(piece.getOwner() != null && ! piece.getOwner().getPlayerId().equals(winner.getPlayerId()))
				winner.assignGamePieceToPlayer(piece);
		}
		
		
		
		
		this.isOver = true;
		GameMessage msg = getCombatPhase().newGameMessageForAllPlayers("battleOver");
		msg.addToData("battle", this.toSerializedFormat());
		msg.addToData("winnerId", winner.getPlayerId());
		msg.addToData("resolution", resolution.toString());
		msg.addToData("statusOfLeftoverPieces", piecesStatuses);
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
		
		this.resolution = resolution;
		
		getCombatPhase().handleStartNextBattle();
	}
	
	public HashMap<String, Object> toSerializedFormat() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("locationOfBattleId", locationOfBattle.getId());
		map.put("attacker", attacker.toSerializedFormat());
		map.put("defender", defender.toSerializedFormat());
		map.put("isOver", isOver);
		map.put("isStarted", isStarted());
		map.put("hexLocations", getGameState().getHexLocationsInSerializedFormat());
		map.put("sideLocation", getGameState().getSideLocation().toSerializedFormat());
		map.put("playingCup", getGameState().getPlayingCup().toSerializedFormat());
		map.put("round", getRound().toSerializedFormat());
		map.put("battleId", getBattleId());
		map.put("resolution", resolution.toString());
		map.put("isAIDefender", isAIDefender);
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

	public CombatBattleRound getRound() {
		return round;
	}

	public void setRound(CombatBattleRound round) {
		this.round = round;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isAIDefender() {
		return isAIDefender;
	}

	public void setAIDefender(boolean isAIDefender) {
		this.isAIDefender = isAIDefender;
	}
	
	
}
