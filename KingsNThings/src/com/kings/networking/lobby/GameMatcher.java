package com.kings.networking.lobby;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kings.model.Game;
import com.kings.model.User;
import com.kings.networking.lobby.exceptions.GameLobbyAlreadyFullException;

/**
 * Matches users who are waiting to a game lobby.  This class holds
 * all of the game lobbys. <b>Use the getInstance method for getting
 * reference to the game matcher.  This needs to be a singleton because
 * the set of game lobbies are being stored in memory.</b>
 * @author devinlynch
 *
 */
public class GameMatcher {
	// Need to synchronize
	private Set<GameLobby> nonFullGameLobbies;
	private Set<GameLobby> pendingGameLobbies;

	private static GameMatcher instance;
	
	public GameMatcher() {
		nonFullGameLobbies = new HashSet<GameLobby>();
		pendingGameLobbies = new HashSet<GameLobby>();
	}
	
	public static GameMatcher getInstance() {
		if(instance == null) {
			instance = new GameMatcher();
		}
		return instance;
	}
	
	/**
	 * Registers the given {@link UserWaiting} into a {@link GameLobby}
	 * @param userWaiting
	 * @return The {@link GameLobby} that the user is now in
	 */
	public GameLobby registerUserWaiting(UserWaiting userWaiting) {
		User user = userWaiting.getUser();
		
		GameLobby usersLobby = getUsersLobby(user);
		if(usersLobby != null) {
			// User is already in lobby, unregister them from the lobby and search for a new one
			usersLobby.unregisterUser(user);
		}
		
		GameLobby lobby = registerUserInANonFullLobby(userWaiting);
		return lobby;
	}
	
	/**
	 * Gets the {@link GameLobby} from the set of pending lobbies that the given user is in
	 * @param user
	 * @return The {@link GameLobby} that the user is in or null
	 */
	public GameLobby getUsersLobby(User user) {
		synchronized (nonFullGameLobbies) {
			Iterator<GameLobby> it = getNonFullGameLobbies().iterator();
			while(it.hasNext()) {
				GameLobby lobby = it.next();
				Set<UserWaiting> userWaitings = lobby.getUsers();
				Iterator<UserWaiting> _it = userWaitings.iterator();
				while(_it.hasNext()) {
					UserWaiting userWaiting = _it.next();
					User userInWaiting = userWaiting.getUser();
					if(userInWaiting==null)
						continue;
					if(userInWaiting.equals(user)) {
						return lobby;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Registers the user into an existing non full {@link GameLobby} or creates a new {@link GameLobby} for the user.
	 * @param userWaiting
	 * @return the {@link GameLobby} which the user was registered in
	 */
	public GameLobby registerUserInANonFullLobby(UserWaiting userWaiting) {
		int numberOfPlayersWanted = userWaiting.getNumberOfPlayersWanted();
		
		GameLobby lobby = getNonFullLobby(numberOfPlayersWanted);
		if(lobby==null){
			lobby = new GameLobby();
			lobby.setNumberOfPlayersWanted(numberOfPlayersWanted);
			addGameLobby(lobby);
		}
		
		try {
			lobby.addUserWaiting(userWaiting);
		} catch (GameLobbyAlreadyFullException e) {
			System.out.println("GOT A GameLobbyAlreadyFullException in registerUserInANonFullLobby.  THIS SHOULD NOT BE HAPPENING!!!");
		}
		
		if(lobby.isFull())
			lobby.becomeGame();
		
		return lobby;
	}
	
	/**
	 * Returns a non full {@link GameLobby} with the same amount of players wanted by the other {@link User}s
	 * @param numberOfPlayersWanted
	 * @return
	 */
	public GameLobby getNonFullLobby(int numberOfPlayersWanted) {
		synchronized (nonFullGameLobbies) {
			Iterator<GameLobby> it = nonFullGameLobbies.iterator();
			while(it.hasNext()) {
				GameLobby lobby = it.next();
				int numPlayersWantedInLobby = lobby.getNumberOfPlayersWanted();
				
				// Continue if the lobby is full
				if(lobby.isFull())
					continue;
				
				// TODO: maybe we can look for lobbies with around the same amount of players
				// 		 instead of exact
				if(numberOfPlayersWanted == numPlayersWantedInLobby) {
					return lobby;
				}
			}
		}
		return null;
	}
	
	/**
	 * Remove the given lobby from the set of full lobbies and adds it to the pending lobby.  <b>This
	 * should ONLY be called once a {@link GameLobby} has a {@link Game} set to it.</b>
	 * @param gameLobby
	 */
	public void assignLobbyAsBeingFull(GameLobby gameLobby) {
		synchronized (nonFullGameLobbies) {
			getNonFullGameLobbies().remove(gameLobby);
		}
		getPendingGameLobbies().add(gameLobby);
	}
	
	public void addGameLobby(GameLobby gameLobby) {
		getNonFullGameLobbies().add(gameLobby);
	}
	
	public Set<GameLobby> getNonFullGameLobbies() {
		return nonFullGameLobbies;
	}

	public void setNonFullGameLobbies(Set<GameLobby> gameLobbies) {
		this.nonFullGameLobbies = gameLobbies;
	}

	public Set<GameLobby> getPendingGameLobbies() {
		return pendingGameLobbies;
	}

	public void setPendingGameLobbies(Set<GameLobby> pendingGameLobbies) {
		this.pendingGameLobbies = pendingGameLobbies;
	}
	
	
	
}
