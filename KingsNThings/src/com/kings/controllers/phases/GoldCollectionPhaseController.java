package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.GoldCollectionPhase;
import com.kings.model.phases.Phase;
import com.kings.model.phases.exceptions.NotYourTurnException;

@RequestMapping("/phase/gold")
public class GoldCollectionPhaseController extends PhaseController {
	@RequestMapping(value="didCollectGold")
	public @ResponseBody String didCollectGold(
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException, NotYourTurnException{
		
		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof GoldCollectionPhase) {
			GoldCollectionPhase sPhase = (GoldCollectionPhase) p;
			sPhase.playerIsReadyForNextPhase(player.getPlayerId());
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
		
	}
}
