package com.kings.http;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.database.DataAccess;
import com.kings.model.Player;
import com.kings.model.User;
import com.kings.networking.UDPMessage;
import com.kings.networking.UDPSenderQueue;
import com.kings.util.Utils;

/**
 * Represents a message being sent to the client about state or an event from the game
 * @author devinlynch
 *
 */
public class GameMessage {
	private String type;
	private HttpResponseData data;
	@JsonIgnore private Set<Player> playersToSendTo;
	
	public GameMessage(String type) {
		setPlayersToSendTo(new HashSet<Player>());
		this.type=type;
		data = new HttpResponseData();
	}
	
	public void addToData(String key, Object val) {
		data.put(key, val);
	}

	@JsonIgnore
	public Set<Player> getPlayersToSendTo() {
		return playersToSendTo;
	}

	public void setPlayersToSendTo(Set<Player> playersToSendTo) {
		this.playersToSendTo = playersToSendTo;
	}
	
	public void addPlayerToSendTo(Player player) {
		getPlayersToSendTo().add(player);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HttpResponseData getData() {
		return data;
	}

	public void setData(HttpResponseData data) {
		this.data = data;
	}
	
	public String toJson() {
		return Utils.toJson(this);
	}
	
	/**
	 * Sends this game message to all users in the players set, and returns set of {@link SentMessage}'s
	 */
	public Set<SentMessage> send(){
		String json = this.toJson();
		
		Set<SentMessage> sentMessages = new HashSet<SentMessage>();
		for(Player player : getPlayersToSendTo()) {
			try{
				String userId = player.getUserId();
				sentMessages.add(new SentMessage(getType(), json, userId));
				DataAccess.getInstance().beginTransaction();
				User user = DataAccess.getInstance().get(User.class, userId);
				Integer port = user.getPort();
				String host = user.getHostName();
				DataAccess.getInstance().commit();
				
				if(port != null && host != null) {
					UDPMessage message = new UDPMessage(host, port, json);
					UDPSenderQueue.addMessagesToQueue(message);
				}
			} catch(Exception e) {
				System.out.println("Error sending game message to player with UserId="+ player.getUserId());
				e.printStackTrace();
			}
		}
		return sentMessages;
	}
}
