package com.kings.model.phases;

import java.util.List;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.Player;

public class RandomEventPhase extends Phase {

	public RandomEventPhase(GameState gameState,
			List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new MovementPhase(getGameState(), getPlayersInOrderOfTurn()));
	}


	@Override
	public GameMessage getPhaseOverMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
