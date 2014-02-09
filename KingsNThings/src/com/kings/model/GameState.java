package com.kings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
	private Map<String, BoardLocation> boardLocations;
	private int phaseTurn;
	
	
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
		this.boardLocations = new HashMap<String, BoardLocation>();
		addBoardLocation("playingCup", playingCup);
		addBoardLocation(sideLocation.getId(), sideLocation);
		phaseTurn=1;
	}
	
	public static GameState createGameStateFromGame(Game game) throws Exception {
		GameState gameState = new GameState();
		initializeNewGameState(gameState, game);
 		return gameState;
	}
	
	protected static void initializeNewGameState(GameState gameState, Game game) throws Exception {
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
	
	/**
	 * Returns a map of the players in the game mapped to a list of ids of things they are aloud to recruit.  The number of things they can recruit
	 * is dependent on how much gold they have / how many things are in their rack to trade.
	 * @return
	 */
	public HashMap<Player, List<String>> getPossibleThingsToRecruitForPlayers() {
		List<Thing> playingCupPieces = new ArrayList<Thing>(getPlayingCup().getThings());
		// Max Free Recruits: 2
		// Max Trades: 5
		// Max Buys: 5
		
		int totalFreeRecruitsPerPlayer = 2;
		int maxNumberOfTrades = 5;
		int maxNumberOfBuys = 5;
		
		int costOfBuyingPiece=5;
		
		HashMap<Player, List<String>> map = new HashMap<Player, List<String>>();
		for(Player p: getPlayers()) {
			int numberOfThingsInPlayersRacks = p.getAllThingsInRacks().size();
			
			int thisPlayersMaxNumebrOfTrades = maxNumberOfTrades;
			if(numberOfThingsInPlayersRacks < 5) {
				thisPlayersMaxNumebrOfTrades = numberOfThingsInPlayersRacks;
			}
			
			int thisPlayersMaxNumberOfBuys = maxNumberOfBuys;
			int playersGold = p.getGold();
			int totalPossibleBuys = playersGold/costOfBuyingPiece;
			if(totalPossibleBuys < maxNumberOfBuys) {
				thisPlayersMaxNumberOfBuys = totalPossibleBuys;
			}
			
			int thisPlayersTotalNumberOfRecruitmentThings =  thisPlayersMaxNumberOfBuys + thisPlayersMaxNumebrOfTrades + totalFreeRecruitsPerPlayer;
			
			if(playingCupPieces.size() < thisPlayersTotalNumberOfRecruitmentThings) {
				thisPlayersTotalNumberOfRecruitmentThings = playingCupPieces.size();
			}
			
			List<String> thisPlayersThingsToRecruit = new ArrayList<String>();
			for(int i = 0; i < thisPlayersTotalNumberOfRecruitmentThings; i++) {
				Thing thing = playingCupPieces.get(new Random().nextInt(playingCupPieces.size()));
				playingCupPieces.remove(thing);
				thisPlayersThingsToRecruit.add(thing.getId());
			}
			map.put(p, thisPlayersThingsToRecruit);
		}
		
		return map;
	}

	public Map<String, BoardLocation> getBoardLocations() {
		return boardLocations;
	}

	public void setBoardLocations(Map<String, BoardLocation> boardLocations) {
		this.boardLocations = boardLocations;
	}
	
	public void addBoardLocation(String id, BoardLocation location) {
		getBoardLocations().put(id, location);
	}
	
	public BoardLocation getBoardLocation(String id) {
		return getBoardLocations().get(id);
	}

	public int getPhaseTurn() {
		return phaseTurn;
	}

	public void setPhaseTurn(int phaseTurn) {
		this.phaseTurn = phaseTurn;
	}
	
	public Stack getStackById(String stackId) {
		Iterator<HexLocation> it = getHexlocations().iterator();
		while (it.hasNext()) {
			HexLocation hexLoc = it.next();
			for (Stack stack : hexLoc.getStacks()) {
				if (stack.getId().equals(stackId)) {
					return stack;
				}
			}
		}
		return null;
	}
	
}