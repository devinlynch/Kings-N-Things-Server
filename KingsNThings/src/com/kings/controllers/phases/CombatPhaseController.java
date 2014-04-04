package com.kings.controllers.phases;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.http.GameMessage;
import com.kings.http.HttpResponseMessage;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.CombatPhase;
import com.kings.model.phases.MovementPhase;
import com.kings.model.phases.Phase;
import com.kings.model.phases.battle.CombatBattle;
import com.kings.model.phases.battle.CombatBattleStep;
import com.kings.model.phases.battle.CombatBattle.BattleResolution;
import com.kings.model.phases.battle.CombatBattleRound.BattleRoundState;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;

@RequestMapping("/phase/combat")
public class CombatPhaseController extends PhaseController {
	
	
	@RequestMapping(value="lockedInRollAndDamage")
	public @ResponseBody String playerLockedInRollAndDamage(
			@RequestParam String battleId,
			@RequestParam String roundId,
			@RequestParam String roundState,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, NotYourTurnException{

		GameState gameState = getGameState(req);
		Player player = getPlayer(req);
		
		Set<String> piecesTakingHits = new HashSet<String>();
		for(int i=0; i<20; i++) {
			String piece = req.getParameter("piceIdTakingHit_"+i);
			if(piece != null) {
				piecesTakingHits.add(piece);
			}
		}
		
		
		Phase p = gameState.getCurrentPhase();
		
		if(p instanceof CombatPhase) {
			CombatPhase sPhase = (CombatPhase) p;
			CombatBattle battle = sPhase.getBattleById(battleId);
			
			if(battle == null) {
				return battleDoesNotExistMessage().toJson();
			} else if (battle.isOver()) {
				return battleOverMessage(battle).toJson();
			} else if (!battle.isStarted()) {
				return battleNotStartedMessage().toJson();
			}
			
			if( ! roundId.equals(battle.getRound().getRoundId()) ) {
				return wrongRoundMessage(battle).toJson();
			}
		
			
			BattleRoundState state = battle.getRound().getState();
			
			if( ! state.toString().equals(roundState) ) {
				return wrongRoundStateMessage(battle).toJson();
			}
			
			if((state!=BattleRoundState.MAGIC_STEP && state!=BattleRoundState.MELEE_STEP && state!=BattleRoundState.RANGE_STEP) || battle.getRound().getCurrentStep() > (battle.getRound().getSteps().size()-1)) {
				return wrongRoundStateMessage(battle).toJson(); 
			} 
			
			CombatBattleStep step = battle.getRound().getSteps().get(battle.getRound().getCurrentStep());
			step.playerLockedInRollAndDamage(player, piecesTakingHits);
			
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
	@RequestMapping(value="didRetreatOrContinue")
	public @ResponseBody String didRetreatOrContinue(
			@RequestParam String battleId,
			@RequestParam String roundId,
			@RequestParam boolean isRetreating,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, NotYourTurnException{

		GameState gameState = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = gameState.getCurrentPhase();
		
		if(p instanceof CombatPhase) {
			CombatPhase sPhase = (CombatPhase) p;
			CombatBattle battle = sPhase.getBattleById(battleId);
			
			if(battle == null) {
				return battleDoesNotExistMessage().toJson();
			} else if (battle.isOver()) {
				return battleOverMessage(battle).toJson();
			} else if (!battle.isStarted()) {
				return battleNotStartedMessage().toJson();
			}
			
			if( ! roundId.equals(battle.getRound().getRoundId()) ) {
				return wrongRoundMessage(battle).toJson();
			}
			
			try {
				battle.getRound().playerDidRetreatOrContinue(player, isRetreating);
			} catch (MoveNotValidException e) {
				return wrongRoundStateMessage(battle).toJson(); 
			}
			
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
	
	
	public HttpResponseMessage battleDoesNotExistMessage() {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.BATTLE_DOES_NOT_EXIST);
		return msg;
	}
	
	public HttpResponseMessage battleOverMessage(CombatBattle battle) {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.BATTLE_OVER);
		msg.addToData("battle", battle.toSerializedFormat());
		return msg;
	}
	
	public HttpResponseMessage battleNotStartedMessage() {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.BATTLE_NOT_STARTED);
		return msg;
	}
	
	public HttpResponseMessage wrongRoundMessage(CombatBattle battle) {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.WRONG_ROUND);
		msg.addToData("battle", battle.toSerializedFormat());
		return msg;
	}
	
	public HttpResponseMessage wrongRoundStateMessage(CombatBattle battle) {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.WRONG_ROUND_STATE);
		msg.addToData("battle", battle.toSerializedFormat());
		return msg;
	}
	
}
