package com.kings.networking.lobby;

import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.User;

/**
 * A user who is waiting to host a game
 * @author devinlynch
 *
 */
public class UserWaitingHostGame extends UserWaiting {
	public UserWaitingHostGame(User user, int numberOfPlayersWanted, boolean demo) {
		super(user, numberOfPlayersWanted);
		setDemo(demo);
	}
	
	@Override
	public boolean isUserWaitingHostGame(){
		return true;
	}
	
	@Override
	protected ResponseError getResponseErrorForNoLobbyFound() {
		return ResponseError.UNABLE_TO_HOST_LOBBY;
	}
}
