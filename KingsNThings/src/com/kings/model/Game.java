package com.kings.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.database.DataAccess;
import com.kings.http.GameMessage;
import com.kings.http.HttpResponseMessage;

public class Game {
	private String gameId;
	private Set<User> users;
	private Date startedDate;
	@JsonIgnore
	private GameState gameState;
	private String createdFromGameLobbyId;
	private boolean active;
	private boolean isDemo;
	private List<SentMessage> sentMessages;
	private List<GameChatMessage> chatMessages;
	
	public Game() {
		setUsers(new HashSet<User>());
		gameState = new GameState();
		sentMessages = new ArrayList<SentMessage>();
		chatMessages = new ArrayList<GameChatMessage>();
		//setDemo(true);
	}
	
	public void addUsers(Set<User> players) {
		getUsers().addAll(players);
	}
	public void addUser(User player) {
		getUsers().add(player);
	}
	
	public GameState start() throws Exception {
		//TODO
		// This is going to handle actually starting the game
		// Right now it is being called from the GameCreatorQueue, so the users are all assigned to this game and now all logic of creating a game
		// and sending messages to the client needs to happen here
		
		return handleStart(false);
	}
	
	public GameState startAsTest() throws Exception {
		return handleStart(true);
	}
	
	protected GameState handleStart(boolean isTest) throws Exception {
		GameState gameState;
		gameState = GameState.createGameStateFromGame(this);
		setGameState(gameState);
		setStartedDate(new Date());
		setActive(true);
		
		for(User user : users) {
			sendGameStartedMessageToUser(user);
		}
				
		gameState.setTestMode(isTest);
		gameState.startGame();
		
		return gameState;
	}

	public void sendGameStartedMessageToUser(User user) {
		HttpResponseMessage message = getGameStartedMessage();
		user.sendMessage(message, new DataAccess());
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

	@JsonIgnore
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

	public boolean isDemo() {
		return isDemo;
	}

	public void setDemo(boolean isDemo) {
		this.isDemo = isDemo;
	}

	public List<SentMessage> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<SentMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}
	
	protected void addSentMessage(SentMessage sentMessage) {
		sentMessage.setGame(this);
		getSentMessages().add(sentMessage);
	}
	
	protected void addSentMessages(Set<SentMessage> sentMessages) {
		for(SentMessage msg : sentMessages)
			addSentMessage(msg);
	}
	
	public Set<SentMessage> getSentMessageAfterDate(Date d) {
		Iterator<SentMessage> it = getSentMessages().iterator();
		Set<SentMessage> messages = new HashSet<SentMessage>();
		while(it.hasNext()) {
			SentMessage msg = it.next();
			if(d.before(msg.getSentDate())){
				messages.add(msg);
			}
		}
		return messages;
	}
	
	/**
	 * Ends the game.  <b>Be careful when calling this, it destroys everything!  The game state will be destroyed FOREVER</b>
	 */
	public void end(boolean shouldAlertUsers) {
		setActive(false);
		
		for(SentMessage m : getSentMessages()) {
			m.setQueued(false);
		}
		
		if(shouldAlertUsers)
			alertUsersThatGameIsOver();
	}
	
	public void alertUsersThatGameIsOver() {
		for(User u : users) {
			sendGameOverMessageToUser(u);
		}
	}
	
	
	public void sendGameOverMessageToUser(User user) {
		HttpResponseMessage message = getGameOverMessage();
		user.sendMessage(message, new DataAccess());
	}
	
	@JsonIgnore
	public HttpResponseMessage getGameOverMessage(){
		HttpResponseMessage message = new HttpResponseMessage();
		message.setType("gameOver");
		message.addToData("gameId", this.getGameId());
		
		return message;
	}
	
	public HttpResponseMessage sendChatMessage(User fromUser, String message, DataAccess access) {
		GameChatMessage msg = new GameChatMessage();
		msg.setGame(this);
		msg.setUser(fromUser);
		msg.setMessage(message);
		msg.setCreatedDate(new Date());
		addChatMessage(msg);
		
		HttpResponseMessage jsonMessage = new HttpResponseMessage();
		jsonMessage.setType("chatMessage");
		jsonMessage.addToData("message", msg.toSerializedFormat());
		
		sendMessageToAllUsers(jsonMessage, access);
		
		return jsonMessage;
	}

	public List<GameChatMessage> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(List<GameChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}
	
	public void addChatMessage(GameChatMessage msg) {
		chatMessages.add(msg);
	}
	
	public void sendMessageToAllUsers(HttpResponseMessage message, DataAccess access) {
		for(User user : getUsers()) {
			user.sendMessage(message, access);
		}
	}
	
}
