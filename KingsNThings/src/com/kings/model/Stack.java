package com.kings.model;

public class Stack extends BoardLocation {
	private HexLocation hexLocation;
	
	public Stack(String id) {
		super(id, "Stack");
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
}
