package com.kings.model.phases.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.CityVill;
import com.kings.model.Creature;
import com.kings.model.Fort;
import com.kings.model.GamePiece;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.SpecialCharacter;
import com.kings.model.Stack;
import com.kings.model.phases.battle.CombatBattle.BattleResolution;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;
import com.kings.util.Utils;

public class CombatBattleRound {
	/*
	 * Main vars
	 */
	private boolean started;
	private boolean ended;
	private String roundId;
	private CombatBattle battle;
	
	/*
	 * Step specific vars
	 */
	private List<CombatBattleStep> steps;
	private int currentStep;
	private boolean stepsAreOver; // Set to true once all 3 battle steps have taken place
	
	/*
	 * Post - step specific vars
	 */
	private boolean didAttackerMakeMoveForRetreatOrContinue;
	private boolean didDefenderMakeMoveForRetreatOrContinue;
	private boolean attackerRetreated;
	private boolean defenderRetreated;
	
	public CombatBattleRound(CombatBattle battle) {
		setBattle(battle);
		roundId = Utils.generateRandomId("battle_"+battle.getBattleId()+"_round");
		steps = new ArrayList<CombatBattleStep>();
	}
	
	public void start() {
		System.out.println("CombatRound started id=["+roundId+"] and BattleId=["+battle.getBattleId()+"]");
		
		GameMessage roundStartedMessage = newRoundMessageForAllPlayers("combatRoundStarted");
		getBattle().getGameState().queueUpGameMessageToSendToAllPlayers(roundStartedMessage);
		
		started=true;
		ended=false;
		stepsAreOver=false;
		
		didAttackerMakeMoveForRetreatOrContinue = false;
		didDefenderMakeMoveForRetreatOrContinue = false;
		attackerRetreated = false;
		defenderRetreated = false;
		
		currentStep=0;
		steps.clear();
		MagicCombatBattleStep magicStep = new MagicCombatBattleStep(this);
		RangedCombatBattleStep rangeStep = new RangedCombatBattleStep(this);
		MeleeCombatBattleStep meleeStep = new MeleeCombatBattleStep(this);
		
		steps.add(magicStep);
		steps.add(rangeStep);
		steps.add(meleeStep);
		
		handleNextStepIfNeeded();
	}
	
	public void handleNextStepIfNeeded() {
		if(currentStep > (steps.size()-1)){
			handleStepsOver();
			return;
		}
		
		CombatBattleStep step = steps.get(currentStep);
		if( step.isStarted() && ! step.isEnded() ) {
			return;
		} else if(step.isEnded()) {
			currentStep++;
		}
		
		if(currentStep > (steps.size()-1)){
			handleStepsOver();
			return;
		}
		
		CombatBattleStep nextStep = steps.get(currentStep);
		nextStep.start();
	}
	
	protected void handleStepsOver() {
		stepsAreOver=true;
	}
	
	public synchronized void playerDidRetreatOrContinue(Player p, boolean isRetreating) throws MoveNotValidException, NotYourTurnException {
		if(!stepsAreOver) {
			throw new MoveNotValidException("The steps are not over yet, you cannot retreat yet");
		}
		
		boolean isAttacker;
		if(p.getPlayerId().equals(battle.getAttacker().getPlayerId())) {
			isAttacker=true;
		} else if(p.getPlayerId().equals(battle.getDefender().getPlayerId())) {
			isAttacker=false;
		} else{
			throw new NotYourTurnException();
		}
		
		if(isAttacker) {
			attackerRetreated = isRetreating;
			didAttackerMakeMoveForRetreatOrContinue = true;
		} else {
			defenderRetreated = isRetreating;
			didDefenderMakeMoveForRetreatOrContinue = true;
		}
		
		handlePostRound();
	}
	
	protected synchronized void handlePostRound() {
		if( !didAttackerMakeMoveForRetreatOrContinue && !didDefenderMakeMoveForRetreatOrContinue )
			return;
		
		
		if(attackerRetreated) {
			handleRetreat(true);
		} else if(defenderRetreated) {
			handleRetreat(false);
		} else{
			handleNewRound();
		}
	}
	
