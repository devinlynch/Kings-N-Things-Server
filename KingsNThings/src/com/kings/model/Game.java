package com.kings.model;

import java.util.HashSet;
import java.util.Set;

public class Game {
	private Set<User> users;
	
	public Game() {
		users = new HashSet<User>();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUsers(Set<User> users) {
		getUsers().addAll(users);
	}
	public void addUser(User user) {
		getUsers().add(user);
	}
}
