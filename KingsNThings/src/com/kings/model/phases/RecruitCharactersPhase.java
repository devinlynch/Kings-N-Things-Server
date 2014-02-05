package com.kings.model.phases;

import java.util.List;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.Player;

public class RecruitCharactersPhase extends Phase {

	public RecruitCharactersPhase(GameState gameState,
			List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("recruitCharacters");
	}

	@Override
	public void handleStart() {
		// Skipping phase for demo
		
		end();
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new RecruitThingsPhase(getGameState(), getPlayersInOrderOfTurn()));
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
