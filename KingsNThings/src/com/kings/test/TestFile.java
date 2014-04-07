package com.kings.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.kings.database.DataAccess;
import com.kings.http.HttpResponseMessage;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.CombatType;
import com.kings.model.Creature;
import com.kings.model.HexLocation;
import com.kings.model.SentMessage;
import com.kings.model.SpecialAbility;
import com.kings.model.SpecialCharacter;
import com.kings.model.User;
import com.kings.model.factory.GameStateFactory;
import com.kings.util.Utils;

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
		
		
		
		for(int i=0; i<37; i++) {
			System.out.println("[d setObject:[[TileLocation alloc] initWithHexNumber:"+i+" andColumn: andRow:] forKey:@\""+i+"\"];");
		}
	}
}
