package com.kings.networking.lobby;

import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.User;

/**
 * A user who is waiting to search for a specific game hosted by a user
 * @author devinlynch
 *
 */
public class UserWaitingSearchGame extends UserWaiting {
	private String usernameOfPlayerSearchingFor;

	public UserWaitingSearchGame(User user, String usernameOfPlayerSearchingFor) {
		super();
		setUser(user);
		setUsernameOfPlayerSearchingFor(usernameOfPlayerSearchingFor);
	}
	
	public String getUsernameOfPlayerSearchingFor() {
		return usernameOfPlayerSearchingFor;
	}

	public void setUsernameOfPlayerSearchingFor(
			String usernameOfPlayerSearchingFor) {
		this.usernameOfPlayerSearchingFor = usernameOfPlayerSearchingFor;
	}
	
	@Override
	public boolean isUserWaitingSearchGame() {
		return true;
	}
	
	@Override
	protected ResponseError getResponseErrorForNoLobbyFound() {
		return ResponseError.NO_OPEN_LOBBY_FOR_USER;
	}
}
