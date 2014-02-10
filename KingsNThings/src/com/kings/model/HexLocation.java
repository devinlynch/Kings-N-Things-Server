package com.kings.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kings.util.Utils;

public class HexLocation extends BoardLocation {
	private HexTile hexTile;
	private Player owner;
	private Set<Stack> stacks;
	private int hexNumber;
	
	public HexLocation(String id, int hexNumber) {
		super(id, "Hex Location");
		setName("Hex Location");
		stacks = new HashSet<Stack>();
		this.setHexNumber(hexNumber);
	}

	public HexTile getHexTile() {
		return hexTile;
	}

	public void setHexTile(HexTile hexTile) {
		if(hexTile==null){
			this.hexTile=null;
			return;
		}
		
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
	
	public void addStack(Stack stack) {
		HexLocation previousLocation = stack.getHexLocation();
		if (previousLocation != null)
			previousLocation.getStacks().remove(stack);
		getStacks().add(stack);
		stack.setHexLocation(this);
	}
	
	public Stack createAndAddNewStackWithPieces(Player owner, Set<GamePiece> gamePieces) {
		Stack stack = new Stack(Utils.generateRandomId("stack"));
		stack.setOwner(owner);
		stack.addGamePiecesToLocation(gamePieces);
		addStack(stack);
		return stack;
	}
	
	/**
	 * Handles the given player capturing this hex location
	 * @param player
	 */
	public void capture(Player player){
		Player previousOwner = getOwner();
		if(previousOwner != null) {
			previousOwner.getOwnedLocations().remove(this);
		}
		
		player.getOwnedLocations().add(this);
		this.setOwner(player);
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
		map.put("hexTile", hexTile != null ? hexTile.toSerializedFormat() : null);
		map.put("hexNumber", getHexNumber());
		return map;
	}

	public Set<Stack> getStacks() {
		return stacks;
	}

	public void setStacks(Set<Stack> stacks) {
		this.stacks = stacks;
	}

	public int getHexNumber() {
		return hexNumber;
	}

	public void setHexNumber(int hexNumber) {
		this.hexNumber = hexNumber;
	}
	
	public List<Player> getPlayersWhoAreOnMe() {
		List<Player> players = new ArrayList<Player>();
		for(GamePiece gp: getGamePieces()) {
			Player p = gp.getOwner();
			if(!players.contains(p)) {
				players.add(p);
			}
		}
		
		for(Stack stack: getStacks()) {
			Player p = stack.getOwner();
			if(!players.contains(p)) {
				players.add(p);
			}
		}
		return players;
	}
	
	
	public Set<Creature> getCreaturePiecesForPlayer(Player p) {
		Set<Creature> set = new HashSet<Creature>();
		Iterator<GamePiece> it = getGamePieces().iterator();
		while(it.hasNext()) {
			GamePiece gp = it.next();
			if(gp instanceof Creature && p.equals(gp.getOwner()))
				set.add((Creature)gp);
		}
		return set;
	}
	
	public Set<GamePiece> getPiecesForPlayer(Player p) {
		Set<GamePiece> set = new HashSet<GamePiece>();
		Iterator<GamePiece> it = getGamePieces().iterator();
		while(it.hasNext()) {
			GamePiece gp = it.next();
			if(p.equals(gp.getOwner()))
				set.add(gp);
		}
		return set;
	}
	
	public Set<Stack> getStacksForPlayer(Player p) {
		Set<Stack> set = new HashSet<Stack>();
		Iterator<Stack> it = getStacks().iterator();
		while(it.hasNext()) {
			Stack st = it.next();
			if(p.equals(st.getOwner()))
				set.add(st);
		}
		return set;
	}

}
