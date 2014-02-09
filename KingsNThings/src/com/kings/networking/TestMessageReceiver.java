package com.kings.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class TestMessageReceiver {
	public static void main(String[] args) throws IOException {
		 DatagramSocket serverSocket = new DatagramSocket(3004);             
		 byte[] receiveData = new byte[(int)(Math.pow(2, 16))];             
		 byte[] sendData = new byte[(int)(Math.pow(2, 16))];             
		 while(true)        {
             DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
             serverSocket.receive(receivePacket);
             String sentence = new String( receivePacket.getData());
             System.out.println(sentence);
		 }
	}
}
