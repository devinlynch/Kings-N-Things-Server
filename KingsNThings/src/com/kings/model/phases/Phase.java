package com.kings.model.phases;

import java.util.List;

import com.kings.model.GameState;
import com.kings.model.Player;

public abstract class Phase {
	private String phaseId;
	private boolean over;
	/**
	 * Contains the list of players in their correct order, this should be updated to the correct order once the sequence 
	 * of phases is over
	 */
	private List<Player> playersInOrderOfTurn;
	
	public Phase(GameState gameState, List<Player> playersInOrderOfTurn) {
		this.gameState=gameState;
		this.playersInOrderOfTurn=playersInOrderOfTurn;
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
		setOver(true);
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
	
	/**
	 * Given a order of players, returns the new order of the players for the next sequence of phases.
	 * <b> This should only be called at the end of sequence of phases</b>
	 * @param players
	 * @return
	 */
	public static List<Player> recalculateOrder(List<Player> players) {
		players.add(players.remove(0));
		return players;
	}

	public synchronized boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
	
}
