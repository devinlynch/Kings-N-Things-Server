package com.kings.http;

public class KingsAndThingsSession {
	private String userId;
	public static final String KAT_SESS_NAME = "com.kings.controllers.KingsAndThingsSession";
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
