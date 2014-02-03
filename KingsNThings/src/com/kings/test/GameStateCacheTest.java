package com.kings.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import com.kings.database.GameStateCache;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.SetupPhase;

public class GameStateCacheTest {
	@Test
	public void test1() {
		GameStateCache cache = GameStateCache.getInstance();
		
		GameState gs = new GameState();
		gs.setCurrentPhase(new SetupPhase(gs, new ArrayList<Player>()));
		cache.addGameState("1", gs);
		
		GameState gs2 = cache.getGameState("1");
		assertTrue(gs2.getCurrentPhase() instanceof SetupPhase);
		gs2.getCurrentPhase().end();
		
		GameState gs3 = cache.getGameState("1");
		assertTrue(gs2.getCurrentPhase() instanceof SetupPhase);
	}
}
