package com.kings.test.networking.lobby;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.kings.model.User;
import com.kings.networking.lobby.GameLobby;
import com.kings.networking.lobby.GameMatcher;
import com.kings.networking.lobby.UserWaiting;
import com.kings.networking.lobby.exceptions.GameLobbyAlreadyFullException;

public class GameMatcherTest {
	
	public GameMatcher getTestGameMatcher() {
		GameMatcher matcher = new GameMatcher();
		
		User u1 = new User();
		u1.setId("1");
		User u2 = new User();
		u2.setId("2");
		UserWaiting uw1 = new UserWaiting(u1, 3);
		UserWaiting uw2 = new UserWaiting(u2, 3);
		
		Set<UserWaiting> users = new HashSet<UserWaiting>();
		users.add(uw1);
		users.add(uw2);
		
		GameLobby gl1 = new GameLobby();
		gl1.setNumberOfPlayersWanted(3);
		gl1.setUsers(users);
		
		matcher.addGameLobby(gl1);
		
		return matcher;
	}
	
	@Test
	public void testGetUsersLobby() {
		GameMatcher matcher = getTestGameMatcher();
		
		// Test getting lobby when user is already in a lobby
		GameLobby gl = matcher.getUsersLobby(new User("2"));
		assertNotNull(gl);
		
		// Test getting null when user is not in lobby
		User u3 = new User();
		u3.setId("3");
		GameLobby gl2 = matcher.getUsersLobby(u3);
		assertNull(gl2);
	}
	
	@Test
	public void testGetNonFullLobby() throws GameLobbyAlreadyFullException {
		GameMatcher matcher = getTestGameMatcher();
		
		// Test getting non full lobby
		GameLobby lobby = matcher.getNonFullLobby(3);
		assertEquals(lobby.getNumberOfPlayersWanted(), 3);
		
		// Test null lobby because all lobbies are full
		UserWaiting uw = new UserWaiting();
		uw.setUser(new User("4"));
		lobby.addUserWaiting(uw);
		lobby = matcher.getNonFullLobby(3);
		assertNull(lobby);
	}
	
	@Test
	public void testRegisterUserInANonFullLobby() {
		GameMatcher matcher = getTestGameMatcher();
		
		// Test existing game lobby assigned and game becomes full
		UserWaiting uw = new UserWaiting();
		uw.setUser(new User("3"));
		uw.setNumberOfPlayersWanted(3);
		GameLobby lobby1 = matcher.registerUserInANonFullLobby(uw);
		
		assertTrue(lobby1.isFull());
		assertNotNull(lobby1.getGame());
		
		// Test new game lobby assigned
		UserWaiting uw2 = new UserWaiting();
		uw2.setUser(new User("4"));
		uw2.setNumberOfPlayersWanted(2);
		GameLobby lobby2 = matcher.registerUserInANonFullLobby(uw2);
		assertFalse(lobby2.isFull());
		assertNull(lobby2.getGame());
	}
}
