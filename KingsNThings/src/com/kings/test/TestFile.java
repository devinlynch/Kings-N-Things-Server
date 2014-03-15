package com.kings.test;

import com.kings.database.DataAccess;

public class TestFile {
	public static void main(String[] args) {
		//t1();
		//for(int i=0; i<37; i++) {
		//	System.out.println("gameState.getHexlocations().get("+i+").setHexTile((HexTile)gameState.getGamePiece(\"frozen-tile-01\"));");
		//}
		
		/*List<String> list = new ArrayList<String>();
		list.add("test");
		System.out.println(list.size());
		list.remove("test");
		System.out.println(list.size());*/
		
		
		DataAccess access = new DataAccess();
		access.beginTransaction();
		access.getQueuedMessagesForUser("1");
		access.commit();
	}
}
