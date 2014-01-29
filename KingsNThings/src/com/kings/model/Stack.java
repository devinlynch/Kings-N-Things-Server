package com.kings.model;

import java.util.HashSet;
import java.util.Set;

public class Stack extends BoardLocation {
	private HexLocation hexLocation;
	private Set<GamePiece> gamePieces;
	
	public Stack(String id) {
		super(id);
		setName("Stack");
		this.gamePieces = new HashSet<GamePiece>();
	}
	
	public Stack(String id, HexLocation hexLocation) {
		this(id);
		setHexLocation(hexLocation);
	}
	
	public HexLocation getHexLocation() {
		return hexLocation;
	}
	public void setHexLocation(HexLocation hexLocation) {
		this.hexLocation = hexLocation;
	}
	public Set<GamePiece> getGamePieces() {
		return gamePieces;
	}
	public void setGamePieces(Set<GamePiece> gamePieces) {
		this.gamePieces = gamePieces;
	}
}
