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

	boolean magicPhaseOver;
	boolean rangePhaseOver;
	boolean meleePhaseOver;
	private List<CombatBattle> combatBattles;

	
	public CombatPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("combat");
	}
	
	private int currentBattle;
	@Override
	public void handleStart() {
		List<CombatBattle> combatBattles = createBattlesAsNeeded();
		this.combatBattles = combatBattles;
		
		handleStartNextBattle();
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
	
	public void handleStartNextBattle() {
		if(currentBattle > combatBattles.size()-1) {
			handleAllDoneBattles();
			return;
		}
		
		combatBattles.get(currentBattle).start();
		currentBattle++;
	}
	
	public void handleAllDoneBattles() {
		boolean allOver = true;
		for(CombatBattle battle : combatBattles) {
			if(!battle.isOver())
				allOver=false;
		}
		
		if(allOver) {
			end();
		}
	}
	
	public CombatBattle getBattleById(String id) {
		for(CombatBattle battle : combatBattles) {
			if(battle.getBattleId().equals(id))
				return battle;
		}
		return null;
	}
	
	

	@Override
	public void setupNextPhase() {
		setNextPhase(new GoldCollectionPhase(getGameState(), getPlayersInOrderOfTurn()));
	}

	@Override
	public GameMessage getPhaseOverMessage() {
		GameMessage message = new GameMessage("combatPhaseIsOver");
		message.setPlayersToSendTo(new HashSet<Player>(getPlayersInOrderOfTurn()));
		message.addToData("hexLocations", getGameState().getHexLocationsInSerializedFormat());
		return message;
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		GameMessage msg = newGameMessageForAllPlayers("combatPhaseStarted");
		return msg;
	}
	
	
	

}
