package com.kings.controllers.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.http.HttpResponseMessage;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.User;
import com.kings.util.Utils;

@Controller
@RequestMapping("/account")
public class LoginController extends AbstractAccountController {
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody String login(
				@RequestParam String username,
				@RequestParam String password,
				@RequestParam(required=false) String hostName,
				@RequestParam(required=false) Integer port,
				HttpServletRequest req,
				HttpServletResponse res){
		
		HttpResponseMessage message = null;
		HttpSession session = req.getSession();
		
		if(isLoggedIn(session)) {
			message = alreadyLoggedInMessage();
			return Utils.toJson(message);
		}
		
		User user = getUser(username);
		
		boolean isUserCredentialsOk = validateUser(user, password);
		if( !isUserCredentialsOk ) {
			message = badUsernamePassMessage();
			return Utils.toJson(message);
		}
		
		
		createSessionFromValidatedUsed(user, session);
		user.setPort(port);
		user.setHostName(hostName);
		
		message = successfulLoginMessage();
		return Utils.toJson(message);
	}
		
	/**
	 * Returns true if the given non encrypted password is the same as the Users stored password
	 * @param user
	 * @param nonEncryptedPassword
	 * @return
	 */
	public boolean validateUser(User user, String nonEncryptedPassword) {
		if(user == null)
			return false;
		
		String password = user.getPassword();
		if(password == null)
			return true;
		
		//TODO: encrypt and compare
		return nonEncryptedPassword.equals(password);
			
	}
	
	public HttpResponseMessage badUsernamePassMessage() {
		return new HttpResponseMessage(ResponseError.BAD_USERNAME_AND_PASSWORD);
	}
	
	public HttpResponseMessage successfulLoginMessage() {
		HttpResponseMessage message = new HttpResponseMessage();
		message.getData().put("success", true);
		message.setName("Successful login");
		return message;
	}
}