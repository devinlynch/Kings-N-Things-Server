package com.kings.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.http.SentMessage;

public class Player extends AbstractSerializedObject {
	private String playerId;
	private String username;
	private String userId;
	private Rack rack1;
	private Rack rack2;
	private Map<String,GamePiece> gamePieces;
	@JsonIgnore
	private GameState gameState;
	private Set<SentMessage> sentMessages;
	private Integer gold;

	private Set<HexLocation> ownedLocations;
	
	public Player(User user, GameState gameState, String playerId) {
		this.playerId = playerId;
		this.gamePieces = new HashMap<String,GamePiece>();
		this.gameState=gameState;
		this.setUsername(user.getUsername());
		this.setUserId(user.getUserId());
		this.setSentMessages(new HashSet<SentMessage>());
		rack1 = new Rack(playerId+"_rack1");
		rack2 = new Rack(playerId+"_rack2");
		gold=0;
		this.ownedLocations = new HashSet<HexLocation>();
		gameState.addBoardLocation(rack1.getId(), rack1);
		gameState.addBoardLocation(rack2.getId(), rack2);
	}
	
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	@JsonIgnore
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Set<SentMessage> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(Set<SentMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}
	
	public Set<SentMessage> getSentMessagesAfterDate(Date date) {
		Set<SentMessage> set = new HashSet<SentMessage>();
		for(SentMessage msg : sentMessages) {
			if(msg.getSentDate().after(date))
				set.add(msg);
		}
		return set;
	}

	public void addSentMessage(SentMessage sentMessage) {
		getSentMessages().add(sentMessage);
	}
	
	public void addSentMessages(Set<SentMessage> sentMessages) {
		getSentMessages().addAll(sentMessages);
	}

	public Rack getRack1() {
		return rack1;
	}

	public void setRack1(Rack rack1) {
		this.rack1 = rack1;
	}

	public Rack getRack2() {
		return rack2;
	}

	public void setRack2(Rack rack2) {
		this.rack2 = rack2;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Set<Map<String, Object>> getGamePiecesInSerializedFormat(){
		Iterator<GamePiece> it = getGamePieces().values().iterator();
		Set<Map<String, Object>> set = new HashSet<Map<String, Object>>();
		while(it.hasNext()) {
			set.add(it.next().toSerializedFormat());
		}
		return set;
	}
	
	public Set<String> getOwnedHexesInSerializedFormat(){
		Iterator<HexLocation> it = getOwnedLocations().iterator();
		Set<String> set = new HashSet<String>();
		while(it.hasNext()) {
			set.add(it.next().getId());
		}
		return set;
	}

	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("playerId", playerId);
		map.put("username", username);
		map.put("userId", userId);
		map.put("gold", gold);
		map.put("rack1", rack1.toSerializedFormat());
		map.put("rack2", rack2.toSerializedFormat());
		map.put("gamePieces", getGamePiecesInSerializedFormat());
		map.put("ownedHexIds", getOwnedHexesInSerializedFormat());
		return map;
	}
	
	public void assignGamePieceToPlayer(GamePiece gamePiece) {
		Player previousOwner = gamePiece.getOwner();
		if(previousOwner != null) {
			previousOwner.removeGamePiece(gamePiece.getId());
		}
		gamePiece.setOwner(this);
		getGamePieces().put(gamePiece.getId(), gamePiece);
	}
	
	public void removeGamePiece(String gamePieceId) {
		getGamePieces().remove(gamePieceId);
	}
	
	public void assignGamePieceToPlayerRack(GamePiece gamePiece) {
		assignGamePieceToPlayer(gamePiece);
		if(!rack1.isFull()) {
			rack1.addGamePieceToLocation(gamePiece);
		} else if(!rack2.isFull()) {
			rack2.addGamePieceToLocation(gamePiece);
		} else{
			//TODO HANDLE FULL RACKS
		}
	}
	
	public void addGold(int gold) {
		this.gold = this.gold+gold;
	}
	
