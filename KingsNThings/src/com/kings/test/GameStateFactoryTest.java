package com.kings.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.User;
import com.kings.model.factory.GameStateFactory;
import com.kings.model.phases.SetupPhase;
import com.kings.util.Utils;
public class GameStateFactoryTest {
	@Test
	public void testJson1() throws Exception {
		GameState gs = new GameState();
		Player p1 = new Player(new User(), gs, "1");
		Player p2 = new Player(new User(), gs, "2");
		Player p3 = new Player(new User(), gs, "3");
		Player p4 = new Player(new User(), gs, "4");
		List<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		gs.addPlayer(p1);
		gs.addPlayer(p2);
		gs.addPlayer(p3);
		gs.addPlayer(p4);
		gs.setCurrentPhase(new SetupPhase(gs, players));
		GameStateFactory.makeGameState(gs, 4);
		Map<String,Object> map = gs.toSerializedFormat();
		System.out.println(Utils.toJson(map));
	}
}
