package com.kings.model.phases;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.BoardLocation;
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
		message.addToData("stackLocationId",stackId);
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
	
	public synchronized void didCreateStack(String playerId, String hexLocationId, List<String> piecesToAddToStack) throws NotYourTurnException{
		if(isOver())
			return;

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
		if( ! getPlayersInOrderOfTurn().get(nextPlayerToMove).getPlayerId().equals(playerId) ){
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