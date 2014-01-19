package com.kings.networking.lobby;

import java.util.ArrayDeque;
import java.util.Queue;

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
		
		if( !instance.isAlive() )
			instance.start();
		return instance;
	}
	
	public void addToQueue(GameLobby gameLobby) {
		gameLobbies.add(gameLobby);
	}
	
	@Override
	public void run() {
		while(! isStopped() || gameLobbies.size() > 0) {
			if(gameLobbies.size() > 0) {
				GameLobby gameLobby = gameLobbies.remove();
				Game createdGame = gameLobby.becomeGame();
				createdGame.start();
			}
			
			// If the queue is empty now, sleep a bit
			if(gameLobbies.size() == 0) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					setStopped(true);
				}
			}
		}
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
}
