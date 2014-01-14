package com.kings.networking;

/**
 * Use this for adding a UDPMessage to the queue of messages to be sent
 * @author devinlynch
 *
 */
public class UDPSenderQueue extends Thread {
	public static UDPMessageSenderThread senderThread = new UDPMessageSenderThread();
	
	protected static void startQueueIfNotStarted() {
		if(! getSenderThread().isAlive() ) {
			getSenderThread().start();
		}
	}
	
	public static void addMessagesToQueue(UDPMessage... messages) {
		startQueueIfNotStarted();
		for(UDPMessage message : messages)
			getSenderThread().addToQueue(message);
	}
	
	public static UDPMessageSenderThread getSenderThread() {
		return UDPMessageSenderThread.getInstance();
	}
	
}
