package com.kings.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseData {
	private Map<Object, Object> map;

	public HttpResponseData() {
		map = new HashMap<Object, Object>();
	}
	
	public Map<Object, Object> getMap() {
		return map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}
	
	public void put(Object key, Object value) {
		getMap().put(key, value);
	}
	
	public void remove(Object key) {
		getMap().remove(key);
	}
	
}
