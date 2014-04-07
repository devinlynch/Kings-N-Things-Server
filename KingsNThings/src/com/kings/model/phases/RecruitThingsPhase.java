             package com.kings.model.phases;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.BoardLocation;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.Thing;

public class RecruitThingsPhase extends Phase {
	Set<String> playersReadyForNextPhase;

	public RecruitThingsPhase(GameState gameState,
			List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("recruitThings");
		playersReadyForNextPhase = new HashSet<String>();
	}

	@Override
	public void handleStart() {
		// Get all the possible things the player is able to to recruit and tell the client
		// Then wait for the client to tell us which of these things they actually recruited
		
		Map<Player, List<String>> mapOfPlayersToThings = getGameState().getPossibleThingsToRecruitForPlayers();
		
		GameMessage message = new GameMessage("didStartRecruitThingsPhase");
		for(Player p: mapOfPlayersToThings.keySet()) {
			List<String> listOfPossibleRecruitments = mapOfPlayersToThings.get(p);
			
			message.addPlayerToSendTo(p);
			message.addUserSpecificData(p.getPlayerId(), "possibleRecruitments", listOfPossibleRecruitments);
		}
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
 	}
	
	
	public void didRecruitAndPlaceThing(String playerId, String thingId, String locationId, boolean didBuy) {
		Player player = getGameState().getPlayerByPlayerId(playerId);
		BoardLocation boardLocation = getGameState().getBoardLocation(locationId);
		Thing thing = (Thing)getGameState().getGamePiece(thingId);
		player.assignGamePieceToPlayer(thing);
		boardLocation.addGamePieceToLocation(thing);
		
		String type="free";
		if(didBuy) {
			type="buy";
			player.removeGold(5);
		}
		
		informPlayersAboutRecruitAndPlacement(playerId, thingId, locationId, type);
	}
	
	public Thing didTradeThing(String playerId, String oldThingId, String newThingId) {
		Player player = getGameState().getPlayerByPlayerId(playerId);
		
		Thing oldThing = (Thing)getGameState().getGamePiece(oldThingId);
		Thing newThing = (Thing)getGameState().getGamePiece(newThingId);
		
		player.assignGamePieceToPlayerRack(newThing);
		getGameState().getPlayingCup().addGamePieceToLocation(oldThing);
		
		GameMessage msg = newGameMessageForAllPlayers("playerTradedThing");
		msg.addToData("player", player.toSerializedFormat());
		msg.addToData("newThing", newThing.toSerializedFormat());
		msg.addToData("oldThing", oldThing.toSerializedFormat());
		msg.addToData("locationId", player.getRack1().getId());
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
		
		return newThing;
	}
	
	public void informPlayersAboutRecruitAndPlacement(String playerId, String thingId, String locationId, String type) {
		GameMessage msg = newGameMessageForAllPlayers("playerRecruitedAndPlacedThing");
		msg.addToData("playerId", playerId);
		msg.addToData("thingId", thingId);
		msg.addToData("locationId", locationId);
		msg.addToData("recruitmentType", type);
		msg.getPlayersToSendTo().remove(getGameState().getPlayerByPlayerId(playerId));
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
	}
	
	public void playerIsReadyForNextPhase(String playerId) {
		playersReadyForNextPhase.add(playerId);
		
		if(playersReadyForNextPhase.size() == getPlayersInOrderOfTurn().size()) {
			end();
		}
	}
	
	
	@Override
	public void setupNextPhase() {
		setNextPhase(new RandomEventPhase(getGameState(), getPlayersInOrderOfTurn()));
	}

	@Override
	public GameMessage getPhaseOverMessage() {
		GameMessage msg = newGameMessageForAllPlayers("recruitThingsPhaseOver");
		
		HashMap<String, Integer> playerGoldMap = new HashMap<String, Integer>();
		for(Player p : getPlayersInOrderOfTurn()) {
			playerGoldMap.put(p.getPlayerId(), p.getGold());
		}
		msg.addToData("playersGold", playerGoldMap);
		msg.addToData("hexeTiles", getGameState().getHexLocationsInSerializedFormat());
		
		return msg;
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
