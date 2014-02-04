package com.kings.model.phases;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;

public class PlacementPhase extends Phase {
	private int nextPlayerToPlaceControlMarkers;
	private Set<String> playersThatHavePlacedForts;
	boolean isTimeToPlaceForts;
	
	public PlacementPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("placement");
		playersThatHavePlacedForts= new HashSet<String>();
		nextPlayerToPlaceControlMarkers = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleStart() {
		/**
		 * 
		 - Server tells player 1 that it is their turn to place their tower and tells every other player that they are waiting on player 1
		- Once player 1 makes their move, they tell the server the id of the hex that they palced their tower on
		- The server tells all players of where player 1 put their hex
		- The server then tells player 2 that they can make their move and all other players that they are waiting on player 2
		--- etc goes on for all players
		- Server continues to next phase once all players have picked their tower locations
		 */
		
		
		tellPlayerItsTheirTurnToPlaceControlMarkers();
	}
	
	public void tellPlayerItsTheirTurnToPlaceControlMarkers() {
		if(nextPlayerToPlaceControlMarkers > getPlayersInOrderOfTurn().size()-1){
			handleTellPlayersTimeToPlaceFort();
			return;
		}
		
		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToPlaceControlMarkers);
		GameMessage message = new GameMessage("yourTurnToPlaceControlMarker");
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	/**
	 * Call this when a player has placed their control markers on the map, will handle telling next player it's their turn
	 * @param playerId
	 * @param hexLocation1
	 * @param hexLocation2
	 * @param hexLocation3
	 * @throws NotYourTurnException 
	 */
	public void didPlaceControlMarkers(String playerId, String hexLocation1, String hexLocation2, String hexLocation3) throws NotYourTurnException{
		// TODO some error checking really needs to take place here
		
		if( ! getPlayersInOrderOfTurn().get(nextPlayerToPlaceControlMarkers).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}
		
		Player player = getGameState().getPlayerByPlayerId(playerId);
		HexLocation loc1 = getGameState().getHexLocationsById(hexLocation1);
		loc1.capture(player);
		HexLocation loc2 = getGameState().getHexLocationsById(hexLocation2);
		loc2.capture(player);
		HexLocation loc3 = getGameState().getHexLocationsById(hexLocation3);
		loc3.capture(player);
		
		Set<Player> otherPlayers = new HashSet<Player>(getPlayersInOrderOfTurn());
		otherPlayers.remove(player);
		GameMessage message = new GameMessage("playerPlacedControlMarker");
		message.addToData("playerId", playerId);
		message.addToData("playerHexes", player.getOwnedHexesInSerializedFormat());
		message.setPlayersToSendTo(otherPlayers);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
		
		nextPlayerToPlaceControlMarkers++;
		tellPlayerItsTheirTurnToPlaceControlMarkers();
	}
	
	public void handleTellPlayersTimeToPlaceFort(){
		isTimeToPlaceForts=true;
		GameMessage message = new GameMessage("timeToPlaceFort");
		message.setPlayersToSendTo(new HashSet<Player>(getPlayersInOrderOfTurn()));
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public void didPlaceFort(String playerId, String fortId, String hexLocation) throws MoveNotValidException {
		if(!isTimeToPlaceForts){
			throw new MoveNotValidException("It is not time to place forts!");
		}
		
		playersThatHavePlacedForts.add(playerId);
		Player player = getGameState().getPlayerByPlayerId(playerId);
		GamePiece fort = player.getGamePieceById(fortId);
		HexLocation loc = getGameState().getHexLocationsById(hexLocation);
		loc.addGamePieceToLocation(fort);
		
		// TODO inform all players of fort placement
		
		if(playersThatHavePlacedForts.size() >= getPlayersInOrderOfTurn().size()) {
			end();
		}
	}

	@Override
	public String getPhaseOverMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new GoldCollectionPhase(getGameState(), getPlayersInOrderOfTurn()));
	}

	@Override
	public String getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
