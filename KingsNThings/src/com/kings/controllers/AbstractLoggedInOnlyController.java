package com.kings.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kings.controllers.account.NotLoggedInException;
import com.kings.database.DataAccess;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.http.HttpResponseMessage;
import com.kings.http.KingsAndThingsSession;
import com.kings.model.User;
import com.kings.util.Utils;

/**
 * Any subclasses of this controller will be restricted to users who are logged in.  A intercepter is linked to this which
 * will check for the user being signed in before entering the controller's mapping methods.
 * @author devinlynch
 *
 */
public class AbstractLoggedInOnlyController extends AbstractDatabaseController {
	@Override
	public boolean preHandleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean isOk = super.preHandleRequest(request, response);
		if(!isOk)
			return false;
		
		User user = getUser(request);
		if(user == null) {
			getDataAccess().rollback();
			throw new NotLoggedInException();
		} else{
			
			
			String hostName = request.getRemoteAddr();
			String portString = request.getParameter("port");
			
			Integer port = null;
			try{
				port=Integer.parseInt(portString);
			} catch(NumberFormatException nfe) {
			}
			
			if(port != null) {
				user.setPort(port);
			}
			
			if(hostName != null) {
				user.setHostName(hostName);
			}
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
	
	public User getUserForReal(HttpServletRequest request) {
		User user = getUser(request);
		return DataAccess.initializeAndUnproxy(user);
	}
	
	@ExceptionHandler({NotLoggedInException.class})
	public @ResponseBody String loggedInException(HttpServletRequest req, Exception exception) {
		handleRollback();
		exception.printStackTrace();
		
		
		HttpResponseMessage m = new HttpResponseMessage(ResponseError.NOT_LOGGED_IN);
		return Utils.toJson(m.toJson());
	}
}
