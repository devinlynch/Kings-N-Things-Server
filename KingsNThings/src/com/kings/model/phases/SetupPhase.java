package com.kings.model.phases;

import com.kings.model.GameState;

public class SetupPhase extends Phase {

	public SetupPhase(GameState gameState) {
		super(gameState);
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

	@Override
	public String getPhaseOverMessage() {
		//TODO
		return null;
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new PlacementPhase(getGameState()));
	}

	@Override
	public String getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
