package com.kings.networking.lobby;

import java.util.Date;

import com.kings.model.User;

/**
 * Holds information for a user who is looking to join a game
 * @author devinlynch
 *
 */
public class UserWaiting {
	private User user;
	private int numberOfPlayersWanted;
	private Date startedWaitingDate;
	private Date lastMessageReceivedDate;
	
	public UserWaiting(){
	}
	
	public UserWaiting(User user, int numberOfPlayersWanted) {
		setUser(user);
		setNumberOfPlayersWanted(numberOfPlayersWanted);
		setStartedWaitingDate(new Date());
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
	public Date getStartedWaitingDate() {
		return startedWaitingDate;
	}

	public void setStartedWaitingDate(Date startedWaitingDate) {
		this.startedWaitingDate = startedWaitingDate;
	}

	public Date getLastMessageReceivedDate() {
		return lastMessageReceivedDate;
	}

	public void setLastMessageReceivedDate(Date lastMessageReceivedDate) {
		this.lastMessageReceivedDate = lastMessageReceivedDate;
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
