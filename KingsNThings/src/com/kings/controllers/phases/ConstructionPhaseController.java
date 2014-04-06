package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.ConstructionPhase;
import com.kings.model.phases.Phase;

@RequestMapping("/phase/construction")
public class ConstructionPhaseController extends PhaseController {
	
	
	@RequestMapping(value = "didUpgradeFort")
	public @ResponseBody
	String didUpgradeFort(@RequestParam String fortId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException {

		GameState state = getGameState(req);
		Player player = getPlayer(req);

		Phase p = state.getCurrentPhase();

		if (p instanceof ConstructionPhase) {
			ConstructionPhase sPhase = (ConstructionPhase) p;
			try{
				sPhase.didUpgradeFort(player.getPlayerId(), fortId);
			} catch(Exception e) {
				return genericError().toJson();
			}
			return successMessage().toJson();
		} else {
			return wrongPhaseMessage().toJson();
		}
	}

	
	@RequestMapping(value = "didBuyFort")
	public @ResponseBody
	String didBuyFort(
			@RequestParam String hexId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException {

		GameState state = getGameState(req);
		Player player = getPlayer(req);

		Phase p = state.getCurrentPhase();

		if (p instanceof ConstructionPhase) {
			ConstructionPhase sPhase = (ConstructionPhase) p;
			try{
				sPhase.didBuildFort(player.getPlayerId(), hexId);
			} catch(Exception e) {
				return genericError().toJson();
			}
			return successMessage().toJson();
		} else {
			return wrongPhaseMessage().toJson();
		}
	}

	// Checking if all players have constructed and are ready to continue
	@RequestMapping(value = "readyForNextPhase")
	public @ResponseBody
	String readyForNext(HttpServletRequest req, HttpServletResponse res)
			throws NotLoggedInException {

		GameState state = getGameState(req);
		Player player = getPlayer(req);

		Phase p = state.getCurrentPhase();

		if (p instanceof ConstructionPhase) {
			ConstructionPhase sPhase = (ConstructionPhase) p;
			sPhase.playerIsReadyForNextPhase(player.getPlayerId());
			return successMessage().toJson();
		} else {
			return wrongPhaseMessage().toJson();
		}
	}

}