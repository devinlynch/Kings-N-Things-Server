package com.kings.model.phases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.BoardLocation;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.Player;

public class RandomEventPhase extends Phase {
	public Set<Player> playersDoneMakingMoves;
	
	public RandomEventPhase(GameState gameState,
			List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("randomEvent");
		playersDoneMakingMoves = new HashSet<Player>();
	}

	@Override
	public void handleStart() {
		// TODO Auto-generated method stub
	}
	
	public synchronized void playerIsDoneMakingMoves(Player p) {
		playersDoneMakingMoves.add(p);
		
		handlePhaseOverIfNeeded();
	}
	
	public synchronized void handlePhaseOverIfNeeded() {
		if(playersDoneMakingMoves.size() == getPlayersInOrderOfTurn().size()) {
			end();
		}
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new MovementPhase(getGameState(), getPlayersInOrderOfTurn(), false));
	}


	@Override
	public GameMessage getPhaseOverMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		GameMessage msg = newGameMessageForAllPlayers("randomEventsStarted");
		return msg;
	}

	
	public void playerPlayedDefection(Player player, String randomEventPieceId, String recruitingForId, boolean didRecruit) {
		GamePiece randomEventPiece = getGameState().getGamePiece(randomEventPieceId);
		GamePiece recruitForPiece = getGameState().getGamePiece(recruitingForId);
		Player oldPlayer = recruitForPiece.getOwner();
		
		if(didRecruit) {
			player.assignGamePieceToPlayerRack(recruitForPiece);
		}
		
		getGameState().getPlayingCup().addGamePieceToLocation(randomEventPiece);
		
		String clientLog = player.getUsername()+" played their defection event.  ";
		if(didRecruit) {
			clientLog += "They stole " + recruitForPiece.getName();
			if(oldPlayer != null) {
				clientLog += " from " + oldPlayer.getUsername() +".";
			} else {
				clientLog += " from the side location.";
			}
		} else {
			clientLog+= "They were unsuccessful with recruiting "+recruitForPiece.getName()+".";
		}

		sendRandomEventMessage(player, clientLog, new ArrayList<GamePiece>(), new ArrayList<BoardLocation>());
	}
	
	public void sendRandomEventMessage(Player playerWhoMadeMove, String clientLog, List<GamePiece> affectedPieces, List<BoardLocation> affectedLocations) {
		List<Map<String,Object>> affectedPiecesSerialized = new ArrayList<Map<String,Object>>();
		for(GamePiece gp : affectedPieces) {
			affectedPiecesSerialized.add(gp.toSerializedFormat());
		}
		
		List<Map<String,Object>> affectedLocationsSerialized = new ArrayList<Map<String,Object>>();
		for(BoardLocation bl : affectedLocations) {
			affectedLocationsSerialized.add(bl.toSerializedFormat());
		}
		
		GameMessage msg = newGameMessageForAllPlayers("randomEventMove");
		msg.addToData("player", playerWhoMadeMove.toSerializedFormat());
		msg.addToData("clientLog", clientLog);
		msg.addToData("affectedPieces", affectedPiecesSerialized);
		msg.addToData("affectedLocations", affectedLocationsSerialized);
		getGameState().queueUpGameMessageToSendToAllPlayers(msg);
	}
}
