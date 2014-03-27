package com.kings.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kings.model.Counter;
import com.kings.model.GamePiece;

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
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String json;
		try {
			json = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			json = "{error: '" +e.toString() +"'}";
		}
		return json;
	}
	
	private static String alphabet="abcdefghijklmnopqrstuvwxyz1234567890";
	public static String randomString(int length){
		String s="";
		for(int i=0; i<length; i++) {
			s+=alphabet.charAt(randInt(0, alphabet.length()-1));
		}
		return s;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * Generates a random ID which is <b>probably</b> going to be unique since it
	 * uses a timestamp.
	 * @return
	 */
	public synchronized static String generateRandomId() {
		String id= ""+new Date().getTime();
		id += randomString(randInt(10, 20));
		return id;
	}
	
	/**
	 * Generates a random id with the given string in the start
	 * @param start
	 * @return
	 */
	public synchronized static String generateRandomId(String start) {
		return start+generateRandomId();
	}
	
	public static Set<String> gamePieceSetToSetOfIds(Set<Counter> set) {
		Iterator<Counter> it = set.iterator();
		Set<String> returningSet = new HashSet<String>();
		while(it.hasNext()) {
			returningSet.add((it.next()).getId());
		}
		return returningSet;
	}
}
