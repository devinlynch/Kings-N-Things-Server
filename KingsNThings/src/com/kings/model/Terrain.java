package com.kings.model;

public class Terrain {
	private String name;
	private String id;
	//TODO GABE : add all terrain types
	public final static Terrain JUNGLE_TERRAIN = new Terrain("Jungle", "jungle-tile");
	public final static Terrain PLAINS_TERRAIN = new Terrain("Plains", "plains-tile");
	public final static Terrain FROZEN_TERRAIN = new Terrain("Frozen", "frozen-tile");
	public final static Terrain MOUNTAIN_TERRAIN = new Terrain("Mountain", "mountain-tile");
	public final static Terrain SWAMP_TERRAIN = new Terrain("Swamp", "swamp-tile");
	public final static Terrain FOREST_TERRAIN = new Terrain("Forest", "forest-tile");
	public final static Terrain DESERT_TERRAIN = new Terrain("Desert", "desert-tile");
	public final static Terrain SEA_TERRAIN = new Terrain("Sea", "sea-tile");
	
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
