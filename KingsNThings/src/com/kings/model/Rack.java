package com.kings.model;

import java.util.Map;

public class Rack extends BoardLocation {
	//TODO: MAKE SURE CORRECT LIMIT
	public static final int LIMIT = 10;
	
	public Rack(String id) {
		super(id, "Rack");
		setName("Rack");
		this.limit=LIMIT;
	}

	private int limit;
	private Player owner;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = super.toSerializedFormat();
		map.put("ownerId", owner != null ? owner.getPlayerId() : null);
		return map;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean isFull() {
		return getGamePieces().size() >= getLimit();
	}
}
