package com.kings.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.http.HttpResponseMessage;

public class Game {
	private String gameId;
	private Set<User> users;
	private Date startedDate;
	private GameState gameState;
	private String createdFromGameLobbyId;
	private boolean active;
	
	public Game() {
		setUsers(new HashSet<User>());
		gameState = new GameState();
	}
	
	public void addUsers(Set<User> players) {
		getUsers().addAll(players);
	}
	public void addUser(User player) {
		getUsers().add(player);
	}
	
	public void start() {
		//TODO
		// This is going to handle actually starting the game
		// Right now it is being called from the GameCreatorQueue, so the users are all assigned to this game and now all logic of creating a game
		// and sending messages to the client needs to happen here
		
		GameState gameState = GameState.createGameStateFromGame(this);
		setGameState(gameState);
		
		
		setStartedDate(new Date());
		setActive(true);
		
		for(User user : users) {
			sendGameStartedMessageToUser(user);
		}
	}

	public void sendGameStartedMessageToUser(User user) {
		HttpResponseMessage message = getGameStartedMessage();
		user.sendJSONMessage(message.toJson());
	}
	
	@JsonIgnore
	public HttpResponseMessage getGameStartedMessage(){
		HttpResponseMessage message = new HttpResponseMessage();
		message.setType("gameStarted");
		message.addToData("game", this);
		return message;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public String getCreatedFromGameLobbyId() {
		return createdFromGameLobbyId;
	}

	public void setCreatedFromGameLobbyId(String createdFromGameLobbyId) {
		this.createdFromGameLobbyId = createdFromGameLobbyId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
