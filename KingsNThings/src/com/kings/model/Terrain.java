package com.kings.model;

public class Terrain {
	private String name;
	private String id;
	public final static Terrain JUNGLE_TERRAIN = new Terrain("Jungle", "TerrainJungle");
	//TODO GABE : add all terrain types
	
	
	public Terrain(String name, String id) {
		this.id=id;
		this.name=name;
	}
	
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
}
