package com.kings.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kings.networking.UDPMessage;
import com.kings.networking.UDPSenderQueue;

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

		UDPMessage udpMessage = new UDPMessage("localhost", 3004 , json);
		UDPSenderQueue.addMessagesToQueue(udpMessage);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
