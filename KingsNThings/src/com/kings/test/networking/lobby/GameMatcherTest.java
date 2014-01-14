package com.kings.test.networking.lobby;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.kings.model.User;
import com.kings.networking.lobby.GameLobby;
import com.kings.networking.lobby.GameMatcher;
import com.kings.networking.lobby.UserWaiting;

public class GameMatcherTest {
	
	@Test
	public void testGetUsersLobby() {
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
		
		// Test getting lobby when user is already in a lobby
		GameLobby gl = matcher.getUsersLobby(u2);
		assertNotNull(gl);
	}
	
}
