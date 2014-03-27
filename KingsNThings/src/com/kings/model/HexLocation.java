package com.kings.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	
	
	public Set<Creature> getCreaturePiecesForPlayerIncludingCreaturesInStack(Player p) {
		Set<Creature> set = new HashSet<Creature>();
		Iterator<GamePiece> it = getGamePieces().iterator();
		while(it.hasNext()) {
			GamePiece gp = it.next();
			if(gp instanceof Creature && p.equals(gp.getOwner()))
				set.add((Creature)gp);
		}
		
		Iterator<Stack> it2 = getStacks().iterator();
		while(it2.hasNext()) {
			Stack st = it2.next();
			if(p.equals(st.getOwner()))
				set.addAll(st.getCreatures());
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
	
	public Set<GamePiece> getAllPiecesOnHexIncludingPiecesInStacks() {
		Set<GamePiece> set = new HashSet<GamePiece>(getGamePieces());
		
		Iterator<Stack> it = getStacks().iterator();
		while(it.hasNext()) {
			Stack st = it.next();
			set.addAll(st.getGamePieces());
		}
		
		return set;
	}
	
	public Set<GamePiece> getAllPiecesOnHexIncludingPiecesInStacksForPlayer(Player p) {
		Set<GamePiece> newSet = new HashSet<GamePiece>();
		
		Set<GamePiece> set = getAllPiecesOnHexIncludingPiecesInStacks();
		Iterator<GamePiece> it = set.iterator();
		while(it.hasNext()) {
			GamePiece piece = it.next();
			if(piece.getOwner() != null && p.getPlayerId().equals(piece.getOwner().getPlayerId()))
				newSet.add(piece);
		}
		
		return newSet;
	}
	
	/**
	 * Get a set of counters in this location that are able to take damage in combat
	 * @return
	 */
	public Set<Counter> getDamageablePiecesOnLocationForPlayer(Player p) {
		Set<Counter> counters = new HashSet<Counter>();
		
		Set<GamePiece> allPieces = getAllPiecesOnHexIncludingPiecesInStacksForPlayer(p);
		for(GamePiece gp : allPieces) {
			if(gp.getOwner() != null && gp.getOwner().getPlayerId().equals(p.getPlayerId())) {
				if(GamePiece.isGamePieceDamageable(gp)) {
					counters.add((Counter)gp);
				}
			}
		}
		return counters;
	}
	
	
	public List<Integer> getAdjacentHexLocations() {
		return getAdjacentHexIndices(getHexNumber());
	}
	
	// Takes an index of the hex in the array of 37 hexes and returns
	// the indices of surrounding hexes
	public static List<Integer> getAdjacentHexIndices(int index) {
		switch(index) {
			case 0: {
				return Arrays.asList(1,2,3,4,5,6);
			}
			case 1: {
				return Arrays.asList(8,9,2,0,6,7);
			}
			case 2: {
				return Arrays.asList(1,9,10,11,3,0);
			}
			case 3: {
				return Arrays.asList(2,11,12,13,4,0);
			}
			case 4: {
				return Arrays.asList(0,3,13,14,15,5);
			}
			case 5: {
				return Arrays.asList(6,0,4,15,16,17);
			}
			case 6: {
				return Arrays.asList(7,1,0,5,17,18);
			}
			case 7: {
				return Arrays.asList(20,8,1,6,18,19);
			}
			case 8: {
				return Arrays.asList(21,22,9,1,7,20);
			}
			case 9: {
				return Arrays.asList(22,23,10,2,1,8);
			}
			case 10: {
				return Arrays.asList(23,24,25,11,2,9);
			}
			case 11: {
				return Arrays.asList(10,25,26,12,3,2);
			}
			case 12: {
				return Arrays.asList(11,26,27,28,13,3);
			}
			case 13: {
				return Arrays.asList(3,12,28,29,14,4);
			}
			case 14: {
				return Arrays.asList(4,13,29,30,31,15);
			}
			case 15: {
				return Arrays.asList(5,4,14,31,32,16);
			}
			case 16: {
				return Arrays.asList(17,5,15,32,33,34);
			}
			case 17: {
				return Arrays.asList(18,6,5,16,34,35);
			}
			case 18: {
				return Arrays.asList(19,7,6,17,35,36);
			}
			case 19: {
				return Arrays.asList(20,7,18,36);
			}
			case 20: {
				return Arrays.asList(21,8,7,19);
			}
			case 21: {
				return Arrays.asList(22,8,20);
			}
			case 22: {
				return Arrays.asList(21,8,9,23);
			}
			case 23: {
				return Arrays.asList(22,9,10,24);
			}
			case 24: {
				return Arrays.asList(23,10,25);
			}
			case 25: {
				return Arrays.asList(24,10,11,26);
			}
			case 26: {
				return Arrays.asList(25,11,12,27);
			}
			case 27: {
				return Arrays.asList(26,12,28);
			}
			case 28: {
				return Arrays.asList(29,13,12,27);
			}
			case 29: {
				return Arrays.asList(13,28,30,14);
			}
			case 30: {
				return Arrays.asList(31,14,29);
			}
			case 31: {
				return Arrays.asList(32,15,14,30);
			}
			case 32: {
				return Arrays.asList(33,16,15,31);
			}
			case 33: {
				return Arrays.asList(34,16,32);
			}
			case 34: {
				return Arrays.asList(35,17,16,33);
			}
			case 35: {
				return Arrays.asList(36,18,17,34);
			}
			case 36: {
				return Arrays.asList(19,18,35);
			}
		}
		
		return null;
	}
	
}
