package com.kings.model.phases;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.BoardLocation;
import com.kings.model.Creature;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.Stack;
import com.kings.model.phases.exceptions.NotYourTurnException;

public class MovementPhase extends Phase {
	private int nextPlayerToMove;
	boolean isTimeToMove;
	boolean isInitialMovement;
	
	public MovementPhase(GameState gameState, List<Player> playersInOrderOfTurn, boolean isInitialMovement) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("movement");
		nextPlayerToMove = 0;
		this.isInitialMovement = isInitialMovement;
		alreadyExploredHexesForPlayers = new HashMap<String, Set<String>>();
		for(Player p : playersInOrderOfTurn) {
			alreadyExploredHexesForPlayers.put(p.getPlayerId(), new HashSet<String>());
		}
	}

	@Override
	public void handleStart() {
		tellPlayerItsTheirTurnToMove();

	}
	
	public void tellPlayerItsTheirTurnToMove() {
		if(nextPlayerToMove > getPlayersInOrderOfTurn().size()-1) {
			end();
			return;
		}

		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToMove);
		GameMessage message = new GameMessage("yourTurnToMoveInMovement");
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public synchronized void didMoveStack(String playerId, String hexLocationId, String stackId) throws NotYourTurnException{
		// TODO some error checking really needs to take place here

		if(isOver())
			return;

		if( ! getPlayersInOrderOfTurn().get(nextPlayerToMove).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}

		Player player = getGameState().getPlayerByPlayerId(playerId);
		HexLocation hexLoc = getGameState().getHexLocationsById(hexLocationId);
		Stack stackloc = getGameState().getStackById(stackId);
		hexLoc.addStack(stackloc);

		Set<Player> otherPlayers = new HashSet<Player>(getPlayersInOrderOfTurn());
		otherPlayers.remove(player);
		GameMessage message = new GameMessage("playerMovedStackToNewLocation");
		message.addToData("playerId", playerId);
		message.addToData("hexLocationId", hexLoc.getId());
		message.addToData("stack",stackloc.toSerializedFormat());
		message.setPlayersToSendTo(otherPlayers);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}

	public synchronized void didMoveGamePiece(String playerId, String boardLocationId, String gamePieceId) throws NotYourTurnException{
		// TODO some error checking really needs to take place here
		
		if(isOver())
			return;

		if( ! getPlayersInOrderOfTurn().get(nextPlayerToMove).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}

		Player player = getGameState().getPlayerByPlayerId(playerId);
		BoardLocation loc = getGameState().getBoardLocation(boardLocationId);
		GamePiece gamepiece = getGameState().getGamePiece(gamePieceId);
		loc.addGamePieceToLocation(gamepiece);


		Set<Player> otherPlayers = new HashSet<Player>(getPlayersInOrderOfTurn());
		otherPlayers.remove(player);
		
		GameMessage message = new GameMessage("playerMovedPieceToNewLocation");
		message.addToData("playerId", playerId);
		message.addToData("boardLocationId", boardLocationId);
		message.addToData("gamePieceId",gamePieceId);
		message.setPlayersToSendTo(otherPlayers);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	private Map<String, Set<String>> alreadyExploredHexesForPlayers;
	public synchronized Set<String> didExploreHex(String playerId, String hexLocationId, String gamePieceId, String stackId, int rollNumber) throws NotYourTurnException{
		if(isOver())
			return null;

		if( ! getPlayersInOrderOfTurn().get(nextPlayerToMove).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}

		Player player = getGameState().getPlayerByPlayerId(playerId);
		HexLocation loc = getGameState().getHexLocationsById(hexLocationId);
		
		if(gamePieceId != null){
			didMoveGamePiece(playerId, hexLocationId, gamePieceId);
		}
		
		if(stackId != null){
			didMoveStack(playerId, hexLocationId, stackId);
		}
		
		
		if(alreadyExploredHexesForPlayers.get(playerId) != null && alreadyExploredHexesForPlayers.get(playerId).contains(hexLocationId)) {
			System.out.println("Player " + playerId +  " already explored " + hexLocationId);
			return new HashSet<String>();
		}
		
		boolean didCapture = false;
		if(rollNumber <= 1 || rollNumber >= 6) {
			didCapture = true;
		}
		
		Set<String> defendingPiecesAdded =  new HashSet<String>();
		if(didCapture) {
			loc.capture(player);
		} else {
			for(int i=0; i < rollNumber ; i ++) {
				Creature crea = getGameState().getPlayingCup().getAnyCreature();
				if(crea == null)
					break;
				defendingPiecesAdded.add(crea.getId());
				loc.addGamePieceToLocation(crea);
			}
		}

		if(alreadyExploredHexesForPlayers.get(playerId) == null) {
			alreadyExploredHexesForPlayers.put(playerId, new HashSet<String>());
		}
		alreadyExploredHexesForPlayers.get(playerId).add(hexLocationId);
		
		GameMessage message = newGameMessageForAllPlayers("playerExploredHex");
		message.addToData("playerId", playerId);
		message.addToData("hexLocationId", loc.getId());
		message.addToData("hexLocation", loc.toSerializedFormat());
		message.addToData("defendingPieceIds",defendingPiecesAdded);
		message.addToData("didCapture", didCapture);
		message.addUserSpecificData(playerId, "isMe", true);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
		
		return defendingPiecesAdded;
	}
	
	/**
	 * Creates a stack for the player with the given pieces on the given {@link HexLocation}
	 * @param playerId
	 * @param hexLocationId
	 * @param piecesToAddToStack
	 * @return ID of the {@link Stack} that was created
	 * @throws NotYourTurnException
	 */
	public synchronized String didCreateStack(String playerId, String hexLocationId, List<String> piecesToAddToStack) throws NotYourTurnException{
		if(isOver())
			return null;

		if( ! getPlayersInOrderOfTurn().get(nextPlayerToMove).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}

		Player player = getGameState().getPlayerByPlayerId(playerId);
		HexLocation hexLocation = getGameState().getHexLocationsById(hexLocationId);
		
		Set<GamePiece> pieces = new HashSet<GamePiece>();
		
		for(String gId : piecesToAddToStack) {
			pieces.add(getGameState().getGamePiece(gId));
		}
		Stack createdStack = hexLocation.createAndAddNewStackWithPieces(player, pieces);

		Set<Player> otherPlayers = new HashSet<Player>(getPlayersInOrderOfTurn());
		otherPlayers.remove(player);
		GameMessage message = new GameMessage("playerCreatedStack");
		message.addToData("playerId", playerId);
		message.addToData("stack", createdStack.toSerializedFormat());
		message.setPlayersToSendTo(otherPlayers);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
		
		return createdStack.getId();
	}
	
	public synchronized void didAddPiecesToStack(String playerId, String stackId, List<String> piecesToAddToStack) throws NotYourTurnException{
		if(isOver())
			return;

		if( ! getPlayersInOrderOfTurn().get(nextPlayerToMove).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}

		Player player = getGameState().getPlayerByPlayerId(playerId);
		Stack stack = getGameState().getStackById(stackId);
		
		Set<GamePiece> pieces = new HashSet<GamePiece>();
		for(String gId : piecesToAddToStack) {
			pieces.add(getGameState().getGamePiece(gId));
		}
		stack.addGamePiecesToLocation(pieces);

		Set<Player> otherPlayers = new HashSet<Player>(getPlayersInOrderOfTurn());
		otherPlayers.remove(player);
		GameMessage message = new GameMessage("playerAddedPiecesToStack");
		message.addToData("playerId", playerId);
		message.addToData("stack", stack.toSerializedFormat());
		message.setPlayersToSendTo(otherPlayers);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	public synchronized void playerIsDoneMakingMoves(String playerId) throws NotYourTurnException {
		if( nextPlayerToMove > getPlayersInOrderOfTurn().size()-1 || ! getPlayersInOrderOfTurn().get(nextPlayerToMove).getPlayerId().equals(playerId) ){
			throw new NotYourTurnException();
		}
		
		nextPlayerToMove++;
		tellPlayerItsTheirTurnToMove();
	}
	
	
	
	@Override
	public void setupNextPhase() {
		if( ! isInitialMovement )
			setNextPhase(new CombatPhase(getGameState(), getPlayersInOrderOfTurn()));
		else
			setNextPhase(new GoldCollectionPhase(getGameState(), getPlayersInOrderOfTurn()));
	}

	@Override
	public GameMessage getPhaseOverMessage() {
		// TODO Auto-generated method stub
		GameMessage message = new GameMessage("movementPhaseOver");
		message.setPlayersToSendTo(new HashSet<Player>(getPlayersInOrderOfTurn()));
		message.addToData("hexLocations", getGameState().getHexLocationsInSerializedFormat());
		return message;
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		GameMessage msg = newGameMessageForAllPlayers("movementPhaseStarted");
		return msg;
	}

}