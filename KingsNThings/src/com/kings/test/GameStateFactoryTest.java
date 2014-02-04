package com.kings.test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.factory.GameStateFactory;
import com.kings.model.phases.SetupPhase;
import com.kings.util.Utils;
public class GameStateFactoryTest {
	@Test
	public void testJson1() throws Exception {
		GameState gs = new GameState();
		gs.setCurrentPhase(new SetupPhase(gs, new ArrayList<Player>()));
		GameStateFactory.makeGameState(gs, 3);
		Map<String,Object> map = gs.toSerializedFormat();
		System.out.println(Utils.toJson(map));
	}
}
