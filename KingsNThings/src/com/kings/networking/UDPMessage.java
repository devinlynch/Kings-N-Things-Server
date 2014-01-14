package com.kings.networking;

/**
 * Represents a message that is meant to be sent using UDP
 * @author devinlynch
 *
 */
public class UDPMessage {
	private String host;
	private int port;
	private String message;
	
	public UDPMessage(){
	}
	
	public UDPMessage(String host, int port, String message) {
		setHost(host);
		setMessage(message);
		setPort(port);
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "{'UDPMessage': "
				+ "{'host':'"+getHost()+"', "
				+ "'port':'"+getPort()+"' "
				+ "'message':'"+getMessage()+"'"
				+ "}"
				+ "}";
	}
}
