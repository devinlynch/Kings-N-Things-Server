package com.kings.model;

public class GamePiece {
	private String id;
	private BoardContainer location;
	private Player owner;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BoardContainer getLocation() {
		return location;
	}
	public void setLocation(BoardContainer location) {
		this.location = location;
	}
}
