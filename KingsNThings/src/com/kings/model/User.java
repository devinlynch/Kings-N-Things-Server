package com.kings.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.networking.UDPMessage;
import com.kings.networking.UDPSenderQueue;
import com.kings.networking.lobby.GameLobby;
import com.kings.networking.lobby.GameMatcher;

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
	@JsonIgnore
	private Set<Game> games;

	@JsonIgnore
	private List<SentMessage> sentMessages;
	
	public User() {
		setGames(new HashSet<Game>());
	}
	
	public User(String userId) {
		setGames(new HashSet<Game>());
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
		Integer port = getPort();
		String hostName = getHostName();
		if(port == null || hostName == null) {
			//TODO handle user who doesnt have these set...
			return;
		}
		
		UDPMessage udpMessage = new UDPMessage(getHostName(), getPort(), message);
		UDPSenderQueue.addMessagesToQueue(udpMessage);
	}
	
	/**
	 * Gets the active game that the user is in
	 * @return
	 */
	@JsonIgnore
	public Game getGame(){
		for(Game game : getGames()){
			if(game.isActive())
				return game;
		}
		return null;
	}
	
	@JsonIgnore
	public GameLobby getGameLobby() {
		try{
			return GameMatcher.getInstance().getUsersLobby(this);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SentMessage> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<SentMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}
	
	protected void addSentMessage(SentMessage sentMessage) {
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
}