	protected synchronized void handleRetreat(boolean isAttacker) {
		List<HexLocation> surroundingLocations = battle.getGameState().getSurroundingHexLocations(getBattle().getLocationOfBattle());
		
		HexLocation hexForPlayerToMoveTo = null;
		
		Player p;
		Player opposingPlayer;
		if(isAttacker) {
			p=battle.getAttacker();
			opposingPlayer=battle.getDefender();
		} else{
			p=battle.getDefender();
			opposingPlayer=battle.getAttacker();
		}
		
		
		// When retreating, the retreating player can only go to a adjacent hex that is theirs that does not have any other enemy's on it
		for(HexLocation hex : surroundingLocations) {
			if(hex.getOwner() != null && hex.getPlayersWhoAreOnMe().size() <= 1 && hex.getOwner().getPlayerId().equals(p.getPlayerId())){
				hexForPlayerToMoveTo = hex;
				break;
			}
		}
		
		// If no such hex exists, the battle continues
		if(hexForPlayerToMoveTo == null) {
			GameMessage msg = newRoundMessageForAllPlayers("retreatCouldNotTakePlace");
			msg.addToData("playerIdTryingToRetreat", p.getPlayerId());
			getBattle().getGameState().queueUpGameMessageToSendToAllPlayers(msg);
			
			handleNewRound();
			return;
		}
		
		// Retreat All the creatures and special characters
		
		List<GamePiece> pieces = new ArrayList<GamePiece>(battle.getLocationOfBattle().getPiecesForPlayer(p));
		List<Stack> stacks = new ArrayList<Stack>(battle.getLocationOfBattle().getStacksForPlayer(p));
		
		Set<Map<String, Object>> retreatedPieces = new HashSet<Map<String,Object>>();

		for(GamePiece piece : pieces) {
			if(piece instanceof Creature || piece instanceof SpecialCharacter){
				hexForPlayerToMoveTo.addGamePieceToLocation(piece);
				retreatedPieces.add(piece.toSerializedFormat());
			}
		}
		
		for(Stack stack : stacks) {
			for(GamePiece piece: stack.getGamePieces()) {
				if(piece instanceof Creature || piece instanceof SpecialCharacter){
					hexForPlayerToMoveTo.addGamePieceToLocation(piece);
				} else {
					battle.getLocationOfBattle().addGamePieceToLocation(piece);
				}
			}
		}
		
		// The winner now conquers the hex
		battle.getLocationOfBattle().capture(opposingPlayer);
		
		GameMessage msg = newRoundMessageForAllPlayers("playerRetreatedFromBattle");
		msg.addToData("retreatedPlayerId", p.getPlayerId());
		msg.addToData("retreatedToHex", hexForPlayerToMoveTo.toSerializedFormat());
		msg.addToData("piecesRetreated", retreatedPieces);
		battle.getGameState().queueUpGameMessageToSendToAllPlayers(msg);
		
		
		BattleResolution resolution;
		if(isAttacker) {
			resolution = BattleResolution.ATTACKER_RETREATED;
		} else {
			resolution = BattleResolution.DEFENDER_REREATED;
		}
		
		end();
		battle.battleDidFinish(opposingPlayer, resolution);
	}
	
	public enum PostBattlePieceStatus {
		DESTROYED,
		REDUCED_LEVEL,
		RESTORED
	}
	
	public void handlePlayerWon() {
		Player winner;
		boolean attackerWon;
		int numAttackerPieces = getBattle().getLocationOfBattle().getDamageablePiecesOnLocationForPlayer(getBattle().getAttacker()).size();
		
		// If attacker lost all their peices, winner is automatically defender
		if(numAttackerPieces == 0) {
			winner = getBattle().getDefender();
			attackerWon=false;
		} else {
			winner = getBattle().getAttacker();
			attackerWon=true;
		}

		// The winning player now owns the hex
		getBattle().getLocationOfBattle().capture(winner);
		
		end();
		battle.battleDidFinish(winner, attackerWon ? BattleResolution.ATTACKER_WON : BattleResolution.DEFENDER_WON);
	}
	
	public void handleNewRound() {
		end();
		battle.startNewRound();
	}
	
	public void end() {
		System.out.println("CombatRound ended id=["+roundId+"] and BattleId=["+battle.getBattleId()+"]");
		ended=true;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public CombatBattle getBattle() {
		return battle;
	}

	public void setBattle(CombatBattle battle) {
		this.battle = battle;
	}

	public List<CombatBattleStep> getSteps() {
		return steps;
	}

	public void setSteps(List<CombatBattleStep> steps) {
		this.steps = steps;
	}

	public String getRoundId() {
		return roundId;
	}

	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}
	
	public GameMessage newRoundMessageForAllPlayers(String type) {
		GameMessage message = getBattle().getCombatPhase().newGameMessageForAllPlayers(type);
		message.addToData("battleId", battle.getBattleId());
		message.addToData("roundId", roundId);
		message.addToData("attackerId", battle.getAttacker().getPlayerId());
		message.addToData("defenderId", battle.getDefender().getPlayerId());
		return message;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public boolean isStepsAreOver() {
		return stepsAreOver;
	}
	
}
