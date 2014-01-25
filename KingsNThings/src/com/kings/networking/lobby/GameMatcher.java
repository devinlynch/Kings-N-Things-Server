package com.kings.networking.lobby;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kings.model.User;
import com.kings.networking.lobby.exceptions.GameLobbyAlreadyFullException;

/**
 * Takes users out of the waiting queue and adds them to a lobby.  This class holds
 * all of the game lobbys. <b>Use the getInstance method for getting
 * reference to the game matcher.  This needs to be a singleton because
 * the set of game lobbies are being stored in memory.</b>
 * @author devinlynch
 *
 */
public class GameMatcher extends Thread {
	// This is stored in the JVM memory, we need to make this stored in a cache server
	private Set<GameLobby> nonFullGameLobbies;
	private static GameMatcher instance;
	private boolean stopped;
	
	public GameMatcher() {
		nonFullGameLobbies = new HashSet<GameLobby>();
	}
	
	public GameMatcher(Set<GameLobby> gameLobbies) {
		nonFullGameLobbies = gameLobbies;
	}
	
	public synchronized static GameMatcher getInstance() {
		if(instance == null) {
			instance = new GameMatcher();
			GameCreatorQueue.getInstance();
		}
		
		if(! instance.isAlive() ) {
			if(instance.isInterrupted()){
				instance = new GameMatcher(instance.getNonFullGameLobbies());
			}
				
			instance.start();
		}
		return instance;
	}
	
	@Override
	public void run() {
		while( ! isStopped() ) {
			UserWaitingQueue queue = UserWaitingQueue.getInstance();
			UserWaiting userWaiting = queue.getUserWaiting();
			
			if(userWaiting != null){
				GameLobby lobby = registerUserWaiting(userWaiting);
				if(lobby != null) {
					userWaiting.informLobbyFound(lobby);
				} else{
					userWaiting.informNoLobbyFound();
				}
			}
			
			if( !queue.isUsersInQueue() ) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					setStopped(true);
				}
			}
		}
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
		
		GameLobby lobby= null;
		if(userWaiting.isUserWaitingHostGame()) {
			lobby = new GameLobby();
			lobby.setNumberOfPlayersWanted(userWaiting.getNumberOfPlayersWanted());
			lobby.setHost(user);
			lobby.setPrivate(true);
			try {
				lobby.addUserWaiting(userWaiting);
			} catch (GameLobbyAlreadyFullException e) {
				lobby = null;
				e.printStackTrace();
			}
			addGameLobby(lobby);
		} else if(userWaiting.isUserWaitingSearchGame()) {
			UserWaitingSearchGame searchLobby = (UserWaitingSearchGame) userWaiting;
			lobby = getGameLobbyHostedByUser(searchLobby.getUsernameOfPlayerSearchingFor());
			if(lobby != null) {
				try {
					lobby.addUserWaiting(userWaiting);
				} catch (GameLobbyAlreadyFullException e) {
					lobby = null;
					e.printStackTrace();
				}
			}
		} else{
			lobby = registerUserInANonFullLobby(userWaiting);
		}
		
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
		
		GameLobby lobby = getNonFullSearchableLobby(numberOfPlayersWanted);
		if(lobby==null)
			lobby = createAndAddNewGameLobbyFromUserWaiting(userWaiting);
		
		try {
			lobby.addUserWaiting(userWaiting);
		} catch (GameLobbyAlreadyFullException e) {
			System.out.println("GOT A GameLobbyAlreadyFullException in registerUserInANonFullLobby.  THIS SHOULD NOT BE HAPPENING!!!");
		}
		
		return lobby;
	}
	
	protected GameLobby createAndAddNewGameLobbyFromUserWaiting(UserWaiting userWaiting) {
		GameLobby lobby = new GameLobby();
		lobby.setNumberOfPlayersWanted(userWaiting.getNumberOfPlayersWanted());
		addGameLobby(lobby);
		return lobby;
	}
	
	public void createGameFromLobby(GameLobby lobby) {
		synchronized (nonFullGameLobbies) {
			getNonFullGameLobbies().remove(lobby);
		}
		GameCreatorQueue queue = GameCreatorQueue.getInstance();
		queue.addToQueue(lobby);
	}
	
	/**
	 * Returns a non full {@link GameLobby} with the same amount of players wanted by the other {@link User}s
	 * @param numberOfPlayersWanted
	 * @return
	 */
	public GameLobby getNonFullSearchableLobby(int numberOfPlayersWanted) {
		synchronized (nonFullGameLobbies) {
			Iterator<GameLobby> it = nonFullGameLobbies.iterator();
			while(it.hasNext()) {
				GameLobby lobby = it.next();
				int numPlayersWantedInLobby = lobby.getNumberOfPlayersWanted();
				
				// Continue if the lobby is full or its a private game
				if(lobby.isFull() || lobby.isPrivate())
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
	
	public GameLobby getGameLobbyHostedByUser(String username) {
		synchronized (nonFullGameLobbies) {
			Iterator<GameLobby> it = nonFullGameLobbies.iterator();
			while(it.hasNext()) {
				GameLobby lobby = it.next();
		
				if(lobby.isFull())
					continue;
				
				if(lobby.getHost() != null && lobby.getHost().getUsername().equals(username)) {
					return lobby;
				}
			}
		}
		return null;
	}
	
	public void addGameLobby(GameLobby gameLobby) {
		getNonFullGameLobbies().add(gameLobby);
	}
	
	public boolean removeGameLobby(GameLobby gameLobby) {
		synchronized (nonFullGameLobbies) {
			return nonFullGameLobbies.remove(gameLobby);
		}
	}
	
	public synchronized Set<GameLobby> getNonFullGameLobbies() {
		return nonFullGameLobbies;
	}

	public void setNonFullGameLobbies(Set<GameLobby> gameLobbies) {
		this.nonFullGameLobbies = gameLobbies;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
