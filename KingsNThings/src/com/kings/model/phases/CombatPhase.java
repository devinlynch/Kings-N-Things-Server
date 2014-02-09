package com.kings.model.phases;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.phases.exceptions.NotYourTurnException;

public class CombatPhase extends Phase {
	/*
	 * Server detects there's a conflict(2 different players on same tile) if
	 * not end (either way send message() Check who is an attacker, who is a
	 * defender Check if monster is Magic,Range or Melee. Magic will attack
	 * first then range and finally melee Rolling for hits: One die is rolled
	 * except when a monster is seen as a CHARGED they use 2 Roll must be equal
	 * to or higher than the random value which inflicts 1 damage. (charged
	 * would be 2) After each round (magic,range and melee) player determines
	 * where to attack monster Retreat is checked at the end of melee section If
	 * attacker wins , he controls hex. All casulaties go back to the thing bag.
	 * 
	 * Check if there is another battle taking place if not end.
	 */

	private int nextPlayerToFight;
	boolean magicPhaseOver;
	boolean rangePhaseOver;
	boolean meleePhaseOver;
	private int numberOfHitsEarnedForAttacker;
	private int numberOfHitsEarnedForDefender;

	// What types their creatures are
	// Set magic fight first
	// Set range second
	// Set melee last
	// Retreat option to end game
	//
	public CombatPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("combat");
		nextPlayerToFight = 0;
		numberOfHitsEarnedForAttacker = 0;
		numberOfHitsEarnedForDefender = 0;
	}

	public void whoIsTheAttacker() {

	}

	public void whatPlayerHasWhatCreaturesToKnowWhatRoundStarts() {
		//Check if any one has Magic creatures if so start handle magic
		//Check if any one has Range creatures if so start handle Range
		//If not just start Melee
		handleTellPlayersTimeToFightMelee();
	}

	public void handleTellPlayersTimeToFightMagic() {
		if(nextPlayerToFight > getPlayersInOrderOfTurn().size()-1){
			handleTellPlayersTimeToFightRange();
			return;
		}
		
		magicPhaseOver = false;
		GameMessage message = new GameMessage("timeToFightWithMagic");
		message.setPlayersToSendTo(new HashSet<Player>(
				getPlayersInOrderOfTurn()));
		getGameState().queueUpGameMessageToSendToAllPlayers(message);

		tellPlayerItsTheirTurnToFightMagic();
	}

	public void tellPlayerItsTheirTurnToFightMagic() {
		GameMessage message = new GameMessage("yourTurnToFightMagic");
		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToFight);
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}

	public void handleTellPlayersTimeToFightRange() {
		if(nextPlayerToFight > getPlayersInOrderOfTurn().size()-1){
			handleTellPlayersTimeToFightMelee();
			return;
		}
		magicPhaseOver = true;
		GameMessage message = new GameMessage("timeToFightWithRange");
		message.setPlayersToSendTo(new HashSet<Player>(
				getPlayersInOrderOfTurn()));
		getGameState().queueUpGameMessageToSendToAllPlayers(message);

		tellPlayerItsTheirTurnToFightRange();
	}

	public void tellPlayerItsTheirTurnToFightRange() {
		GameMessage message = new GameMessage("yourTurnToFightwithRange");
		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToFight);
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}

	public void handleTellPlayersTimeToFightMelee() {
		if(nextPlayerToFight > getPlayersInOrderOfTurn().size()-1){
			retreat();
			return;
		}
		rangePhaseOver = true;
		GameMessage message = new GameMessage("timeToPlaywithMelee");
		message.setPlayersToSendTo(new HashSet<Player>(
				getPlayersInOrderOfTurn()));
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
		tellPlayerItsTheirTurnToFightMelee();
		
	}

	public void tellPlayerItsTheirTurnToFightMelee() {
		GameMessage message = new GameMessage("yourTurnToFightWithMelee");
		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToFight);
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}

	public synchronized void didFightInMagic(String playerId,String hexLocation1, String hexLocation2, String hexLocation3)throws NotYourTurnException {

	}

	public synchronized void didFightInRange(String playerId,String hexLocation1, String hexLocation2, String hexLocation3)throws NotYourTurnException {

	}

	public synchronized void didFightInMelee(String playerId,String hexLocation1, String hexLocation2, String hexLocation3)throws NotYourTurnException {

	}

	public synchronized void retreat() {

	}

	@Override
	public void handleStart() {
		// TODO Auto-generated method stub
		whatPlayerHasWhatCreaturesToKnowWhatRoundStarts();
	}

	public void tellAttackerTheyAreFirstToFight() {
		if (nextPlayerToFight > getPlayersInOrderOfTurn().size() - 3) {
			end();
			return;
		}

		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToFight);
		GameMessage message = new GameMessage("yourTurntoFight");
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new GoldCollectionPhase(getGameState(),
				getPlayersInOrderOfTurn()));

	}

	@Override
	public GameMessage getPhaseOverMessage() {
		GameMessage message = new GameMessage("combatPhaseIsOver");
		message.setPlayersToSendTo(new HashSet<Player>(
				getPlayersInOrderOfTurn()));
		message.addToData("hexLocations", getGameState()
				.getHexLocationsInSerializedFormat());
		return message;
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		GameMessage msg = newGameMessageForAllPlayers("combatPhaseStarted");
		return msg;
	}

}
