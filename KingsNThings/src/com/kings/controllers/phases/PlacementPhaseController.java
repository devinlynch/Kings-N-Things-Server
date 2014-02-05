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
import com.kings.model.phases.Phase;
import com.kings.model.phases.PlacementPhase;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;

@RequestMapping("/phase/placement")
public class PlacementPhaseController extends PhaseController {

	@RequestMapping(value="placeControlMarker")
	public @ResponseBody String placeControlMarker(
		@RequestParam String hexLocation1,
		@RequestParam String hexLocation2,
		@RequestParam String hexLocation3,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException, NotYourTurnException{

		
		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof PlacementPhase) {
			PlacementPhase sPhase = (PlacementPhase) p;
			sPhase.didPlaceControlMarkers(player.getPlayerId(), hexLocation1, hexLocation2, hexLocation3);
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
		
	}
	
	@RequestMapping(value="placeFort")
	public @ResponseBody String placeFort(
		@RequestParam String hexLocation,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof PlacementPhase) {
			PlacementPhase sPhase = (PlacementPhase) p;
			// Making assumption player already has 1 fort since this should be initialized when game starts
			Fort fort = (Fort) player.getFortPieces().toArray()[0];
			sPhase.didPlaceFort(player.getPlayerId(), fort.getId(), hexLocation);
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
		
	}
}
