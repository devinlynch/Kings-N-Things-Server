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
import com.kings.networking.lobby.UserWaitingQueue;

@RequestMapping("/lobby")
public class LobbyController extends AbstractLoggedInOnlyController {
	
	@RequestMapping(value="searchAndJoinLobby")
	public @ResponseBody String searchAndJoinLobby(
		@RequestParam int numberOfPreferredPlayers,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{

		User user = getUser(req);
		UserWaiting userWaiting = new UserWaiting(user, numberOfPreferredPlayers);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		HttpResponseMessage message = new HttpResponseMessage();
		return message.toJson();
	}
}
