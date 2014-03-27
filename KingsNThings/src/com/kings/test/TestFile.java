package com.kings.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.kings.database.DataAccess;
import com.kings.http.HttpResponseMessage;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.model.CombatType;
import com.kings.model.HexLocation;
import com.kings.model.SentMessage;
import com.kings.model.SpecialAbility;
import com.kings.model.SpecialCharacter;
import com.kings.model.User;
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
		
		
		HashMap<String, SpecialCharacter> map = new HashMap<String, SpecialCharacter>();
		map.put("specialcharacter_01", new SpecialCharacter("specialcharacter_01", "Marksman",SpecialAbility.markmanCounter,5, CombatType.RANGE));
		map.put("specialcharacter_02", new SpecialCharacter("specialcharacter_02", "SirLanceALot",SpecialAbility.markmanCounter, 5, CombatType.MELEE));
		map.put("specialcharacter_03", new SpecialCharacter("specialcharacter_03", "ArchMage",SpecialAbility.markmanCounter, 6, CombatType.MAGIC));
		map.put("specialcharacter_04", new SpecialCharacter("specialcharacter_04", "DwarfKing",SpecialAbility.markmanCounter, 5, CombatType.MELEE));
		map.put("specialcharacter_05", new SpecialCharacter("specialcharacter_05", "AssassinPrimus",SpecialAbility.eliminatecounterNocombat,4, CombatType.MAGIC));
		map.put("specialcharacter_06", new SpecialCharacter("specialcharacter_06", "BaronMunchausen",SpecialAbility.markmanCounter, 4, CombatType.MELEE));
		map.put("specialcharacter_07", new SpecialCharacter("specialcharacter_07", "GrandDuke",SpecialAbility.markmanCounter, 4, CombatType.MELEE));
		map.put("specialcharacter_08", new SpecialCharacter("specialcharacter_08", "ElfLord",SpecialAbility.markmanCounter, 6, CombatType.RANGE));
		map.put("specialcharacter_09", new SpecialCharacter("specialcharacter_09", "MasterThief",SpecialAbility.masterCounterTheft, 4, CombatType.MELEE));
		map.put("specialcharacter_10", new SpecialCharacter("specialcharacter_10", "SwordMaster",SpecialAbility.swordElimnator, 4, CombatType.MELEE));
		map.put("specialcharacter_11", new SpecialCharacter("specialcharacter_11", "ArchCleric",SpecialAbility.markmanCounter, 5, CombatType.MAGIC));
	
		for(String key : map.keySet()) {
			SpecialCharacter c = map.get(key);
			
			//String s = " [specialCharacters setObject:[[SpecialCharacter alloc] initWithId:@\""+c.getId()+"\" andSpecialAbility: [SpecialAbility get"+c.getSpecialAbility().getName()+"Instance] andFilename:@\"\" andCombatType:[CombatType getRangeInstance] andCombatValue:4 andCanCharge:false andIsFlyingCreature:false] forKey:@\""+c.getId()+"\"];";
			String s = c.getId() +" - "+ c.getName();
			System.out.println(s);
		}
	}
}
