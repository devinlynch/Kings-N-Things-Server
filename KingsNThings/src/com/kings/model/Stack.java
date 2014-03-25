package com.kings.model;

import java.util.Iterator;
import java.util.Map;


public class Stack extends BoardLocation {
	private HexLocation hexLocation;
	private Player owner;
	
	public Stack(String id) {
		super(id, "Stack");
	}
	
	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = super.toSerializedFormat();
		map.put("ownerId", owner != null ? owner.getPlayerId() : null);
		map.put("hexLocationId", hexLocation != null ? hexLocation.getId() : null);
		return map;
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

	public Player getOwner() {
		return owner;
	}
	
	@Override
	public void addGamePieceToLocation(GamePiece piece) {
		super.addGamePieceToLocation(piece);
		
		if(getOwner() != null) {
			getOwner().assignGamePieceToPlayer(piece);
		}
	}

	
	/**
	 * Assigns the owner of the stack AS WEL AS assigns all pieces in the stack to the given player
	 * @param owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
		Iterator<GamePiece> it = getGamePieces().iterator();
		while(it.hasNext()) {
			owner.assignGamePieceToPlayer(it.next());
		}
	}
}
