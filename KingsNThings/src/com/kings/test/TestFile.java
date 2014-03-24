package com.kings.test;

import java.util.Arrays;
import java.util.List;

import com.kings.database.DataAccess;
import com.kings.model.HexLocation;
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
		
		
		for(int i= 0; i<37; i++) {
			List<Integer> l = HexLocation.getAdjacentHexIndices(i);
			for(Integer z : l) {
				List<Integer> l2 = HexLocation.getAdjacentHexIndices(z);
				if(!l2.contains(i)) {
					System.out.println("Check " +i+ " and " +z);
				}
			}
		}
	}
}
