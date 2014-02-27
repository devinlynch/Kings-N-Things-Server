package com.kings.database;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.kings.model.GameState;

/**
 * Game state cache which maps game ids to the game state in the cache
 * @author devinlynch
 *
 */
public class GameStateCache {
	private static GameStateCache instance;
	private ConcurrentMap<String, GameState> cache;
	private Object monitor = new Object();
	
	public GameStateCache() {
		this.cache = new ConcurrentHashMap<String, GameState>();
	}
	
	public static synchronized GameStateCache getInstance() {
		if(instance == null){
			instance = new GameStateCache();
		}
		return instance;
	}
	
	public synchronized void addGameState(String gameId, GameState gameState) {
		synchronized (monitor) {
			getCache().putIfAbsent(gameId, gameState);
		}
	}
	
	public GameState getGameState(String gameId) {
		synchronized (monitor) {
			return getCache().get(gameId);
		}
	}
	
	public void removeGameStateForGame(String gameId) {
		synchronized (monitor) {
			getCache().remove(gameId);
		}
	}

	protected ConcurrentMap<String, GameState> getCache() {
		return cache;
	}
}
