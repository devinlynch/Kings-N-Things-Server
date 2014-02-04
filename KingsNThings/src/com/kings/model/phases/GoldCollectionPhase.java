package com.kings.model.phases;

import java.util.List;

import com.kings.http.GameMessage;
import com.kings.model.Game;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.networking.UDPSenderQueue;

public class GoldCollectionPhase extends Phase {

	public GoldCollectionPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("gold");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleStart() {
		GameMessage gameMessage = new GameMessage("howMuchGold");
		
		// to some sort of algorithm
		
		gameMessage.addToData("player1gold", 1);
		getGameState().queueUpGameMessageToSendToAllPlayers(gameMessage);
		
		// TODO Auto-generated method stub

	}

	@Override
	public void setupNextPhase() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPhaseOverMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
