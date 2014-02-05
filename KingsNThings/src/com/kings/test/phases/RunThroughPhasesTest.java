package com.kings.test.phases;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.kings.http.SentMessage;
import com.kings.model.Game;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.User;
import com.kings.model.phases.GoldCollectionPhase;
import com.kings.model.phases.PlacementPhase;
import com.kings.model.phases.SetupPhase;
import com.kings.model.phases.exceptions.NotYourTurnException;

public class RunThroughPhasesTest {
	
	public GameState getNewGameState() throws Exception{
		Game game = new Game();
		game.setActive(true);
		game.setGameId("1");
		game.setStartedDate(new Date());
		Set<User> users = new HashSet<User>();
		users.add(new User("1"));
		users.add(new User("2"));
		users.add(new User("3"));
		users.add(new User("4"));
		game.setUsers(users);
		game.start();
		
		return game.getGameState();
	}
	
	@Test
	public void testRunThroughGame() throws Exception{
		GameState gs = getNewGameState();
		
		assertEquals("setup", gs.getCurrentPhase().getPhaseId());
		
		// Go through setup phase
		SetupPhase sPhase = (SetupPhase)gs.getCurrentPhase();
		sPhase.playerIsReadyForPlacement("1");
		sPhase.playerIsReadyForPlacement("3");
		sPhase.playerIsReadyForPlacement("4");
		
		assertEquals("setup", gs.getCurrentPhase().getPhaseId());
		sPhase.playerIsReadyForPlacement("2");
		assertEquals("placement", gs.getCurrentPhase().getPhaseId());
		
		// Now do placement phase
		PlacementPhase pPhase = (PlacementPhase) gs.getCurrentPhase();
		pPhase.didPlaceControlMarkers("player1", "hexLocation_1", "hexLocation_2", "hexLocation_3");
		pPhase.didPlaceControlMarkers("player2", "hexLocation_4", "hexLocation_5", "hexLocation_6");
		pPhase.didPlaceControlMarkers("player3", "hexLocation_7", "hexLocation_8", "hexLocation_9");
		pPhase.didPlaceControlMarkers("player4", "hexLocation_10", "hexLocation_11", "hexLocation_12");
		
		assertEquals("player1", gs.getHexLocationsById("hexLocation_1").getOwner().getPlayerId());
		assertEquals("player2", gs.getHexLocationsById("hexLocation_5").getOwner().getPlayerId());
		assertEquals("player3", gs.getHexLocationsById("hexLocation_9").getOwner().getPlayerId());
		assertEquals("player4", gs.getHexLocationsById("hexLocation_12").getOwner().getPlayerId());
		
		Player p1 = gs.getPlayerByPlayerId("player1");
		GamePiece fort1 = (GamePiece) p1.getGamePieces().values().toArray()[0];
		assertEquals("Tower", fort1.getName());
		pPhase.didPlaceFort("player1", fort1.getId(), "hexLocation_1");
		assertEquals("hexLocation_1", fort1.getLocation().getId());
		
		// Try placing fort out of order
		boolean didGetNotTurnException = false;
		try{
			pPhase.didPlaceFort("player3", "", "");
		} catch(NotYourTurnException e) {
			didGetNotTurnException = true;
		}
		assertTrue(didGetNotTurnException);
		
		Player p2 = gs.getPlayerByPlayerId("player2");
		GamePiece fort2 = (GamePiece) p2.getGamePieces().values().toArray()[0];
		assertEquals("Tower", fort2.getName());
		pPhase.didPlaceFort("player2", fort2.getId(), "hexLocation_4");
		assertEquals("hexLocation_4", fort2.getLocation().getId());
		
		Player p3 = gs.getPlayerByPlayerId("player3");
		GamePiece fort3 = (GamePiece) p3.getGamePieces().values().toArray()[0];
		assertEquals("Tower", fort3.getName());
		pPhase.didPlaceFort("player3", fort3.getId(), "hexLocation_7");
		assertEquals("hexLocation_7", fort3.getLocation().getId());
		
		Player p4 = gs.getPlayerByPlayerId("player4");
		GamePiece fort4 = (GamePiece) p4.getGamePieces().values().toArray()[0];
		assertEquals("Tower", fort4.getName());
		pPhase.didPlaceFort("player4", fort4.getId(), "hexLocation_10");
		assertEquals("hexLocation_10", fort4.getLocation().getId());
		
		// Gold phase should be started and handled automatically, players should now have gold assigned
		assertEquals("gold", gs.getCurrentPhase().getPhaseId());
		
		assertEquals(new Integer(14), p1.getGold());
		assertEquals(new Integer(14), p2.getGold());
		assertEquals(new Integer(14), p3.getGold());
		assertEquals(new Integer(14), p4.getGold());
		
		GoldCollectionPhase cPhase = (GoldCollectionPhase) gs.getCurrentPhase();
		
		cPhase.playerIsReadyForNextPhase("player1");
		cPhase.playerIsReadyForNextPhase("player2");
		cPhase.playerIsReadyForNextPhase("player3");
		assertEquals("gold", gs.getCurrentPhase().getPhaseId());
		cPhase.playerIsReadyForNextPhase("player4");
		assertEquals("recruitThings", gs.getCurrentPhase().getPhaseId());
		
		for(SentMessage msg : gs.getSentMessages()){
			System.out.println(msg.getJson());
		}
	}
}
