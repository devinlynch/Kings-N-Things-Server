package com.kings.test;

import org.junit.Test;

import static org.junit.Assert.*;

import com.kings.database.DataAccess;
import com.kings.http.GameMessage;
import com.kings.model.Game;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.SentMessage;
import com.kings.model.User;

public class MessageSavingTests {
	@Test
	public void testSaveMessage() {
		DataAccess access = new DataAccess();
		access.beginTransaction();
		User u1 = new User();
		access.save(u1);
		
		
		Game g = new Game();
		g.addUser(u1);
		access.save(g);

		GameState gs = new GameState();
		gs.setGameId(g.getGameId());
		Player p1 = new Player(u1, gs, "1");
		gs.addPlayer(p1);
		g.setGameState(gs);
		
		access.commit();

		GameMessage gameMessage = new GameMessage("test");
		gameMessage.addPlayerToSendTo(p1);
		gameMessage.addToData("foo", "bar");
		gs.queueUpGameMessageToSendToAllPlayers(gameMessage);
		
		access.beginTransaction();
		Game savedGame = access.get(Game.class, g.getGameId());
		SentMessage sm = savedGame.getSentMessages().get(0);
		assertTrue(sm.getJson().contains("foo"));
		access.commit();
	}
}
