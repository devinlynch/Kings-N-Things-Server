package com.kings.model.factory;

import java.util.HashSet;
import java.util.Set;

import com.kings.model.Counter;
import com.kings.model.GameState;

/**
 * This class populates a given GameState object with all needed objects, pieces, etc. for playing a game
 * @author devinlynch
 *
 */
public class GameStateFactory {
	
	//TODO implement
	
	public static GameState makeGameState(GameState gameState, int numberOfPlayers) throws Exception {
		if(numberOfPlayers < 2 || numberOfPlayers > 4)
			throw new Exception("Number of players must be between 2 and 4");
		
		switch(numberOfPlayers){
		case 2: {
			
		}
		case 3: {
			
		}
		case 4: {
			
		}
		}
		
		return gameState;
	}
	
	public Set<Counter> getAllCounters() {
		Set<Counter> counters = new HashSet<Counter>();
		
		return counters;
	}
	
}
