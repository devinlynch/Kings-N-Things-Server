package com.kings.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kings.http.SentMessage;
import com.kings.util.Utils;

public class Player {
	private String playerId;
	private String userId;
	private Set<Rack> racks;
	private Set<GamePiece> gamePieces;
	private GameState gameState;
	private Set<SentMessage> sentMessages;
	
	public Player(User user, GameState gameState, String playerId) {
		this.playerId = playerId;
		this.racks = new HashSet<Rack>();
		this.gamePieces = new HashSet<GamePiece>();
		this.gameState=gameState;
		this.setUserId(user.getUserId());
		this.setSentMessages(new HashSet<SentMessage>());
	}
	
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public Set<Rack> getRacks() {
		return racks;
	}
	public void setRacks(Set<Rack> racks) {
		this.racks = racks;
	}
	public Set<GamePiece> getGamePieces() {
		return gamePieces;
	}
	public void setGamePieces(Set<GamePiece> gamePieces) {
		this.gamePieces = gamePieces;
	}
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<SentMessage> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(Set<SentMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}
	
	public Set<SentMessage> getSentMessagesAfterDate(Date date) {
		Set<SentMessage> set = new HashSet<SentMessage>();
		for(SentMessage msg : sentMessages) {
			if(msg.getSentDate().after(date))
				set.add(msg);
		}
		return set;
	}

	public void addSentMessage(SentMessage sentMessage) {
		getSentMessages().add(sentMessage);
	}
	
	public void addSentMessages(Set<SentMessage> sentMessages) {
		getSentMessages().addAll(sentMessages);
	}
}