	public void removeGold(int gold) {
		this.gold = this.gold-gold;
	}

	public Set<HexLocation> getOwnedLocations() {
		return ownedLocations;
	}
	
	public List<HexLocation> getOwnedLocationsAsList() {
		return new ArrayList<HexLocation>(getOwnedLocations());
	}

	public void setOwnedLocations(Set<HexLocation> ownedLocations) {
		this.ownedLocations = ownedLocations;
	}
	
	public Map<String, GamePiece> getGamePieces() {
		return gamePieces;
	}

	public void setGamePieces(Map<String, GamePiece> gamePieces) {
		this.gamePieces = gamePieces;
	}
	
	public GamePiece getGamePieceById(String id) {
		return getGamePieces().get(id);
	}
	
	public Set<SpecialCharacter> getSpecialCharacterPieces() {
		Set<SpecialCharacter> set = new HashSet<SpecialCharacter>();
		Iterator<GamePiece> it = getGamePieces().values().iterator();
		while(it.hasNext()) {
			GamePiece gp = it.next();
			if(gp instanceof SpecialCharacter)
				set.add((SpecialCharacter)gp);
		}
		return set;
	}
	
	public Set<Creature> getCreaturePieces() {
		Set<Creature> set = new HashSet<Creature>();
		Iterator<GamePiece> it = getGamePieces().values().iterator();
		while(it.hasNext()) {
			GamePiece gp = it.next();
			if(gp instanceof Creature)
				set.add((Creature)gp);
		}
		return set;
	}
	
	public Set<Fort> getFortPieces() {
		Set<Fort> set = new HashSet<Fort>();
		Iterator<GamePiece> it = getGamePieces().values().iterator();
		while(it.hasNext()) {
			GamePiece gp = it.next();
			if(gp instanceof Fort)
				set.add((Fort)gp);
		}
		return set;
	}
	
	public Set<SpecialIncomeCounter> getSpecialIncomeCounterPieces() {
		Set<SpecialIncomeCounter> set = new HashSet<SpecialIncomeCounter>();
		Iterator<GamePiece> it = getGamePieces().values().iterator();
		while(it.hasNext()) {
			GamePiece gp = it.next();
			if(gp instanceof SpecialIncomeCounter)
				set.add((SpecialIncomeCounter)gp);
		}
		return set;
	}

	/**
	 * Income is calculated as follows:
	 * <ul>
	 * 	<li>one gold piece for each land hex you control</li>
	 *	<li>one gold piece for each special character you control</li>
	 *	<li>as many gold pieces as the combat value of each fort you control</li>
	 *	<li>as many gold pieces as the printed value of each special income counter you control</li>
	 *</ul>
	 * @return
	 */
	public int getIncome() {
		int income=0;
		
		int numberOfLandHexes = getOwnedLocations().size();
		int numberOfSpecialChars = getSpecialCharacterPieces().size();
		
		Iterator<Fort> fortIt = getFortPieces().iterator();
		int goldForForts=0;
		while(fortIt.hasNext()) {
			Fort f = fortIt.next();
			goldForForts += f.getLevelNum();
		}
		
		Iterator<SpecialIncomeCounter> sicIt = getSpecialIncomeCounterPieces().iterator();
		int goldForSICs=0;
		while(sicIt.hasNext()) {
			SpecialIncomeCounter sic = sicIt.next();
			goldForSICs += sic.getGoldValue();
		}
		
		income = numberOfLandHexes + numberOfSpecialChars + goldForForts + goldForSICs;
		
		return income;
	}
	
	public Set<Thing> getAllThingsInRacks() {
		Set<Thing> things = new HashSet<Thing>();
		for(GamePiece gp: rack1.getGamePieces()){
			if(gp instanceof Thing)
				things.add((Thing)gp);
		}
		for(GamePiece gp: rack2.getGamePieces()){
			if(gp instanceof Thing)
				things.add((Thing)gp);
		}
		return things;
	}
}
