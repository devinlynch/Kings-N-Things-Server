package com.kings.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BoardLocation extends AbstractSerializedObject {
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
	
	/**
	 * Adds the given piece to this location and updates the piece state
	 * @param piece
	 */
	public void addGamePieceToLocation(GamePiece piece) {
		gamePieces.add(piece);
		piece.setLocation(this);
	}
	
	/**
	 * Adds the given pieces to this location and updates the pieces states
	 * @param piece
	 */
	public void addGamePiecesToLocation(Set<GamePiece> pieces) {
		Iterator<GamePiece> it = pieces.iterator();
		while(it.hasNext()) {
			addGamePieceToLocation(it.next());
		}
	}

	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("locationId", id);
		Set<Map<String, Object>> pieces = new HashSet<Map<String, Object>>();
		Iterator<GamePiece> it = getGamePieces().iterator();
		while(it.hasNext()) {
			GamePiece piece = it.next();
			pieces.add(piece.toSerializedFormat());
		}
		map.put("gamePieces", pieces);
		
		
		return map;
	}
}
