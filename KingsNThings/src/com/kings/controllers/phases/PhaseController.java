package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;

import com.kings.controllers.AbstractLoggedInOnlyController;
import com.kings.database.GameStateCache;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.http.HttpResponseMessage;
import com.kings.model.Game;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.User;

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
	
	public HttpResponseMessage successMessage() {
		HttpResponseMessage msg = new HttpResponseMessage();
		return msg;
	}
}
