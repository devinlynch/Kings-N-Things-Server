package com.kings.model.phases;

import com.kings.model.GameState;

public abstract class Phase {
	
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
}
