package com.kings.http;

import java.util.Date;

public class SentMessage {
	private Date sentDate;
	private String json;
	private String type;
	private String sentToUserId;
	
	public SentMessage(String type, String json, String userId){
		setSentDate(new Date());
		setType(type);
		setJson(json);
		setSentToUserId(userId);;
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

	public String getSentToUserId() {
		return sentToUserId;
	}

	public void setSentToUserId(String sentToUserId) {
		this.sentToUserId = sentToUserId;
	}
}
