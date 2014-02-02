package com.kings.model;

public class HexLocation extends BoardLocation {
	private HexTile hexTile;
	private Player owner;
	
	public HexLocation(String id) {
		super(id);
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

}
