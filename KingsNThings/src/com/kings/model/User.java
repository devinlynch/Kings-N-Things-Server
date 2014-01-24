package com.kings.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.networking.UDPMessage;
import com.kings.networking.UDPSenderQueue;
import com.kings.networking.lobby.GameLobby;

public class User {
	
	// Columns
	private String userId;
	private String username;
	private Integer port;
	private String hostName;
	private Date createdDateTime;
	private Date lastUpdate;
	
	@JsonIgnore
	private String password;
	
	// DB Relations
	private Set<Game> games;

	// Cache only values
	private GameLobby gameLobby;
	
	//TODO
	private MatchmakingStatus matchmakingStatus;
	public enum MatchmakingStatus{
		SEARCHING_FOR_GAME,
		IN_LOBBY_WAITING_FOR_MORE_PLAYERS,
		GAME_STARTING,
		IN_GAME
	}
	
	public User() {
	}
	
	public User(String userId) {
		this.setUserId(userId);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public GameLobby getGameLobby() {
		return gameLobby;
	}
	public void setGameLobby(GameLobby gameLobby) {
		this.gameLobby = gameLobby;
	}
	public Set<Game> getGames() {
		return games;
	}
	public void setGames(Set<Game> games) {
		this.games = games;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof User))
			return false;
		
		User comparingUser = (User) o;
		
		String thisId = this.getUserId();
		String thatId = comparingUser.getUserId();
		
		if(thisId == null && thatId == null)
			return false;
		
		return thisId != null && thisId.equals(thatId);
	}
	
	public void sendJSONMessage(String message) {
		UDPMessage udpMessage = new UDPMessage(getHostName(), getPort(), message);
		UDPSenderQueue.addMessagesToQueue(udpMessage);
	}
	
}
