package com.kings.model.phases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.phases.battle.CombatBattle;
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

	/*
	 * For Iteration 1 Goals : Detect theres a fight Find all creatures involed
	 * in fight Click roll have numbers assigned to each creature (3 creatures,3
	 * random numbers) Then be able to select which creature takes damage
	 * Retreat: If attacker retreats defender stays and attacker goes back to
	 * where he was If defender retreats attacker claims the hex.
	 * 
	 * 
	 * Simple version: Find Creatures for each Player assigned rolls to each
	 * creature,Assign hits,Send back Values to Client They decide who gets
	 * hits, Add Retreat so players can leave.
	 */

	private int nextPlayerToFight;
	boolean magicPhaseOver;
	boolean rangePhaseOver;
	boolean meleePhaseOver;
	private int numberOfHitsEarnedForAttacker;
	private int numberOfHitsEarnedForDefender;
	private int attacker;
	private int defender;
	private List<CombatBattle> combatBattles;
	private List<CombatBattle> doneBattles;

	
	public CombatPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("combat");
		nextPlayerToFight = 0;
		attacker = 0;
		defender = 0;
		numberOfHitsEarnedForAttacker = 0;
		numberOfHitsEarnedForDefender = 0;
		doneBattles = new ArrayList<CombatBattle>();
	}

	public void tellPlayerItsTheirTurnToFightMelee() {
		GameMessage message = new GameMessage("yourTurnToFightWithMelee");
		Player currentPlayer = getPlayersInOrderOfTurn().get(nextPlayerToFight);
		message.addPlayerToSendTo(currentPlayer);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}

	// Step 3: Assign random numbers numbers to each creature in players stack

	public synchronized void didFightInMelee(String playerId, String StackId)
			throws NotYourTurnException {
		// Get all All creatures in a stack, along with their combat value
		// Assign a random number between 1-6 to each Creature
		// If the random number is equal or greater than the combat value add to
		// the Counter for hits by 1
		// If player is attacker add to attacker counter , If defender add to
		// defender counter
		Random r = new Random();
		int randomnum = r.nextInt(6-1) + 1;
		
		//Find all creatures in stack 
		

		//Compare random number with creature combat value
		
		
		
		
	}

	// Step 4: End Combat By removing whoever clicked Retreat(If attacker clicked : Go to previous hex owned, If defender clicks, attackers gets hex and defender goes to previous hex owned.
	public synchronized void retreat() {
		// Find out which player pressed retreat
		// If Owner retreats -> Move to adjcent hex with stack END
		// If Attacker retreats -> Move to adjcent hex owned with stack END

	}

	// Check if combat is done.
	public synchronized void playerIsDoneCombat(String playerId)
			throws NotYourTurnException {
		if (!getPlayersInOrderOfTurn().get(nextPlayerToFight).getPlayerId()
				.equals(playerId)) {
			throw new NotYourTurnException();
		}

		nextPlayerToFight++;
		tellPlayerItsTheirTurnToFightMelee();
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
	
	
	
	
	//////////// Start for Devin Stuff /////////
	
	
	@Override
	public void handleStart() {
		List<CombatBattle> combatBattles = createBattlesAsNeeded();
		this.combatBattles = combatBattles;
		
		handleStartFirstBattle();
	}
	
	public List<CombatBattle> createBattlesAsNeeded() {
		List<CombatBattle> combatBattles = new ArrayList<CombatBattle>();
		for(HexLocation hexLocation: getGameState().getHexlocations()) {
			List<Player> playersOnHex = hexLocation.getPlayersWhoAreOnMe();
			if(playersOnHex.size() > 1) {
				CombatBattle battle = new CombatBattle(hexLocation, this);
			}
		}
		return combatBattles;
	}
	

	///////////////////////////////////// Stuff under here is for later////////////////////////////////////////////////////
	/*public void handleTellPlayersTimeToFightMagic() {
		if (nextPlayerToFight > getPlayersInOrderOfTurn().size() - 1) {
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
		if (nextPlayerToFight > getPlayersInOrderOfTurn().size() - 1) {
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

	public synchronized void didFightInMagic(String playerId,
			String hexLocation1, String hexLocation2, String hexLocation3)
			throws NotYourTurnException {

	}

	public synchronized void didFightInRange(String playerId,
			String hexLocation1, String hexLocation2, String hexLocation3)
			throws NotYourTurnException {

	}*/

}
