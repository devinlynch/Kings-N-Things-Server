package com.kings.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.http.SentMessage;
import com.kings.model.phases.Phase;
import com.kings.model.phases.SetupPhase;

public class GameState {
	private String gameId;
	private Phase currentPhase;
	private boolean isStarted;
	private Set<SentMessage> sentMessages;
	
	
	// In Game Shtuff
	private PlayingCup playingCup;
	private BoardLocation boardLocation;
	private Bank bank;
	private Set<GamePiece> gamePieces;
	private Set<Player> players;

	
	public PlayingCup getPlayingCup() {
		return playingCup;
	}

	public void setPlayingCup(PlayingCup playingCup) {
		this.playingCup = playingCup;
	}

	public BoardLocation getBoardLocation() {
		return boardLocation;
	}

	public void setBoardLocation(BoardLocation boardLocation) {
		this.boardLocation = boardLocation;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public GameState() {
		this.players = new HashSet<Player>();
		this.setSentMessages(new HashSet<SentMessage>());
		this.gamePieces = new HashSet<GamePiece>();
		setCurrentPhase(new SetupPhase(this));
	}
	
	public static GameState createGameStateFromGame(Game game) {
		GameState gameState = new GameState();
		gameState.setGameId(game.getGameId());
		
		int i=1;
		for(User user : game.getUsers()) {
			Player player = new Player(user, gameState, "player"+i);
			gameState.addPlayer(player);
			i++;
		}
		
		return gameState;
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

	public Set<GamePiece> getGamePieces() {
		return gamePieces;
	}

	public void setGamePieces(Set<GamePiece> gamePieces) {
		this.gamePieces = gamePieces;
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
			if(p.getUserId().equals(userId));
				return p;
		}
		return null;
	}
	
	public Player getPlayerByPlayerId(String playerId){
		Iterator<Player> it = getPlayers().iterator();
		while(it.hasNext()) {
			Player p = it.next();
			if(p.getPlayerId().equals(playerId));
				return p;
		}
		return null;
	}


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

	public Set<SentMessage> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(Set<SentMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}
}
