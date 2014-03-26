package com.kings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
		BoardLocation previousLocation = piece.getLocation();
		if(previousLocation != null)
			previousLocation.removeGamePieceFromLocation(piece);
		gamePieces.add(piece);
		piece.setLocation(this);
	}
	
	protected void removeGamePieceFromLocation(GamePiece piece) {
		gamePieces.remove(piece);
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
		map.put("name", name);
		
		return map;
	}
	
	/**
	 * Gets all thing pieces contained in this location
	 * @return
	 */
	public Set<Thing> getThings() {
		Set<Thing> things = new HashSet<Thing>();
		for(GamePiece p: getGamePieces()) {
			if(p instanceof Thing) {
				things.add((Thing)p);
			}
		}
		return things;
	}
	
	public Set<Creature> getCreatures() {
		Set<Creature> things = new HashSet<Creature>();
		for(GamePiece p: getGamePieces()) {
			if(p instanceof Creature) {
				things.add((Creature)p);
			}
		}
		return things;
	}
	
	public List<SpecialCharacter> getSpecialCharacters() {
		List<SpecialCharacter> things = new ArrayList<SpecialCharacter>();
		for(GamePiece p: getGamePieces()) {
			if(p instanceof SpecialCharacter) {
				things.add((SpecialCharacter)p);
			}
		}
		return things;
	}
	
	public List<CityVill> getCityVills() {
		List<CityVill> things = new ArrayList<CityVill>();
		for(GamePiece p: getGamePieces()) {
			if(p instanceof CityVill) {
				things.add((CityVill)p);
			}
		}
		return things;
	}
	
	public List<Fort> getForts() {
		List<Fort> things = new ArrayList<Fort>();
		for(GamePiece p: getGamePieces()) {
			if(p instanceof Fort) {
				things.add((Fort)p);
			}
		}
		return things;
	}
	
	public GamePiece getGamePieceById(String id) {
		for(GamePiece p: getGamePieces()) {
			if(id.equals(p.getId())) {
				return p;
			}
		}
		return null;
	}
	
}
