package com.kings.model.phases;

import java.util.List;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.Player;

public class SetupPhase extends Phase {

	public SetupPhase(GameState gameState, List<Player>playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleStart() {
		/*// Send message to all players 
		- Info on players in game
		- Locations of lands on hexes
		- Ids of game pieces assigned to players at beginning (gold and tower)
		- Ids of hexes owned by each player*/
	}
	
	public GameMessage generateSetupGameMessage() {
		GameMessage message = new GameMessage("setupGame");
		
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
