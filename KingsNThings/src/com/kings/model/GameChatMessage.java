package com.kings.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GameChatMessage extends AbstractSerializedObject {
	private Game game;
	private String gameChatMessageId;
	private User user;
	private String message;
	private Date createdDate;
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getGameChatMessageId() {
		return gameChatMessageId;
	}
	public void setGameChatMessageId(String gameChatMessageId) {
		this.gameChatMessageId = gameChatMessageId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameChatMessageId", gameChatMessageId);
		map.put("userId", user.getUserId());
		map.put("gameId", game.getGameId());
		map.put("message", message);
		map.put("createdDate", createdDate);
		return map;
	}
	
}
