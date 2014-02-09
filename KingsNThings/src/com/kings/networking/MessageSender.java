package com.kings.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Used for actually sending a UDPMessage
 * @author devinlynch
 *
 */
public class MessageSender {
	
	/**
	 * Does the actual sending of a UDPMessage
	 * @param host
	 * @param port
	 * @param message
	 * @throws IOException
	 */
	public static void sendUDPMessage(UDPMessage udpMessage) throws IOException{
		String message = udpMessage.getMessage();
		int port = udpMessage.getPort();
		String host = udpMessage.getHost();
		
		byte[] messageBytes = message.getBytes();
		InetAddress address = InetAddress.getByName(host);
		DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, address, port);

		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		
		socket.close();
		System.out.println("Sent " + new String(packet.getData()));
	}
	
}
