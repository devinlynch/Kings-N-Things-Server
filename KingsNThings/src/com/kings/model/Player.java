package com.kings.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.http.SentMessage;

public class Player extends AbstractSerializedObject {
	private String playerId;
	private String username;
	private String userId;
	private Rack rack1;
	private Rack rack2;
	private Set<GamePiece> gamePieces;
	@JsonIgnore
	private GameState gameState;
	private Set<SentMessage> sentMessages;
	private Integer gold;
	private Set<HexLocation> ownedLocations;
	
	public Player(User user, GameState gameState, String playerId) {
		this.playerId = playerId;
		this.gamePieces = new HashSet<GamePiece>();
		this.gameState=gameState;
		this.setUsername(user.getUsername());
		this.setUserId(user.getUserId());
		this.setSentMessages(new HashSet<SentMessage>());
		rack1 = new Rack(playerId+"_rack1");
		rack2 = new Rack(playerId+"_rack2");
		gold=0;
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
	
	@JsonIgnore
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Set<Map<String, Object>> getGamePiecesInSerializedFormat(){
		Iterator<GamePiece> it = getGamePieces().iterator();
		Set<Map<String, Object>> set = new HashSet<Map<String, Object>>();
		while(it.hasNext()) {
			set.add(it.next().toSerializedFormat());
		}
		return set;
	}

	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("playerId", playerId);
		map.put("username", username);
		map.put("userId", userId);
		map.put("gold", gold);
		map.put("rack1", rack1.toSerializedFormat());
		map.put("rack2", rack2.toSerializedFormat());
		map.put("gamePieces", getGamePiecesInSerializedFormat());
		return map;
	}
	
	public void assignGamePieceToPlayer(GamePiece gamePiece) {
		Player previousOwner = gamePiece.getOwner();
		if(previousOwner != null) {
			previousOwner.removeGamePiece(gamePiece);
		}
		gamePiece.setOwner(this);
		getGamePieces().add(gamePiece);
	}
	
	public void removeGamePiece(GamePiece gamePiece) {
		getGamePieces().remove(gamePiece);
	}
	
	public void assignGamePieceToPlayerRack(GamePiece gamePiece) {
		assignGamePieceToPlayer(gamePiece);
		if(!rack1.isFull()) {
			rack1.addGamePieceToLocation(gamePiece);
		} else if(!rack2.isFull()) {
			rack2.addGamePieceToLocation(gamePiece);
		} else{
			//TODO HANDLE FULL RACKS
		}
	}
	
	public void addGold(int gold) {
		this.gold = this.gold+gold;
	}

	public Set<HexLocation> getOwnedLocations() {
		return ownedLocations;
	}

	public void setOwnedLocations(Set<HexLocation> ownedLocations) {
		this.ownedLocations = ownedLocations;
	}

}
