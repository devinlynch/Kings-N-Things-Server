package com.kings.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kings.http.SentMessage;

public class Player {
	private String playerId;
	private String userId;
	private Rack rack1;
	private Rack rack2;
	private Set<GamePiece> gamePieces;
	private GameState gameState;
	private Set<SentMessage> sentMessages;
	private Integer gold;
	
	public Player(User user, GameState gameState, String playerId) {
		this.playerId = playerId;
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

	public Rack getRack1() {
		return rack1;
	}

	public void setRack1(Rack rack1) {
		this.rack1 = rack1;
	}

	public Rack getRack2() {
		return rack2;
	}

	public void setRack2(Rack rack2) {
		this.rack2 = rack2;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}
	
}
