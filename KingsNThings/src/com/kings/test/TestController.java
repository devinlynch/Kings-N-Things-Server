package com.kings.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kings.model.User;
import com.kings.networking.UDPMessage;
import com.kings.networking.UDPSenderQueue;
import com.kings.networking.lobby.UserWaiting;
import com.kings.networking.lobby.UserWaitingQueue;
import com.kings.networking.lobby.UserWaitingSearchGame;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/testJSON")
	public @ResponseBody String testJson() {
		Map<String, String> map = new HashMap<String,String>();
		map.put("such", "Team");
		
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			json = "{error: '" +e.toString() +"'}";
		}
		return json;
	}
	
	@RequestMapping("/testConnectAndSend")
	public @ResponseBody String testConnectAndSend(
			@RequestParam String host, 
			@RequestParam int port, 
			@RequestParam String message) {
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("type", "test");

		UDPMessage udpMessage = new UDPMessage(host, port, message);
		UDPSenderQueue.addMessagesToQueue(udpMessage);
		
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			json = "{error: '" +e.toString() +"'}";
		}
		return json;
	}
	
	@RequestMapping("/addTestUserWaiting")
	public @ResponseBody String addTestUserWaiting(@RequestParam int numberOfPreferredPlayers) {
		User testUser = new User();
		testUser.setUserId(""+new Date().getTime());
		testUser.setUsername("t-"+new Date().getTime());
		UserWaiting userWaiting = new UserWaiting(testUser, numberOfPreferredPlayers);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		return "Added";
	}
	
	@RequestMapping("/testJoinGame")
	public @ResponseBody String addTestUserWaiting(@RequestParam String usernameOfHost) {
		User testUser = new User();
		testUser.setUserId(""+new Date().getTime());
		testUser.setUsername("t-"+new Date().getTime());
		UserWaiting userWaiting = new UserWaitingSearchGame(testUser, usernameOfHost);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		return "Added";
	}
	

}
