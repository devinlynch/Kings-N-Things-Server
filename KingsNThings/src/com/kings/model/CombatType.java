package com.kings.model;

import java.util.HashMap;

public class CombatType {
	private String name;
	private String id; 
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public HashMap<String, CombatType> getMapOfInstances(){
		// TODO GABE
		return null;
	}
	
	/**
	 * Given an id, returns a new instance of the corresponding GamePiece class
	 * @param id
	 * @return
	 */
	public CombatType getInstanceForId(String id) {
		return getMapOfInstances().get(id);
	}

}
