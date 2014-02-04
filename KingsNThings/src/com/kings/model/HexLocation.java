package com.kings.model;

import java.util.Map;

public class HexLocation extends BoardLocation {
	private HexTile hexTile;
	private Player owner;
	
	public HexLocation(String id) {
		super(id, "Hex Location");
		setName("Hex Location");
	}

	public HexTile getHexTile() {
		return hexTile;
	}

	public void setHexTile(HexTile hexTile) {
		this.hexTile = hexTile;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = super.toSerializedFormat();
		map.put("ownerId", owner != null ? owner.getPlayerId() : null);
		return map;
	}

}
