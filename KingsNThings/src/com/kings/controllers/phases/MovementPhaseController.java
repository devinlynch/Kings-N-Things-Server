package com.kings.controllers.phases;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.type.descriptor.sql.JdbcTypeFamilyInformation.Family;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.MovementPhase;
import com.kings.model.phases.Phase;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;
@RequestMapping("/phase/movement")
public class MovementPhaseController extends PhaseController {

		@RequestMapping(value="moveStack")
		public @ResponseBody String moveStack(
			@RequestParam String hexLocationId,
			@RequestParam String stackId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, NotYourTurnException{


			GameState state = getGameState(req);
			Player player = getPlayer(req);
			Phase p = state.getCurrentPhase();

			if (p instanceof MovementPhase) {
				MovementPhase sPhase = (MovementPhase) p;
				sPhase.didMoveStack(player.getPlayerId(), hexLocationId, stackId);
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}

		}
		
		@RequestMapping(value="moveGamePiece")
		public @ResponseBody String moveGamePiece(
			@RequestParam String locationId,
			@RequestParam String gamePieceId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{

			GameState state = getGameState(req);
			Player player = getPlayer(req);

			Phase p = state.getCurrentPhase();

			if(p instanceof MovementPhase) {
				MovementPhase sPhase = (MovementPhase) p;
				sPhase.didMoveGamePiece(player.getPlayerId(),locationId,gamePieceId);
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}

		}
		
		@RequestMapping(value="createStack")
		public @ResponseBody String createStack(
			@RequestParam String hexLocationId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{
			
			List<String> gamePiecesToAdd = new ArrayList<String>();
			
			for(int i=1; i<=10; i++) {
				String gamePieceId = req.getParameter("gamePiece_"+i);
				if(gamePieceId != null)
					gamePiecesToAdd.add(gamePieceId);
			}
			
			GameState state = getGameState(req);
			Player player = getPlayer(req);

			Phase p = state.getCurrentPhase();

			if(p instanceof MovementPhase) {
				MovementPhase sPhase = (MovementPhase) p;
				sPhase.didCreateStack(player.getPlayerId(), hexLocationId, gamePiecesToAdd);
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}
		}
		
		@RequestMapping(value="addPiecesToStack")
		public @ResponseBody String addPiecesToStack(
			@RequestParam String stackId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{
			
			List<String> gamePiecesToAdd = new ArrayList<String>();
			
			for(int i=1; i<=10; i++) {
				String gamePieceId = req.getParameter("gamePiece_"+i);
				if(gamePieceId != null)
					gamePiecesToAdd.add(gamePieceId);
			}
			
			GameState state = getGameState(req);
			Player player = getPlayer(req);

			Phase p = state.getCurrentPhase();

			if(p instanceof MovementPhase) {
				MovementPhase sPhase = (MovementPhase) p;
				sPhase.didAddPiecesToStack(player.getPlayerId(), stackId, gamePiecesToAdd);
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}
		}
		
		@RequestMapping(value="playerIsDoneMakingMoves")
		public @ResponseBody String moveGamePiece(
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException, MoveNotValidException, NotYourTurnException{

			GameState state = getGameState(req);
			Player player = getPlayer(req);

			Phase p = state.getCurrentPhase();

			if(p instanceof MovementPhase) {
				MovementPhase sPhase = (MovementPhase) p;
				sPhase.playerIsDoneMakingMoves(player.getPlayerId());
				return successMessage().toJson();
			} else{
				return wrongPhaseMessage().toJson();
			}

		}
	}