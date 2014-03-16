package com.kings.model;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SentMessage {
	private Date sentDate;
	private String messageId;
	private String json;
	private String type;
	private boolean queued;
	
	@JsonIgnore
	private User sentToUser;
	
	@JsonIgnore
	private Game game;
	
	public SentMessage(){
		
	}
	
	public SentMessage(String type, String json, User user, String messageId){
		setSentDate(new Date());
		setType(type);
		setJson(json);
		setSentToUser(user);
		setMessageId(messageId);
		setQueued(true);
	}
	
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public User getSentToUser() {
		return sentToUser;
	}

	public void setSentToUser(User sentToUser) {
		this.sentToUser = sentToUser;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public HashMap<String, Object> toSerializedFormat() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("gameId", game != null ? game.getGameId() : "");
		map.put("userId", sentToUser.getUserId());
		map.put("json", json);
		map.put("messageId", messageId);
		map.put("sentDate", sentDate);
		map.put("type", type);
		return map;
	}

	public boolean isQueued() {
		return queued;
	}

	public void setQueued(boolean queued) {
		this.queued = queued;
	}

}
