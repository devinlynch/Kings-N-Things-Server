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
	private int nextPlayerToPlaceFort;
	boolean isTimeToPlaceForts;
	
	public PlacementPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("placement");
		nextPlayerToPlaceFort= 0;
		nextPlayerToPlaceControlMarkers = 0;
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
	public synchronized void didPlaceControlMarkers(String playerId, String hexLocation1, String hexLocation2, String hexLocation3) throws NotYourTurnException{
		// TODO some error checking really needs to take place here
		
		if(isOver())
			return;
		
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
		
		tellPlayerItsTheirTurnToPlaceFort();
	}
	
	public void tellPlayerItsTheirTurnToPlaceFort(){
		GameMessage message = new GameMessage("yourTurnToPlaceFort");
		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToPlaceFort);
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	/**
	 * Call this when a player places their fort on a hex
	 * @param playerId
	 * @param fortId
	 * @param hexLocation
	 * @throws MoveNotValidException
	 * @throws NotYourTurnException 
	 */
	public synchronized void didPlaceFort(String playerId, String fortId, String hexLocation) throws MoveNotValidException, NotYourTurnException {
		// TODO add some server side error checking
		
		if(isOver())
			return;
		
		if(!isTimeToPlaceForts){
			throw new MoveNotValidException("It is not time to place forts!");
		}
		
		if( ! getPlayersInOrderOfTurn().get(nextPlayerToPlaceFort).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}
		
		Player player = getGameState().getPlayerByPlayerId(playerId);
		GamePiece fort = player.getGamePieceById(fortId);
		HexLocation loc = getGameState().getHexLocationsById(hexLocation);
		loc.addGamePieceToLocation(fort);
		
		
		Set<Player> otherPlayers = new HashSet<Player>(getPlayersInOrderOfTurn());
		otherPlayers.remove(player);
		GameMessage message = new GameMessage("playerDidPlaceFort");
		message.addToData("playerId", playerId);
		message.addToData("fortId", fortId);
		message.addToData("hexLocationId", hexLocation);
		message.setPlayersToSendTo(otherPlayers);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
		
		// TODO inform all players of fort placement
		
		nextPlayerToPlaceFort++;
		if(nextPlayerToPlaceFort > getPlayersInOrderOfTurn().size()-1) {
			end();
		} else{
			tellPlayerItsTheirTurnToPlaceFort();
		}
	}

	@Override
	public GameMessage getPhaseOverMessage() {
		GameMessage message = new GameMessage("placementPhaseOver");
		message.setPlayersToSendTo(new HashSet<Player>(getPlayersInOrderOfTurn()));
		message.addToData("hexLocations", getGameState().getHexLocationsInSerializedFormat());
		return message;
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new GoldCollectionPhase(getGameState(), getPlayersInOrderOfTurn()));
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
