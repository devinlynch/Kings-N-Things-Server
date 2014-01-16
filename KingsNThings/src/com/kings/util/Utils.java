package com.kings.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	/**
	 * Returns true if any of the given strings are null or empty
	 * @param strings
	 * @return
	 */
	public static boolean isNullOrEmpty(String... strings) {
		if(strings == null)
			return true;
		for(String s : strings) {
			if(s==null || s.isEmpty())
				return true;
		}
		return false;
	}
	
	public static String toJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			json = "{error: '" +e.toString() +"'}";
		}
		return json;
	}
}
