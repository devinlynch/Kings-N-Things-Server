package com.kings.model;

public class Player {
	private User user;
	
	public Player(User user) {
		this.user=user;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
