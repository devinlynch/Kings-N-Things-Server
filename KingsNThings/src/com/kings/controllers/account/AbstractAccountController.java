package com.kings.controllers.account;

import javax.servlet.http.HttpSession;

import com.kings.controllers.AbstractDatabaseController;
import com.kings.database.DataAccess;
import com.kings.http.HttpResponseMessage;
import com.kings.http.KingsAndThingsSession;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.Game;
import com.kings.model.User;
import com.kings.networking.lobby.GameLobby;

public abstract class AbstractAccountController extends AbstractDatabaseController {

	protected void createSessionFromValidatedUsed(User user, HttpSession session) {
		KingsAndThingsSession katSesh = new KingsAndThingsSession();
		katSesh.setUserId(user.getUserId());
		session.setAttribute(KingsAndThingsSession.KAT_SESS_NAME, katSesh);
	}
	
	protected User getUser(String username) {
		DataAccess access = getDataAccess();
		return access.getUserByUsername(username);
	}
	
	protected void saveUser(User user) {
		getDataAccess().save(user);
	}
	
	protected boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(KingsAndThingsSession.KAT_SESS_NAME) != null;
	}
	
	protected HttpResponseMessage alreadyLoggedInMessage() {
		return new HttpResponseMessage(ResponseError.ALREADY_LOGGED_IN);
	}
	
	protected HttpResponseMessage alreadyRegisteredMessage() {
		return new HttpResponseMessage(ResponseError.ALREADY_REGISTERED);
	}
	
	public void addDataToMessageForLoggedInUser(User user, HttpResponseMessage message){
		message.addToData("user", user);
		GameLobby gameLobby = user.getGameLobby();
		if(gameLobby != null)
			message.addToData("gameLobby", user.getGameLobby());
		
		/*
		 * TODO: UNCOMMENT.  THIS IS COMMENTED TO GAMES ARNT SENT UPON LOGIN
		Game game = user.getGame();
		if(game != null)
			message.addToData("game", game);*/
		
	}
}
