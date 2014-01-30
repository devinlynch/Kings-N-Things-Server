package com.kings.model.phases;

import java.util.List;

import com.kings.model.GameState;
import com.kings.model.Player;

public class PlacementPhase extends Phase {

	public PlacementPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleStart() {
		/**
		 * 
		 - Server tells player 1 that it is their turn to place their tower and tells evry other player that they are waiting on player 1
		- Once player 1 makes their move, they tell the server the id of the hex that they palced their tower on
		- The server tells all players of where player 1 put their hex
		- The server then tells player 2 that they can make their move and all other players that they are waiting on player 2
		--- etc goes on for all players
		- Server continues to next phase once all players have picked their tower locations
		 */
	}
	
	public void didPlaceFort(String playerId, String hexLocation) {
		// handle placing of hex, update state
	}

	@Override
	public String getPhaseOverMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setupNextPhase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
