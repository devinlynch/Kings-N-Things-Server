package com.kings.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import com.kings.networking.MessageSender;
import com.kings.networking.UDPMessage;


public class TestGameMessagesender {
	/**
	 * Give an array of paths to the json messages.   For example, if you wanted to sent the golCollection message, you would given an array of:
	 * 	{"GoldCollection/goldCollection"}
	 * @param paths
	 * @throws IOException
	 */
	public static void main(String[] paths) throws IOException {
		for(String path : paths) {
			 BufferedReader br = new BufferedReader(new FileReader("SampleMessages/Phases/"+path+".json"));
			    try {
			        StringBuilder sb = new StringBuilder();
			        String line = br.readLine();

			        while (line != null) {
			            sb.append(line);
			            sb.append("\n");
			            line = br.readLine();
			        }
			        String json = sb.toString();
			        String host = System.getProperty("host", "localhost");
			        String port = System.getProperty("port", "3004");
			        UDPMessage message = new UDPMessage(host, Integer.parseInt(port), json);
			        MessageSender.sendUDPMessage(message);
			    } finally {
			        br.close();
			    }
		}
	}
	
}
