package com.kings.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public void start() {
		//TODO
		// This is going to handle actually starting the game
		// Right now it is being called from the GameCreatorQueue, so the users are all assigned to this game and now all logic of creating a game
		// and sending messages to the client needs to happen here
		
		
		// For testing, this isnt actually whats going to be sent
		for(User user : users) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> map = new HashMap<String,String>();
				map.put("event", "joinedGame");
				map.put("game", mapper.writeValueAsString(this));
				String json = mapper.writeValueAsString(map);
				user.sendJSONMessage(json);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
