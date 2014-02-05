package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.Phase;
import com.kings.model.phases.SetupPhase;

@RequestMapping("/phase/setup")
public class SetupPhaseController extends PhaseController {

	@RequestMapping(value="readyForPlacement")
	public @ResponseBody String readyForPlacement(
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof SetupPhase) {
			SetupPhase sPhase = (SetupPhase) p;
			sPhase.playerIsReadyForPlacement(player.getPlayerId());
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
}
