package com.kings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.http.GameMessage;
import com.kings.http.SentMessage;
import com.kings.model.factory.GameStateFactory;
import com.kings.model.phases.Phase;
import com.kings.model.phases.SetupPhase;

public class GameState extends AbstractSerializedObject {
	private String gameId;

	@JsonIgnore private Phase currentPhase;
	private boolean isStarted;
	private List<SentMessage> sentMessages;
	
	// In Game Shtuff
	private PlayingCup playingCup;
	private Bank bank;
	private Map<String,GamePiece> gamePieces;
	private Set<Player> players;
	private List<HexLocation> hexlocations;
	private SideLocation sideLocation;

	
	public PlayingCup getPlayingCup() {
		return playingCup;
	}

	public void setPlayingCup(PlayingCup playingCup) {
		this.playingCup = playingCup;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public GameState() {
		this.players = new HashSet<Player>();
		this.setSentMessages(new ArrayList<SentMessage>());
		this.setGamePieces(new HashMap<String,GamePiece>());
		this.hexlocations = new ArrayList<HexLocation>();
		this.playingCup = new PlayingCup("playingCup");
		this.bank = new Bank("bank", 9999999);
		this.sideLocation= new SideLocation("side", "Side");
	}
	
	public static GameState createGameStateFromGame(Game game) throws Exception {
		GameState gameState = new GameState();
		gameState.setGameId(game.getGameId());
		
		int i=1;
		List<Player> playersInOrder = new ArrayList<Player>();
		for(User user : game.getUsers()) {
			Player player = new Player(user, gameState, "player"+i);
			gameState.addPlayer(player);
			playersInOrder.add(player);
			i++;
		}
		
		gameState.setCurrentPhase(new SetupPhase(gameState, playersInOrder));
		GameStateFactory.makeGameState(gameState, gameState.getPlayers().size());
		
 		return gameState;
	}
	
	public Set<HexLocation> getHexLocationsOwnedByPlayer(String playerId) {
		Iterator<HexLocation> it = getHexlocations().iterator();
		Set<HexLocation> locations = new HashSet<HexLocation>();
		while(it.hasNext()){
			HexLocation loc = it.next();
			locations.add(loc);
		}
		return locations;
	}
	
	public HexLocation getHexLocationsById(String hexLocationId) {
		Iterator<HexLocation> it = getHexlocations().iterator();
		while(it.hasNext()){
			HexLocation loc = it.next();
			if(loc.getId().equals(hexLocationId))
				return loc;
		}
		return null;
	}
	
	public void startGame() {
		if( ! isStarted() ){
			getCurrentPhase().start();
			setStarted(true);
		}
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	
	public void addPlayer(Player player) {
		getPlayers().add(player);
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	protected void addSentMessage(SentMessage sentMessage) {
		getSentMessages().add(sentMessage);
	}
	
	protected void addSentMessages(Set<SentMessage> sentMessages) {
		getSentMessages().addAll(sentMessages);
	}
	
	public void queueUpGameMessageToSendToAllPlayers(GameMessage gameMessage) {
		Set<SentMessage> sentMessages = gameMessage.send();
		for(SentMessage msg : sentMessages) {
			Player p = getPlayerByUserId(msg.getSentToUserId());
			if(p != null)
				p.addSentMessage(msg);
		}
		addSentMessages(sentMessages);
	}
	
	public Player getPlayerByUserId(String userId){
		Iterator<Player> it = getPlayers().iterator();
		while(it.hasNext()) {
			Player p = it.next();
			if(p.getUserId().equals(userId))
				return p;
		}
		return null;
	}
	
	public Player getPlayerByPlayerId(String playerId){
		Iterator<Player> it = getPlayers().iterator();
		while(it.hasNext()) {
			Player p = it.next();
			if(p.getPlayerId().equals(playerId))
				return p;
		}
		return null;
	}

	@JsonIgnore
	public Phase getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(Phase currentPhase) {
		this.currentPhase = currentPhase;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public List<SentMessage> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<SentMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public List<HexLocation> getHexlocations() {
		return hexlocations;
	}

	public void setHexlocations(List<HexLocation> hexlocations) {
		this.hexlocations = hexlocations;
	}
	
	public Set<Map<String,Object>> getPlayersInSerializedFormat() {
		Set<Map<String,Object>> set = new HashSet<Map<String,Object>>();
		
		for(Player p: getPlayers())
			set.add(p.toSerializedFormat());
		return set;
	}
	
	public List<Map<String, Object>> getGamePiecesInSerializedFormat(){
		Iterator<GamePiece> it = getGamePieces().values().iterator();
		List<Map<String, Object>> set = new ArrayList<Map<String, Object>>();
		while(it.hasNext()) {
			set.add(it.next().toSerializedFormat());
		}
		return set;
	}
	
	public List<Map<String, Object>> getHexLocationsInSerializedFormat(){
		Iterator<HexLocation> it = getHexlocations().iterator();
		List<Map<String, Object>> set = new ArrayList<Map<String, Object>>();
		while(it.hasNext()) {
			set.add(it.next().toSerializedFormat());
		}
		return set;
	}

	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("players", getPlayersInSerializedFormat());
		map.put("currentPhaseId", getCurrentPhase().getPhaseId());
		//map.put("gamePieces", getGamePiecesInSerializedFormat());  // I dont think this is needed since they should all have a location - Devin Feb 8th 2014
		map.put("playingCup", getPlayingCup().toSerializedFormat());
		map.put("bank", getBank().toSerializedFormat());
		map.put("sideLocation", getSideLocation().toSerializedFormat());
		map.put("hexLocations", getHexLocationsInSerializedFormat());
		return map;
	}

	public SideLocation getSideLocation() {
		return sideLocation;
	}

	public void setSideLocation(SideLocation sideLocation) {
		this.sideLocation = sideLocation;
	}

	public Map<String,GamePiece> getGamePieces() {
		return gamePieces;
	}

	public void setGamePieces(Map<String,GamePiece> gamePieces) {
		this.gamePieces = gamePieces;
	}
	
	public GamePiece getGamePiece(String gamePieceId) {
		return getGamePieces().get(gamePieceId);
	}
	
	public HashMap<Player, List<Thing>> getPossibleThingsToRecruitForPlayers() {
		Set<GamePiece> playingCupPieces = getPlayingCup().getGamePieces();
		
		
		
		return null;
	}
	
}
