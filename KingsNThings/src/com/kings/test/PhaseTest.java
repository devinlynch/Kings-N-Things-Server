package com.kings.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kings.database.DataAccess;
import com.kings.model.Player;
import com.kings.model.User;
import com.kings.model.phases.Phase;

import static org.junit.Assert.*;

public class PhaseTest {

	@Test
	public void testRecalculateOrder(){
		Player p1 = new Player(new User(), null, "p1");
		Player p2 = new Player(new User(), null, "p2");
		Player p3 = new Player(new User(), null, "p3");
		Player p4 = new Player(new User(), null, "p4");
		List<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		
		players = Phase.recalculateOrder(players);
		assertEquals("p1", players.get(3).getPlayerId());
		assertEquals("p2", players.get(0).getPlayerId());
		
		players = Phase.recalculateOrder(players);
		assertEquals("p1", players.get(2).getPlayerId());
		assertEquals("p2", players.get(3).getPlayerId());
		assertEquals("p3", players.get(0).getPlayerId());

	}
	
}
