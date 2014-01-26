package com.kings.controllers.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.http.HttpResponseMessage;
import com.kings.model.User;
import com.kings.util.Utils;

@RequestMapping("/register")
public class RegisterController extends AbstractAccountController {
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody String register(
			@RequestParam(required=true) String username,
			@RequestParam(required=true) String password,
			@RequestParam(required=false) String hostName,
			@RequestParam(required=false) Integer port,
			@RequestParam(defaultValue="true", required=false) Boolean loginAfterRegister,
			HttpServletRequest req,
			HttpServletResponse res){
		HttpResponseMessage message = null;
		HttpSession session = req.getSession();
		String type = "register";
		
		if(isLoggedIn(session)) {
			message = alreadyLoggedInMessage();
			message.setType(type);
			return Utils.toJson(message);
		}
		
		User user = getUser(username);
		
		if(user != null) {
			message = alreadyRegisteredMessage();
			message.setType(type);
			return Utils.toJson(message);
		}
		
		user = new User();
		user.setUsername(username);
		//TODO add encryption
		user.setPassword(password);
		user.setHostName(hostName);
		user.setPort(port);
		saveUser(user);
		
		message =  successfulRegisterMessage();
		message.setType(type);
		if(loginAfterRegister) {
			createSessionFromValidatedUsed(user, session);
			addDataToMessageForLoggedInUser(user, message);
			message.addToData("isLoggedIn", true);
		}
		
		return Utils.toJson(message);
	}
	
	protected HttpResponseMessage successfulRegisterMessage() {
		HttpResponseMessage message = new HttpResponseMessage();
		message.getData().put("success", true);
		message.setName("Successfully registerered");
		return message;
	}
}
