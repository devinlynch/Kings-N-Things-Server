package com.kings.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HexLocation extends BoardLocation {
	private HexTile hexTile;
	private Player owner;
	private Set<Stack> stacks;
	
	public HexLocation(String id) {
		super(id, "Hex Location");
		setName("Hex Location");
		stacks = new HashSet<Stack>();
	}

	public HexTile getHexTile() {
		return hexTile;
	}

	public void setHexTile(HexTile hexTile) {
		BoardLocation previousLocation = hexTile.getLocation();
		if(previousLocation!=null) {
			if(previousLocation instanceof HexLocation) {
				((HexLocation) previousLocation).setHexTile(null);
			} else{
				previousLocation.removeGamePieceFromLocation(hexTile);
			}
		}
		hexTile.setLocation(this);
		this.hexTile = hexTile;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public Set<Map<String,Object>> getStacksInSerializedFormat() {
		Set<Map<String,Object>> set = new HashSet<Map<String,Object>>();
		
		for(Stack s: getStacks())
			set.add(s.toSerializedFormat());
		return set;
	}
	
	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = super.toSerializedFormat();
		map.put("ownerId", owner != null ? owner.getPlayerId() : null);
		map.put("stacks", getStacksInSerializedFormat());
		return map;
	}

	public Set<Stack> getStacks() {
		return stacks;
	}

	public void setStacks(Set<Stack> stacks) {
		this.stacks = stacks;
	}

}
