package com.kings.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kings.http.KingsAndThingsSession;
import com.kings.model.User;

/**
 * Any subclasses of this controller will be restricted to users who are logged in.  A intercepter is linked to this which
 * will check for the user being signed in before entering the controller's mapping methods.
 * @author devinlynch
 *
 */
public class AbstractLoggedInOnlyController extends AbstractDatabaseController {
	@Override
	public boolean preHandleRequest(HttpServletRequest request,
			HttpServletResponse response) {
		boolean isOk = super.preHandleRequest(request, response);
		if(!isOk)
			return false;
		
		User user = getUser(request);
		if(user == null) {
			getDataAccess().rollback();
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandleRequest(HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView) {
		super.postHandleRequest(request, response, modelAndView);
	}
	
	public User getUser(HttpServletRequest request) {
		KingsAndThingsSession session = (KingsAndThingsSession)request.getSession().getAttribute(KingsAndThingsSession.KAT_SESS_NAME);
		if(session != null) {
			String id = session.getUserId();
			return getDataAccess().get(User.class, id);
		}
		return null;
	}
}
