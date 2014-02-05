package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.AbstractLoggedInOnlyController;
import com.kings.controllers.account.NotLoggedInException;
import com.kings.database.DataAccess;
import com.kings.database.GameStateCache;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.http.HttpResponseMessage;
import com.kings.model.Game;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.User;
import com.kings.model.phases.exceptions.MoveNotValidException;
import com.kings.model.phases.exceptions.NotYourTurnException;
import com.kings.util.Utils;

public class PhaseController extends AbstractLoggedInOnlyController {
	public GameState getGameState(HttpServletRequest req) {
		User user = getUser(req);
		Game game = user.getGame();
		return GameStateCache.getInstance().getGameState(game.getGameId());
	}
	
	public Player getPlayer(HttpServletRequest req) {
		User user = getUser(req);
		GameState gs = getGameState(req);
		return gs.getPlayerByUserId(user.getUserId());
	}
	
	public HttpResponseMessage wrongPhaseMessage() {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.WRONG_PHASE);
		return msg;
	}
	
	public HttpResponseMessage notYourTurnMessage() {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.NOT_YOUR_TURN);
		return msg;
	}
	
	public HttpResponseMessage badMoveMessage() {
		HttpResponseMessage msg = new HttpResponseMessage(ResponseError.BAD_MOVE);
		return msg;
	}
	
	public HttpResponseMessage successMessage() {
		HttpResponseMessage msg = new HttpResponseMessage();
		return msg;
	}
	
	@ExceptionHandler({MoveNotValidException.class})
	public @ResponseBody String moveNotValidException(HttpServletRequest req, Exception exception) {
		handleRollback();
		
		return badMoveMessage().toJson();
	}
	
	@ExceptionHandler({NotYourTurnException.class})
	public @ResponseBody String NotYourTurnException(HttpServletRequest req, Exception exception) {
		handleRollback();
		
		return notYourTurnMessage().toJson();
	}
}
