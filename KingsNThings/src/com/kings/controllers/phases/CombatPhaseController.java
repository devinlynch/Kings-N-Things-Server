package com.kings.controllers.phases;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.Fort;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.CombatPhase;
import com.kings.model.phases.MovementPhase;
import com.kings.model.phases.Phase;
import com.kings.model.phases.PlacementPhase;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/phase/combat")
public class CombatPhaseController extends PhaseController {
	
	
	@RequestMapping(value="combatFight")
	
	public @ResponseBody String combatFight(
			@RequestParam String hexLocationId,
			@RequestParam String stackIdForOwner,
			@RequestParam String stackIdForAttacker,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, NotYourTurnException{

			
			GameState state = getGameState(req);
			Player player = getPlayer(req);
			
			Phase p = state.getCurrentPhase();
			
			if(p instanceof CombatPhase) {
				CombatPhase sPhase = (CombatPhase) p;
				sPhase.didFightInMelee(player.getPlayerId(), hexLocationId, stackIdForOwner, stackIdForAttacker);
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}
			
		}
	@RequestMapping(value="timeToRetreat")
	public @ResponseBody String timeToRetreat(	
		@RequestParam String hexLocationIdForOwner,
		@RequestParam String hexLocationIdForAttacker,
		@RequestParam String stackIdForOwner,
		@RequestParam String stackIdForAttacker,

		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof CombatPhase) {
			CombatPhase sPhase = (CombatPhase) p;
			sPhase.retreat(player.getPlayerId(), hexLocationIdForOwner, hexLocationIdForAttacker,stackIdForOwner,stackIdForAttacker);
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
		
	}
	@RequestMapping(value="playerIsDoneCombat")
	public @ResponseBody String playerIsDoneCombat(
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);

		Phase p = state.getCurrentPhase();

		if(p instanceof MovementPhase) {
			MovementPhase sPhase = (MovementPhase) p;
			sPhase.(player.getPlayerId());
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}

	}
}
}
