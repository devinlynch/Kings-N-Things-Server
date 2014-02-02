package com.kings.model.phases;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.Player;

public class SetupPhase extends Phase {

	public SetupPhase(GameState gameState, List<Player>playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleStart() {
		/*
		 * Send message to all players with:
			^ Info on players in game
			^ Locations of lands on hexes
			^ Ids of game pieces assigned to players at beginning (gold and tower)
			^ Ids of hexes owned by each player
		*/
		
		//TODO initialize game state with players owning certain locations, pieves, gold and initialize land on hexes
		GameMessage message = generateSetupGameMessage();
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public GameMessage generateSetupGameMessage() {
		GameMessage message = new GameMessage("setupGame");
		message.setPlayersToSendTo(getGameState().getPlayers());		
		message.addToData("hexes", getGameState().getHexlocations());
		
		//TODO we need to send over certain data from a player to all other players, we need to somehow encapsulate certain data
		message.addToData("playersInGame", getGameState().getPlayers());
		message.addToData("usernamesOfUsers", getGameState().getPlayers());
		
		
		
		for(Player p : getGameState().getPlayers()) {
			Set<String> myGamePieceIds = new HashSet<String>();
			for(GamePiece piece : p.getGamePieces()) {
				myGamePieceIds.add(piece.getId());
			}
			message.addUserSpecificData(p.getPlayerId(), "myGamePieces", p);
			message.addUserSpecificData(p.getPlayerId(), "myGold", p.getGold());
			message.addUserSpecificData(p.getPlayerId(), "myOwnedHexes", getGameState().getHexLocationsOwnedByPlayer(p.getPlayerId()));
			message.addUserSpecificData(p.getPlayerId(), "myPlayerState", p);
		}
		
		return message;
	}

	@Override
	public String getPhaseOverMessage() {
		//TODO
		return null;
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new PlacementPhase(getGameState(), getPlayersInOrderOfTurn()));
	}

	@Override
	public String getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
