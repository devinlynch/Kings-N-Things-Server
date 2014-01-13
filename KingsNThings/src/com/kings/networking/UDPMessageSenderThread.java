package com.kings.networking;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Thread that continually loops through a Queue of UDPMessages and sends them.
 * @author devinlynch
 *
 */
public class UDPMessageSenderThread extends Thread {
	private boolean stopped;
	private Queue<UDPMessage> messages;
	private static UDPMessageSenderThread instance = null;
	
	public UDPMessageSenderThread() {
		super();
		messages = new ArrayDeque<UDPMessage>();
	}
	
	public static UDPMessageSenderThread getInstance() {
        if (instance == null) {
            instance = new UDPMessageSenderThread();
        }
        return instance;
    }
	
	@Override
	public void run() {
		while(! isStopped() || messages.size() > 0) {
			if(messages.size() > 0) {
				UDPMessage message = messages.remove();
				try {
					MessageSender.sendUDPMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// If the queue is empty now, sleep a bit
			if(messages.size() == 0) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					setStopped(true);
				}
			}
		}
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	
	public void addToQueue(UDPMessage message) {
		messages.add(message);
	}
}
