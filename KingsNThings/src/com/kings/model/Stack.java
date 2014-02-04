package com.kings.model;

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

	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
