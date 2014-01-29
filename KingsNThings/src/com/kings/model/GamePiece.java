package com.kings.model;

public class GamePiece {
	private String id;
	private BoardLocation location;
	private Player owner;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BoardLocation getLocation() {
		return location;
	}
	public void setLocation(BoardLocation location) {
		this.location = location;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
