package com.kings.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kings.networking.UDPMessage;
import com.kings.networking.UDPSenderQueue;

@Controller
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
	

}
