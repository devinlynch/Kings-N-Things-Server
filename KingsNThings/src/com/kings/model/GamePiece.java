package com.kings.model;

import java.util.HashMap;
import java.util.Map;

public abstract class GamePiece extends AbstractSerializedObject {
	private String id;
	private BoardLocation location;
	private Player owner;
	private String name;
	
	public GamePiece(String id, String name) {
		this.id=id;
		this.name=name;
	}
	
	/**
	 * Every actual game piece type needs to implement this.  This retusn a HashMap
	 * containing the keys of game piece ID's mapped to the instances of the piece.
	 * <br /><br/>
	 * For example, the fort class would implement this method returning a HashMap of size
	 * 4 containing the IDs of the 4 different fort types mapped to a instance of a {@link Fort}.  
	 *
	 * @return
	 */
	public abstract HashMap<String, GamePiece> getMapOfInstances();
	
	/**
	 * Given an id, returns a new instance of the corresponding GamePiece class
	 * @param id
	 * @return
	 */
	public GamePiece getInstanceForId(String id) {
		return getMapOfInstances().get(id);
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
		return map;
	}
}
