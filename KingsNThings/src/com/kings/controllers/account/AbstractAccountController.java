package com.kings.controllers.account;

import javax.servlet.http.HttpSession;

import com.kings.controllers.AbstractKingsController;
import com.kings.database.DataAccess;
import com.kings.http.HttpResponseMessage;
import com.kings.http.KingsAndThingsSession;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.User;

public abstract class AbstractAccountController extends AbstractKingsController {

	private static final String KAT_SESS_NAME = "com.kings.controllers.KingsAndThingsSession";

	protected void createSessionFromValidatedUsed(User user, HttpSession session) {
		KingsAndThingsSession katSesh = new KingsAndThingsSession();
		katSesh.setUserId(user.getUserId());
		
		session.setAttribute(KAT_SESS_NAME, katSesh);
	}
	
	protected User getUser(String username) {
		DataAccess access = getDataAccess();
		return access.getUserByUsername(username);
	}
	
	protected void saveUser(User user) {
		getDataAccess().save(user);
	}
	
	protected boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(KAT_SESS_NAME) != null;
	}
	
	protected HttpResponseMessage alreadyLoggedInMessage() {
		return new HttpResponseMessage(ResponseError.ALREADY_LOGGED_IN);
	}
	
	protected HttpResponseMessage alreadyRegisteredMessage() {
		return new HttpResponseMessage(ResponseError.ALREADY_REGISTERED);
	}
}
