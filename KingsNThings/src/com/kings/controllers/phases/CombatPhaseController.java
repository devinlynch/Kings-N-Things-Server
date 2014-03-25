package com.kings.controllers.phases;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.CombatPhase;
import com.kings.model.phases.MovementPhase;
import com.kings.model.phases.Phase;
import com.kings.model.phases.battle.CombatBattle;
import com.kings.model.phases.battle.CombatBattle.BattleResolution;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;

@RequestMapping("/phase/combat")
public class CombatPhaseController extends PhaseController {
	
	
	/*@RequestMapping(value="didRollInBattle")
	public @ResponseBody String combatFight(
			@RequestParam String battleId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, NotYourTurnException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof CombatPhase) {
			CombatPhase sPhase = (CombatPhase) p;
			CombatBattle battle = sPhase.getBattleById(battleId);
			if(battle != null) {
				battle.playerDidRoll(player);
			} else{
				return genericError().toJson();
			}
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}
	
	@RequestMapping(value="didTakeDamageInBattle")
	public @ResponseBody String timeToRetreat(	
		@RequestParam String battleId,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{

		List<String> gamePiecesTakingDamage = new ArrayList<String>();
		for(int i=1; i<=20; i++) {
			String gamePieceId = req.getParameter("gamePiece_"+i);
			if(gamePieceId != null)
				gamePiecesTakingDamage.add(gamePieceId);
		}
		
		GameState state = getGameState(req);
		Player player = getPlayer(req);
		
		Phase p = state.getCurrentPhase();
		
		if(p instanceof CombatPhase) {
			CombatPhase sPhase = (CombatPhase) p;
			CombatBattle battle = sPhase.getBattleById(battleId);
			if(battle != null) {
				battle.playerTookDamage(player, new HashSet<String>(gamePiecesTakingDamage));
			} else{
				return genericError().toJson();
			}
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
		
	}
	@RequestMapping(value="didMakeResoltionInBattle")
	public @ResponseBody String playerIsDoneCombat(
		boolean didRetreat,
		String battleId,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{

		GameState state = getGameState(req);
		Player player = getPlayer(req);

		Phase p = state.getCurrentPhase();

		if(p instanceof MovementPhase) {
			CombatPhase sPhase = (CombatPhase) p;
			CombatBattle battle = sPhase.getBattleById(battleId);
			if(battle != null) {
				// TODO hardcoding retreat for now since other is not supported
				battle.playerMadeResolution(player, BattleResolution.RETREAT);
			} else{
				return genericError().toJson();
			}
			return successMessage().toJson();
		} else{
			return wrongPhaseMessage().toJson();
		}
	}*/
}
