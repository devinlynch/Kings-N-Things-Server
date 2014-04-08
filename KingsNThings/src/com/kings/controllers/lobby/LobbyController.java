package com.kings.controllers.lobby;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kings.controllers.AbstractLoggedInOnlyController;
import com.kings.controllers.account.NotLoggedInException;
import com.kings.http.HttpResponseError;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.http.HttpResponseMessage;
import com.kings.model.Game;
import com.kings.model.User;
import com.kings.networking.lobby.GameLobby;
import com.kings.networking.lobby.GameMatcher;
import com.kings.networking.lobby.UserWaiting;
import com.kings.networking.lobby.UserWaitingHostGame;
import com.kings.networking.lobby.UserWaitingQueue;
import com.kings.networking.lobby.UserWaitingSearchGame;

@RequestMapping("/lobby")
public class LobbyController extends AbstractLoggedInOnlyController {
	private String type="lobbyHttpResponse";
	
	@RequestMapping(value="joinLobby")
	public @ResponseBody String joinLobby(
		@RequestParam(required=false) boolean isDemo,
		@RequestParam int numberOfPreferredPlayers,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{

		User user = getUserForReal(req);
		Game game = user.getGame();
		if(game != null) {
			return userAlreadyinGameMessage(game).toJson();
		}
		
		UserWaiting userWaiting = new UserWaiting(user, numberOfPreferredPlayers);
		userWaiting.setDemo(isDemo);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		HttpResponseMessage message = new HttpResponseMessage();
		message.setType(type);
		return message.toJson();
	}
	
	@RequestMapping(value="hostLobby")
	public @ResponseBody String hostLobby(
		@RequestParam int numberOfPreferredPlayers,
		@RequestParam(required=false) boolean isDemo,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{
		
		User user = getUserForReal(req);
		Game game = user.getGame();
		if(game != null) {
			return userAlreadyinGameMessage(game).toJson();
		}
		
		UserWaiting userWaiting = new UserWaitingHostGame(user, numberOfPreferredPlayers, isDemo);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		HttpResponseMessage message = new HttpResponseMessage();
		message.setType(type);
		return message.toJson();	}
	
	@RequestMapping(value="searchLobby")
	public @ResponseBody String searchLobby(
		@RequestParam String usernameOfHost,
		@RequestParam(required=false) boolean isDemo,
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{
		
		User user = getUserForReal(req);
		Game game = user.getGame();
		if(game != null) {
			return userAlreadyinGameMessage(game).toJson();
		}
		
		UserWaiting userWaiting = new UserWaitingSearchGame(user, usernameOfHost, isDemo);
		UserWaitingQueue.addUserWaitingToQueue(userWaiting);
		
		HttpResponseMessage message = new HttpResponseMessage();
		message.setType(type);
		return message.toJson();
	}
	
	@RequestMapping(value="unregisterFromLobby")
	public @ResponseBody String removeFromLobby(
		HttpServletRequest req,
		HttpServletResponse res) throws NotLoggedInException{
		
		try{
			User user = getUserForReal(req);
			Game game = user.getGame();
			if(game != null) {
				return userAlreadyinGameMessage(game).toJson();
			}
			GameMatcher.getInstance().unregisterUserFromLobby(user);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		HttpResponseMessage message = new HttpResponseMessage();
		message.setType(type);
		return message.toJson();
	}
	
	@RequestMapping(value="getLobbyState")
	public @ResponseBody String getLobbyState(
			@RequestParam String gameLobbyId,
			HttpServletRequest req,
			HttpServletResponse res) throws NotLoggedInException{
		
		HttpResponseMessage message = new HttpResponseMessage();
		message.setType("gameLobbyState");
		
		GameLobby lobby = null;
		try{
			lobby = GameMatcher.getInstance().getGameLobby(gameLobbyId);
		} catch(Exception e) {
		}
		
		if(lobby != null){
			message.addToData("lobby", lobby);
		} else{
			message.setError(new HttpResponseError(ResponseError.GENERIC_ERROR));
		}
		message.setCreatedDate(new Date());
		return message.toJson();
	}
	
	public HttpResponseMessage userAlreadyinGameMessage(Game game) {
		HttpResponseMessage m = new HttpResponseMessage(ResponseError.USER_ALREADY_IN_GAME);
		m.setType(type);
		m.addToData("game", game);
		return m;
	}
}
