package com.kings;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			json = e.toString();
		}
		return json;
	}
	
}
