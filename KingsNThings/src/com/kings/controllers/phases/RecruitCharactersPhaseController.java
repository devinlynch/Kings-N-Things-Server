package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.Phase;
import com.kings.model.phases.RecruitCharactersPhase;

public class RecruitCharactersPhaseController extends InGameController {
	
	
	@RequestMapping(value="makeRollForPlayer")
	public @ResponseBody String readyForPlacement(
			@RequestParam String recruitingCharacterId,
			@RequestParam int numPreRolls,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof RecruitCharactersPhase) {
			RecruitCharactersPhase rcPhase = (RecruitCharactersPhase) p;
			try {
				rcPhase.makeRollForPlayer(player.getPlayerId(), recruitingCharacterId, numPreRolls);
			} catch (com.kings.model.phases.exceptions.NotYourTurnException e) {
				notYourTurnMessage().toJson();
			}
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
	@RequestMapping(value="postRoll")
	public @ResponseBody String readyForPlacement(
			@RequestParam int numPostRolls,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof RecruitCharactersPhase) {
			RecruitCharactersPhase rcPhase = (RecruitCharactersPhase) p;
			try {
				rcPhase.postRoll(player.getPlayerId(), numPostRolls);
			} catch (com.kings.model.phases.exceptions.NotYourTurnException e) {
				notYourTurnMessage().toJson();
			}
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
}
