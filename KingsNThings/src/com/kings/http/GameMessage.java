package com.kings.http;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.database.DataAccess;
import com.kings.model.Player;
import com.kings.model.SentMessage;
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
	private Map<String, HttpResponseData> userSpecificData;
	@JsonIgnore private Set<Player> playersToSendTo;
	private Date createdDate;
	private String messageId;
	private long delay;
	
	public GameMessage(String type) {
		setPlayersToSendTo(new HashSet<Player>());
		this.type=type;
		this.userSpecificData = new HashMap<String, HttpResponseData>();
		data = new HttpResponseData();
		createdDate = new Date();
		this.messageId = Utils.generateRandomId("gamemessage");
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
	
	public void addPlayersToSendTo(List<Player> players) {
		getPlayersToSendTo().addAll(players);
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
	
	public String toJson(String playerId) {
		Map<Object,Object> thisAsMap = new HashMap<Object, Object>();
		thisAsMap.put("type", getType());
		HttpResponseData data = getUserSpecificData().get(playerId);
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		if(data != null)
			dataMap.putAll(data.getMap());
		dataMap.putAll(getData().getMap());
		thisAsMap.put("data", dataMap);
		thisAsMap.put("createdDate", getCreatedDate());
		thisAsMap.put("messageId", messageId);
		return Utils.toJson(thisAsMap);
	}
	
	/**
	 * Sends this game message to all users in the players set, and returns set of {@link SentMessage}'s
	 */
	public Set<SentMessage> send(){
		Set<SentMessage> sentMessages = new HashSet<SentMessage>();
		for(Player player : getPlayersToSendTo()) {
			try{
				String json = this.toJson(player.getPlayerId());
				String userId = player.getUserId();
				boolean didJustStartTransaction=false;
				DataAccess access = new DataAccess();
				if(!access.isTransactionActive()){
					access.beginTransaction();
					didJustStartTransaction=true;
				}
				User user = access.get(User.class, userId);
				sentMessages.add(new SentMessage(getType(), json, user, getMessageId()));
				Integer port = user.getPort();
				String host = user.getHostName();
				if(didJustStartTransaction)
					access.commit();
				
				if(port != null && host != null) {
					UDPMessage message = new UDPMessage(host, port, json);
					message.setDelay(delay);
					UDPSenderQueue.addMessagesToQueue(message);
				}
			} catch(Exception e) {
				System.out.println("Error sending game message to player with Username="+ player.getUsername());
				e.printStackTrace();
			}
		}
		return sentMessages;
	}
	
	/**
	 * NOT FOR PRODUCTION USE!
	 * @return
	 */
	public Set<SentMessage> testSend(){
		Set<SentMessage> sentMessages = new HashSet<SentMessage>();
		for(Player player : getPlayersToSendTo()) {
			try{
				String json = this.toJson(player.getPlayerId());
				String userId = player.getUserId();
				sentMessages.add(new SentMessage(getType(), json, new User(userId), getMessageId()));
				Integer port = 3004;
				String host = "localhost";
				
				if(port != null && host != null) {
					UDPMessage message = new UDPMessage(host, port, json);
					UDPSenderQueue.addMessagesToQueue(message);
				}
			} catch(Exception e) {
				System.out.println("Error sending game message to player with Username="+ player.getUsername());
				e.printStackTrace();
			}
		}
		return sentMessages;
	}

	@JsonIgnore
	public Map<String, HttpResponseData> getUserSpecificData() {
		return userSpecificData;
	}

	public void setUserSpecificData(Map<String, HttpResponseData> userSpecificData) {
		this.userSpecificData = userSpecificData;
	}
	
	public void addUserSpecificData(String playerId, String key, Object value) {
		HttpResponseData userData = getUserSpecificData().get(playerId);
		if(userData==null){
			userData = new HttpResponseData();
			getUserSpecificData().put(playerId, userData);
		}
		userData.put(key, value);
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
}
