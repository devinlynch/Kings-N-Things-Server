package com.kings.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kings.database.DataAccess;
import com.kings.http.HttpResponseMessage;
import com.kings.model.User;
import com.kings.networking.MessageSender;
import com.kings.networking.UDPMessage;
import com.kings.networking.UDPSenderQueue;
import com.kings.networking.lobby.GameLobby;
import com.kings.networking.lobby.UserWaiting;
import com.kings.networking.lobby.exceptions.GameLobbyAlreadyFullException;

public class TestClientReactor {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("type", "TEST");		
		
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			json = "{error: '" +e.toString() +"'}";
		}

		//UDPMessage udpMessage = new UDPMessage("localhost", 3004 , json);
		//UDPSenderQueue.addMessagesToQueue(udpMessage);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test2() throws IOException, GameLobbyAlreadyFullException {
		HttpResponseMessage m = new HttpResponseMessage();
		GameLobby lobby = new GameLobby();
		lobby.setNumberOfPlayersWanted(4);
		lobby.setPrivate(true);
		User u = new User();
		u.setUsername("test");
		UserWaiting uw = new UserWaiting();
		uw.setUser(u);
		lobby.setHost(u);
		lobby.addUserWaiting(uw);
		m.setType("joinLobby");
		m.addToData("joinedLobby", lobby);
		
		UDPMessage udpMessage = new UDPMessage("localhost", 3004 , m.toJson());
		MessageSender.sendUDPMessage(udpMessage);
		
	}
	
	@Test
	public void test3() {
		User user = DataAccess.getInstance().get(User.class, "10");
		System.out.println(user.getHostName());
	}

}
