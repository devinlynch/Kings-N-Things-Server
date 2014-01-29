package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.Game;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.User;
import com.kings.model.phases.Phase;
import com.kings.model.phases.PlacementPhase;

public class PlacementPhaseController extends PhaseController {

	@RequestMapping(value="placeFort")
	public @ResponseBody String joinLobby(
		@RequestParam String placedOnHexId,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{

		// This is just as an example, not compelete / tested / implemented 100%
		
		User user = getUserForReal(req);
		Game game = user.getGame();

		GameState state = game.getGameState();
		Player player = state.getPlayerByUserId(user.getUserId());
		Phase p = state.getCurrentPhase();
		PlacementPhase placementPhase = (PlacementPhase) p;
		placementPhase.didPlaceFort(player.getPlayerId(), placedOnHexId);
		
		return null;
	}
}
