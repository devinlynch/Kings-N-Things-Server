package com.kings.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Game {
	private Set<Player> players;
	
	public Game() {
		setPlayers(new HashSet<Player>());
	}
	
	public void addPlayers(Set<Player> players) {
		getPlayers().addAll(players);
	}
	public void addPlayer(Player player) {
		getPlayers().add(player);
	}
	
	public void start() {
		//TODO
		// This is going to handle actually starting the game
		// Right now it is being called from the GameCreatorQueue, so the users are all assigned to this game and now all logic of creating a game
		// and sending messages to the client needs to happen here
		
		
		// For testing, this isnt actually whats going to be sent
		for(Player player : players) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> map = new HashMap<String,String>();
				map.put("event", "gameStarted");
				map.put("game", mapper.writeValueAsString(this));
				String json = mapper.writeValueAsString(map);
				
				User user = player.getUser();
				user.sendJSONMessage(json);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	
}
