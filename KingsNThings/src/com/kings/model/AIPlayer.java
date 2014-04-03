package com.kings.model;

public class AIPlayer extends Player {

	public AIPlayer(User user, GameState gameState, String playerId) {
		super(user, gameState, playerId);
		setAi(true);
	}
	
}
