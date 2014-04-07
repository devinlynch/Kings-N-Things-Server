package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.http.GameMessage;
import com.kings.http.HttpResponseMessage;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.Thing;
import com.kings.model.phases.Phase;
import com.kings.model.phases.RecruitThingsPhase;

@RequestMapping("/phase/recruitThings")
public class RecruitThingsPhaseController extends PhaseController {

	@RequestMapping(value="recruitedAndPlacedThing")
	public @ResponseBody String readyForPlacement(
			@RequestParam String thingId,
			@RequestParam String locationId,
			@RequestParam(required=false, defaultValue="false") boolean wasBought,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof RecruitThingsPhase) {
			RecruitThingsPhase sPhase = (RecruitThingsPhase) p;
			sPhase.didRecruitAndPlaceThing(player.getPlayerId(), thingId, locationId, wasBought);
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
	@RequestMapping(value="tradedThing")
	public @ResponseBody String readyForPlacement(
			@RequestParam String oldThingId,
			@RequestParam String newThingId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof RecruitThingsPhase) {
			RecruitThingsPhase sPhase = (RecruitThingsPhase) p;
			Thing newThing = sPhase.didTradeThing(player.getPlayerId(), oldThingId, newThingId);
			HttpResponseMessage msg = successMessage();
			msg.addToData("newThing", newThing.toSerializedFormat());
			return msg.toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
	@RequestMapping(value="readyForNextPhase")
	public @ResponseBody String readyForPlacement(
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof RecruitThingsPhase) {
			RecruitThingsPhase sPhase = (RecruitThingsPhase) p;
			sPhase.playerIsReadyForNextPhase(player.getPlayerId());
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
}
