package com.kings.model.phases;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.BoardLocation;
import com.kings.model.Fort;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.PlayingCup;

/*Whats supposed to happen
 * Player can skip, build or Upgrade
 * Build: Simply click build they will get a lvl 1 fort and they pick on the board where to place it
 * Upgrade: They select a fort, then depending on the level it gets upgraded one more
 * BUT if its lvl 3 then we check their gold for more than 20 than let them get a citadel
 */
public class ConstructionPhase extends Phase {
	private int nextPlayerToMove;
	boolean isTimeToMove;
	boolean playerhaswon = false;
	Set<String> playersReadyForNextPhase;

	public ConstructionPhase(GameState gameState,
			List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("construction");
		nextPlayerToMove = 0;
		playersReadyForNextPhase = new HashSet<String>();
	}

	@Override
	public void handleStart() {
		

		tellPlayerItsTheirTurnToMove();
	}

	public void tellPlayerItsTheirTurnToMove() {
		if (nextPlayerToMove > getPlayersInOrderOfTurn().size() - 1) {
			end();
			return;
		}

		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToMove);
		GameMessage message = new GameMessage("yourTurnToConstruct");
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}

	// Remove 5 Gold, unless last level for now it just gives you a fort instead
	public synchronized void didUpgradeFort(String playerId, String fortId) throws Exception {
		// Get fort,check level,upgrade to next level , remove previous,remove 5
		// gold, if lvl 3 to lvl 4 check if gold is greater 20 then upgrade
		
		Player player = getGameState().getPlayerByPlayerId(playerId);
		Fort fort = (Fort) getGameState().getGamePiece(fortId);
		Fort newFort = null;
		PlayingCup pCup = getGameState().getPlayingCup();
		BoardLocation location = fort.getLocation();
		
		int cost = 5;
		int citadelCost = 20;
		if(getPlayersInOrderOfTurn().size() < 4)
			citadelCost = 15;
		
		if (fort.getActualLevelNumWhenRestored() == 1 && player.getGold() >= 5) {
			newFort = pCup.getFortsWithLevel(2).get(0);
		} else if (fort.getActualLevelNumWhenRestored() == 2 && player.getGold() >= 5) {
			newFort = pCup.getFortsWithLevel(3).get(0);
		} else if (fort.getActualLevelNumWhenRestored() == 3 && player.getGold() >= citadelCost) {
			newFort = pCup.getFortsWithLevel(4).get(0);
			cost=citadelCost;
		} else{
			throw new Exception();
		}
		
		if(newFort != null) {
			pCup.addGamePieceToLocation(fort);
			location.addGamePieceToLocation(newFort);
			player.assignGamePieceToPlayer(newFort);
			player.removeGold(cost);
		}

		informPlayersAboutUpgradedFort(player, newFort, "upgraded");

	}

	/*
	 * Checking if the player has built a fort, it finds a fort and then it buys
	 * it It then removes 5 gold
	 */
	public synchronized void didBuildFort(String playerId, String locationId) throws Exception {
		Player player = getGameState().getPlayerByPlayerId(playerId);
		BoardLocation boardLocation = getGameState().getBoardLocation(locationId);
		PlayingCup cup = getGameState().getPlayingCup();
		
		if(player.getGold() < 5)
			throw new Exception();
		
		Fort fort = cup.getFortsWithLevel(1).get(0);
		player.assignGamePieceToPlayer(fort);
		boardLocation.addGamePieceToLocation(fort);
		player.removeGold(5);

		informPlayersAboutUpgradedFort(player, fort, "built");
	}

	// Letting other players know who upgraded a fort
	public void informPlayersAboutUpgradedFort(Player player, Fort newFort, String type) {
		GameMessage msg = newGameMessageForAllPlayers("playerMadeMoveInConstruction");
		msg.addToData("player", player.toSerializedFormat());
		msg.addToData("newFort", newFort.toSerializedFormat());
		msg.addToData("location", newFort.getLocation().toSerializedFormat());
		msg.addToData("type", type);
		msg.addToData("playingCup", getGameState().getPlayingCup().toSerializedFormat());
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
	}

	public void playerIsReadyForNextPhase(String playerId) {
		playersReadyForNextPhase.add(playerId);

		if (playersReadyForNextPhase.size() == getPlayersInOrderOfTurn().size()) {
			end();
		}
	}
	
	@Override
	public void setupNextPhase() {
		setNextPhase(new GoldCollectionPhase(getGameState(),getPlayersInOrderOfTurn()));
	}

	@Override
	public GameMessage getPhaseOverMessage() {
		// TODO Auto-generated method stub

		GameMessage message = new GameMessage("ConstructionPhaseOver");

		HashMap<String, Integer> playerGoldMap = new HashMap<String, Integer>();
		for (Player p : getPlayersInOrderOfTurn()) {
			playerGoldMap.put(p.getPlayerId(), p.getGold());
		}
		message.addToData("playersGold", playerGoldMap);
		message.addToData("hexeTiles", getGameState()
				.getHexLocationsInSerializedFormat());

		return message;
	}

	public void handlePhaseOver() {
		Set<Player> playersWithCitadelsLastConsPhase = getGameState().getPlayersWithCitadelsLastConsPhase();
		if(playersWithCitadelsLastConsPhase == null){
			getGameState().setPlayersWithCitadelsLastConsPhase(new HashSet<Player>());
		}
		
		Set<Player> playersCurrentlyWithCits = getGameState().getPlayersWithCitadelsOnBoard();
		
		Player p = null;
		for(Player _p : playersCurrentlyWithCits) {
			p = _p;
		}
		
		if(playersCurrentlyWithCits.size() == 1 && playersWithCitadelsLastConsPhase.contains(p)) {
			playerhaswon = true;
		}
		
		if(playerhaswon) {
			getGameState().playerWon(p);
		}
		
		getGameState().setPlayersWithCitadelsLastConsPhase(playersCurrentlyWithCits);
		
		if(getPlayersInOrderOfTurn().size() >= 4)
			setPlayersInOrderOfTurn(recalculateOrder(getPlayersInOrderOfTurn()));
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		GameMessage msg = newGameMessageForAllPlayers("constructionStarted");
		return msg;
	}

}