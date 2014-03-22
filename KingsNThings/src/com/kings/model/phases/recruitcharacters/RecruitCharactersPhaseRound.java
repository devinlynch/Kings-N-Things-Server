package com.kings.model.phases.recruitcharacters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.SpecialCharacter;
import com.kings.model.phases.RecruitCharactersPhase;


/**
 * Represents a round of recruit characters for a specific player.  This was encapsulated in a separate
 * object because the {@link RecruitCharactersPhase} was getting too messy.
 * @author devinlynch
 *
 */
public class RecruitCharactersPhaseRound {
	private  boolean started;
	private boolean roundOver;
	private Player player;
	private RecruitCharactersPhase phase;
	private boolean didRoll;
	private int rollValue;
	private int preRollChanges;
	private SpecialCharacter specialCharacterRecruiting;
	private boolean didRecruit;
	private int postRollChanges;
	
	public RecruitCharactersPhaseRound(Player player, RecruitCharactersPhase phase) {
		this.phase = phase;
		this.player = player;
		setDidRoll(false);
		rollValue=0;
		preRollChanges=0;
		didRecruit=false;
		specialCharacterRecruiting=null;
		started = false;
		postRollChanges=0;
	}
	
	public void start() {
		started = true;
		
		GameMessage message = new GameMessage("yourTurnToRecruitSpecialCharacter");
		List<SpecialCharacter> specialCharctersAvailable = getGameState().getSideLocation().getSpecialCharacters();
		
		List<Map<String,Object>> specialCharctersAvailableSerialized = new ArrayList<Map<String,Object>>();
		for(SpecialCharacter sc : specialCharctersAvailable) {
			specialCharctersAvailableSerialized.add(sc.toSerializedFormat());
		}
		
		if(specialCharctersAvailable.isEmpty()){
			end();
			return;
		}
		message.addToData("availableSpecialCharacters", specialCharctersAvailableSerialized);
		message.addPlayerToSendTo(player);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	
	// Once it's the players turn to recruit, they get to pick 1 special character from the
	// list of avaialble characters.  They then make a roll with 2 dice.  If the roll is >=
	// to the characters combat value then they recruit it.  Before they roll they also get
	// to pay increments of 5 gold for adding 1 to their roll.
	public void makeRollForPlayer(String recruitingCharacterId, int numRequestedPreRollChanges) {
		if(didRoll) {
			postRoll(0);
			return;
		}
		
		
		rollValue = getGameState().rollDice(2);
		
		if(recruitingCharacterId != null){
			specialCharacterRecruiting = (SpecialCharacter) getGameState().getSideLocation().getGamePieceById(recruitingCharacterId);
		}else{
			specialCharacterRecruiting = getGameState().getSideLocation().getAnySpecialCharacter();
		}
		
		if(specialCharacterRecruiting==null) {
			System.err.println("Player by id ["+player.getPlayerId()+"] tried recruiting a character by id ["+recruitingCharacterId+"] but it is not in side location!");
			end();
			return;
		}
		
		for(int i=0; i < numRequestedPreRollChanges; i++) {
			if(player.getGold() <5)
				break;
			player.removeGold(5);
			
			preRollChanges++;
		}
		
		didRecruit = false;
		if( (rollValue+preRollChanges) >= (specialCharacterRecruiting.getCombatValue()*2) ) {
			didRecruit = true;
		}
		
		didRoll = true;
		
		GameMessage message = phase.newGameMessageForAllPlayers("didRollInRecruitCharactrs");
		message.addToData("playerId", player.getPlayerId());
		message.addToData("theRoll", rollValue);
		message.addToData("numPreRolls", preRollChanges);
		message.addToData("didRecruit", didRecruit);
		message.addToData("specialCharacter", specialCharacterRecruiting.toSerializedFormat());
		message.addUserSpecificData(player.getPlayerId(), "isMe", true);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
		
		if(didRecruit) {
			end();
		}
	}
	
	// If the player did not recruit the character after their roll, they can pay 10 gold increments
	// for adding 1 to their roll.
	public void postRoll(int numPostRollChanges) {
		if( ! didRoll ) {
			// Force the player to roll
			System.out.println("Player with id["+player.getPlayerId()+"] did not roll yet but tried to call postRoll, rolling for them");
			makeRollForPlayer(null, 0);
			if(isRoundOver())
				return;
		}
		
		
		for(int i=0; i < numPostRollChanges; i++) {
			if(player.getGold() <10)
				break;
			player.removeGold(10);
			postRollChanges++;
		}
		
		if( (rollValue+preRollChanges+postRollChanges) >= (specialCharacterRecruiting.getCombatValue()*2) ) {
			didRecruit = true;
		}
		
		end();
	}
	
	
	public void end() {
		setRoundOver(true);
		sendRoundOverMessage();
		phase.handleStartNextRoundIfNeeded();
	}
	
	public void sendRoundOverMessage() {
		GameMessage message = phase.newGameMessageForAllPlayers("roundOfRecruitCharactersOver");
		message.addToData("playerId", player.getPlayerId());
		message.addToData("theRoll", rollValue);
		message.addToData("numPreRolls", preRollChanges);
		message.addToData("numPostRolls", postRollChanges);
		message.addToData("didRecruit", didRecruit);
		message.addToData("specialCharacter", specialCharacterRecruiting.toSerializedFormat());
		message.addUserSpecificData(player.getPlayerId(), "isMe", true);
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
	}
	
	
	public GameState getGameState() {
		return phase.getGameState();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isDidRoll() {
		return didRoll;
	}

	public void setDidRoll(boolean didRoll) {
		this.didRoll = didRoll;
	}

	public SpecialCharacter getSpecialCharacterRecruiting() {
		return specialCharacterRecruiting;
	}

	public void setSpecialCharacterRecruiting(SpecialCharacter specialCharacterRecruiting) {
		this.specialCharacterRecruiting = specialCharacterRecruiting;
	}

	public boolean isDidRecruit() {
		return didRecruit;
	}

	public void setDidRecruit(boolean didRecruit) {
		this.didRecruit = didRecruit;
	}

	public boolean isRoundOver() {
		return roundOver;
	}

	public void setRoundOver(boolean roundOver) {
		this.roundOver = roundOver;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
}
