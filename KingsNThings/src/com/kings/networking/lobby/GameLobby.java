package com.kings.networking.lobby;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kings.database.DataAccess;
import com.kings.http.HttpResponseMessage;
import com.kings.model.Game;
import com.kings.model.User;
import com.kings.networking.lobby.exceptions.GameLobbyAlreadyFullException;
import com.kings.util.Utils;

/**
 * Holds a set of players who are paired up to play against each other, may be waiting
 * on more players to join or may be ready to play.
 * @author devinlynch
 *
 */
public class GameLobby {
	private String gameLobbyId;
	private Set<UserWaiting> users;
	private int numberOfPlayersWanted;
	private Game game;
	private boolean isPrivate;
	private User host;
	private boolean demo;
	
	public GameLobby(boolean demo) {
		users = new HashSet<UserWaiting>();
		setGameLobbyId(Utils.generateRandomId());
		this.demo=demo;
	}
	
	public Set<UserWaiting> getUsers() {
		return users;
	}
	public void setUsers(Set<UserWaiting> users) {
		this.users = users;
	}
	public int getNumberOfPlayersWanted() {
		return numberOfPlayersWanted;
	}
	public void setNumberOfPlayersWanted(int numberOfPlayersWanted) {
		this.numberOfPlayersWanted = numberOfPlayersWanted;
	}
	
	public boolean isFull() {
		return users.size() >= numberOfPlayersWanted;
	}
	
	public boolean isEmpty() {
		return users.size() == 0;
	}
	
	/**
	 * Unregisters the user from this lobby, that is, it removes the user from the set of
	 * UserWaitings if he exists in the set
	 * @param user
	 */
	public void unregisterUser(User user) {
		synchronized (users) {
			Iterator<UserWaiting> it = getUsers().iterator();
			while(it.hasNext()) {
				UserWaiting uw = it.next();
				User userAlreadyInLobby = uw.getUser();
				if(user.equals(userAlreadyInLobby)) {
					it.remove();
				}
			}
			informUsersOfNewLobbyState();
		}
	}
	
	public void addUserWaiting(UserWaiting userWaiting) throws GameLobbyAlreadyFullException {
		if(isFull())
			throw new GameLobbyAlreadyFullException();
		
		getUsers().add(userWaiting);
		informUsersOfNewLobbyState();
	}
	
	public void informUsersOfNewLobbyState() {
		synchronized (users) {
			HttpResponseMessage message = new HttpResponseMessage();
			message.setType("gameLobbyState");
			message.addToData("lobby", this);
			
			for(UserWaiting userWaiting : users) {
				User user = userWaiting.getUser();
				user.sendMessage(message, new DataAccess());
			}
		}
	}
	
	/**
	 * Turns this GameLobby into a {@link Game} and assigns it to the game attribute. <b>You only call this once
	 * the Lobby is ready to be turned into a game, do not do any more operations on the lobby afterwards</b>
	 * 
	 */
	public Game becomeGame() {
		//TODO any other game initializing
		Game game = new Game();
		game.setCreatedFromGameLobbyId(getGameLobbyId());
		for(UserWaiting userWaiting : getUsers()) {
			User user = userWaiting.getUser();
			game.addUser(user);
		}
		setGame(game);
		
		return game;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getGameLobbyId() {
		return gameLobbyId;
	}

	public void setGameLobbyId(String gameLobbyId) {
		this.gameLobbyId = gameLobbyId;
	}

	public boolean isDemo() {
		return demo;
	}

	public void setDemo(boolean demo) {
		this.demo = demo;
	}
}
