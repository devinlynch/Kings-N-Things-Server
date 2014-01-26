package com.kings.networking.lobby;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

import com.kings.model.Game;

/**
 * A queue that runs and creates games from {@link GameLobby}'s 
 * @author devinlynch
 *
 */
public class GameCreatorQueue extends Thread {
	private Queue<GameLobby> gameLobbies;
	private static GameCreatorQueue instance = null;
	private boolean stopped;

	public GameCreatorQueue() {
		super();
		gameLobbies = new ArrayDeque<GameLobby>();
	}
	
	public static GameCreatorQueue getInstance() {
		if(instance == null){
			instance = new GameCreatorQueue();
			instance.start();
		}
		
		if( !instance.isAlive() ) {
			instance.start();
			instance.setStopped(false);
		}
		return instance;
	}
	
	public void addToQueue(GameLobby gameLobby) {
		gameLobbies.add(gameLobby);
	}
	
	@Override
	public void run() {
		while( ! isStopped() ) {
			runThroughPendingGameLobbies();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				setStopped(true);
			}
		}
	}
	
	
	protected void runThroughPendingGameLobbies() {
		Set<GameLobby> gameLobbies = GameMatcher.getInstance().getNonFullGameLobbies();
		Set<GameLobby> coppiedLobbies = new HashSet<GameLobby>(gameLobbies);
		
		Iterator<GameLobby> it = coppiedLobbies.iterator();
		while( it.hasNext() ) {
			GameLobby gameLobby = it.next();
			
			if(gameLobby.isEmpty()) {
				GameMatcher.getInstance().removeGameLobby(gameLobby);
				continue;
			}
			
			if(gameLobby.isFull()) {
				turnIntoGame(gameLobby);
			}
			
			
		}
	}
	
	protected void turnIntoGame(GameLobby gameLobby) {
		boolean isStillInQueue = GameMatcher.getInstance().removeGameLobby(gameLobby);
		if( ! isStillInQueue )
			return;
		System.out.println("Removed game lobby " + gameLobby.toString() + " from queue, turning into game");
		Game createdGame = gameLobby.becomeGame();
		createdGame.start();
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
}
