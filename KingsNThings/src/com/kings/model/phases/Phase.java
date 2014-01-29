package com.kings.model.phases;

import java.util.List;

import com.kings.model.GameState;
import com.kings.model.Player;

public abstract class Phase {
	/**
	 * Contains the list of players in their correct order, this should be updated to the correct order once phase starts
	 */
	private List<Player> playersInOrderOfTurn;
	
	public Phase(GameState gameState) {
		this.gameState=gameState;
	}
	
	private GameState gameState;
	private Phase nextPhase;
	
	public void start() {
		sendPhaseStartedMessage();
		handleStart();
	}
	
	public abstract void handleStart();
	public abstract void setupNextPhase();
	
	public void end() {
		setupNextPhase();
		handlePhaseOver();
		gameState.setCurrentPhase(nextPhase);
		nextPhase.handleStart();
	}
	
	public void handlePhaseOver(){
		// TODO
	}
	
	public void sendPhaseStartedMessage() {
		// TODO
	}
	
	public abstract String getPhaseOverMessage();
	public abstract String getPhaseStartedMessage();

	
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Phase getNextPhase() {
		return nextPhase;
	}

	public void setNextPhase(Phase nextPhase) {
		this.nextPhase = nextPhase;
	}

	public List<Player> getPlayersInOrderOfTurn() {
		return playersInOrderOfTurn;
	}

	public void setPlayersInOrderOfTurn(List<Player> playersInOrderOfTurn) {
		this.playersInOrderOfTurn = playersInOrderOfTurn;
	}
}
