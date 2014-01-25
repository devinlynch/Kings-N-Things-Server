package com.kings.controllers.lobby;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.AbstractLoggedInOnlyController;
import com.kings.controllers.account.NotLoggedInException;
import com.kings.http.HttpResponseMessage;
import com.kings.model.User;
import com.kings.networking.lobby.UserWaiting;
import com.kings.networking.lobby.UserWaitingHostGame;
import com.kings.networking.lobby.UserWaitingQueue;
import com.kings.networking.lobby.UserWaitingSearchGame;

@RequestMapping("/lobby")
public class LobbyController extends AbstractLoggedInOnlyController {
	
	@RequestMapping(value="joinLobby")
	public @ResponseBody String joinLobby(
		@RequestParam int numberOfPreferredPlayers,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{

		User user = getUser(req);
		UserWaiting userWaiting = new UserWaiting(user, numberOfPreferredPlayers);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		HttpResponseMessage message = new HttpResponseMessage();
		return message.toJson();
	}
	
	@RequestMapping(value="hostLobby")
	public @ResponseBody String hostLobby(
		@RequestParam int numberOfPreferredPlayers,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{
		
		User user = getUser(req);
		UserWaiting userWaiting = new UserWaitingHostGame(user, numberOfPreferredPlayers);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		HttpResponseMessage message = new HttpResponseMessage();
		return message.toJson();	}
	
	@RequestMapping(value="searchLobby")
	public @ResponseBody String searchLobby(
		@RequestParam String usernameOfHost,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{
		
		User user = getUser(req);
		UserWaiting userWaiting = new UserWaitingSearchGame(user, usernameOfHost);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		HttpResponseMessage message = new HttpResponseMessage();
		return message.toJson();
	}
	
}
