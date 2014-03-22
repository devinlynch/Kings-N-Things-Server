package com.kings.model;

import java.util.HashMap;
import java.util.Map;

public abstract class GamePiece extends AbstractSerializedObject {
	private String id;
	private BoardLocation location;
	private Player owner;
	private String name;
	private boolean netralized;
	
	public GamePiece(String id, String name) {
		this.id=id;
		this.name=name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BoardLocation getLocation() {
		return location;
	}
	public void setLocation(BoardLocation location) {
		this.location = location;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Map<String,Object> toSerializedFormat() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", getId());
		map.put("locationId", location!= null ? location.getId() : null);
		map.put("ownerId", owner != null ? owner.getPlayerId() : null);
		map.put("neutralized", netralized);
		return map;
	}
	
	public void neutralize() {
		setNetralized(true);
	}

	public boolean isNetralized() {
		return netralized;
	}

	public void setNetralized(boolean netralized) {
		this.netralized = netralized;
	}
	
}
