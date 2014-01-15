package com.kings.test.prototype.JSON;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJsonSerialize {

	public static void main(String[] args)  {
		ClassA a = ClassA.testCreate();
		ObjectMapper mapper = new ObjectMapper();
		
		String json= null;
		try {
			json = mapper.writeValueAsString(a);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ClassA _a = null;
		try {
			_a = mapper.readValue(json, ClassA.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(json);
	}
}
