package com.kings.model.phases;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kings.controllers.phases.SetupPhaseController;
import com.kings.http.GameMessage;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.Player;

public class SetupPhase extends Phase {
	private Set<String> playersReadyForPlacement;

	public SetupPhase(GameState gameState, List<Player>playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		playersReadyForPlacement = new HashSet<String>();
		setPhaseId("setup");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleStart() {
		/*
		 * Send message to all players with:
			^ Info on players in game
			^ Locations of lands on hexes
			^ Ids of game pieces assigned to players at beginning (gold and tower)
		*/
		
		GameMessage message = generateSetupGameMessage();
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public GameMessage generateSetupGameMessage() {
		GameMessage message = new GameMessage("setupGame");
		message.setPlayersToSendTo(getGameState().getPlayers());		
		message.addToData("gameState", getGameState().toSerializedFormat());
		
		/*for(Player p : getGameState().getPlayers()) {
			Set<String> myGamePieceIds = new HashSet<String>();
			for(GamePiece piece : p.getGamePieces()) {
				myGamePieceIds.add(piece.getId());
			}
			message.addUserSpecificData(p.getPlayerId(), "myself", p.getGamePieces());
			message.addUserSpecificData(p.getPlayerId(), "myGold", p.getGold());
			message.addUserSpecificData(p.getPlayerId(), "myPlayerState", p);
		}*/
		
		return message;
	}
	
	/**
	 * Called from {@link SetupPhaseController} for each player who is ready for next phase.
	 * Once all players are ready, this phase ends
	 * @param playerId
	 */
	public synchronized void playerIsReadyForPlacement(String playerId) {
		if(isOver())
			return;
		playersReadyForPlacement.add(playerId);
		if(playersReadyForPlacement.size() >= getPlayersInOrderOfTurn().size()) {
			end();
		}
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
