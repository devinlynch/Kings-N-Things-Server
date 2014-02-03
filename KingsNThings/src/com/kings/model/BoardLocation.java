package com.kings.model;

import java.util.HashSet;
import java.util.Set;

public class BoardLocation {
	private String id;
    private String name;
    private Set<GamePiece> gamePieces;
    
    public BoardLocation(String id, String name) {
    	this.gamePieces = new HashSet<GamePiece>();
    	this.id=id;
    	this.name=name;
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<GamePiece> getGamePieces() {
		return gamePieces;
	}
	public void setGamePieces(Set<GamePiece> gamePieces) {
		this.gamePieces = gamePieces;
	}
}
