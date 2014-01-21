package com.kings.networking.lobby;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Holds a queue of users who are waiting to be assigned to a {@link GameLobby}
 * @author devinlynch
 *
 */
public class UserWaitingQueue {
	private Queue<UserWaiting> userWaitingQueue;
	private static UserWaitingQueue instance;
	
	public UserWaitingQueue() {
		userWaitingQueue = new ArrayDeque<UserWaiting>();
	}
	
	public static UserWaitingQueue getInstance() {
		if(instance == null) {
			instance = new UserWaitingQueue();
			GameMatcher.getInstance();
		}
		return instance;
	}
	
	public static void addUserWaitingToQueue(UserWaiting userWaiting) {
		getInstance().addUserWaiting(userWaiting);
	}
	
	private void addUserWaiting(UserWaiting userWaiting) {
		userWaitingQueue.add(userWaiting);
	}

	/**
	 * Removes the {@link UserWaiting} from the queue and returns it
	 * @return
	 */
	public UserWaiting getUserWaiting() {
		synchronized (userWaitingQueue) {
			return userWaitingQueue.poll();
		}
	}
	
	public boolean isUsersInQueue() {
		return !userWaitingQueue.isEmpty();
	}
}
