package com.kings.networking.lobby;

import com.kings.model.User;

/**
 * Holds information for a user who is looking to join a game
 * @author devinlynch
 *
 */
public class UserWaiting {
	private User user;
	private int numberOfPlayersWanted;
	
	public UserWaiting(){
		
	}
	
	public UserWaiting(User user, int numberOfPlayersWanted) {
		setUser(user);
		setNumberOfPlayersWanted(numberOfPlayersWanted);
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getNumberOfPlayersWanted() {
		return numberOfPlayersWanted;
	}
	public void setNumberOfPlayersWanted(int numberOfPlayersWanted) {
		this.numberOfPlayersWanted = numberOfPlayersWanted;
	}
	
	@Override
	public boolean equals(Object o) {
		if(! (o instanceof UserWaiting))
			return false;
		
		UserWaiting comparingUW = (UserWaiting) o;
		
		if(comparingUW.getUser() == null) {
			return this.getUser()==null;
		}
		
		return comparingUW.getUser().equals(this.getUser());
	}
}
