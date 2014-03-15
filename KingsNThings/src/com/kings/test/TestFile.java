package com.kings.test;

import com.kings.database.DataAccess;
import com.kings.model.SentMessage;
import com.kings.model.User;

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
		User u = access.get(User.class, "1");
		SentMessage sm = new SentMessage();
		sm.setMessageId("t1");
		sm.setSentToUser(u);
		
		access.save(sm);
		access.commit();
	}
}
