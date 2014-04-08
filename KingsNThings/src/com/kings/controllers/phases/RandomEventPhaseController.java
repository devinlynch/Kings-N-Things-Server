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
import com.kings.model.phases.RandomEventPhase;

@RequestMapping("/phase/RandomEvent")
public class RandomEventPhaseController extends InGameController {
	
		@RequestMapping(value="playDefection")
		public @ResponseBody String readyForPlacement(
				@RequestParam String randomEventPieceId,
				@RequestParam String recruitingForId,
				@RequestParam boolean didRecruit,
				HttpServletRequest req,
				HttpServletResponse res) throws NotLoggedInException{

			GameState state = getGameState(req);
			Player player = getPlayer(req);
			
			Phase p = state.getCurrentPhase();
			
			if(p instanceof RandomEventPhase) {
				RandomEventPhase rcPhase = (RandomEventPhase) p;
				rcPhase.playerPlayedDefection(player, randomEventPieceId, recruitingForId, didRecruit);
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}
		}
		
		@RequestMapping(value="doneMakingMoves")
		public @ResponseBody String doneMakingMoves(
				HttpServletRequest req,
				HttpServletResponse res) throws NotLoggedInException{

			GameState state = getGameState(req);
			Player player = getPlayer(req);
			
			Phase p = state.getCurrentPhase();
			
			if(p instanceof RandomEventPhase) {
				RandomEventPhase rcPhase = (RandomEventPhase) p;
				rcPhase.playerIsDoneMakingMoves(player);
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}
		}
}
