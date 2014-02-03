package com.kings.model;

import java.util.HashMap;

public class SpecialCharacter extends Counter {
	private SpecialCharacter otherSide;
	private SpecialAbility specialAbility;
	
	public SpecialCharacter(String id, String name, SpecialAbility specialAbility) {
		super(id, name);
		this.specialAbility=specialAbility;
	}
	
	public SpecialCharacter getOtherSide() {
		return otherSide;
	}
	public void setOtherSide(SpecialCharacter otherSide) {
		this.otherSide = otherSide;
	}
	public SpecialAbility getSpecialAbility() {
		return specialAbility;
	}
	public void setSpecialAbility(SpecialAbility specialAbility) {
		this.specialAbility = specialAbility;
	}
	
	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		// Will add later specifics later
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("Marksman", new SpecialCharacter("specialcharacter_01", "Marksman",SpecialAbility.markmanCounter));
		map.put("SirLanceALot", new SpecialCharacter("specialcharacter_02", "SirLanceALot",SpecialAbility.markmanCounter));
		map.put("ArchMage", new SpecialCharacter("specialcharacter_03", "ArchMage",SpecialAbility.markmanCounter));
		map.put("DwarfKing", new SpecialCharacter("specialcharacter_04", "DwarfKing",SpecialAbility.markmanCounter));
		map.put("AssassinPrimus", new SpecialCharacter("specialcharacter_05", "AssassinPrimus",SpecialAbility.eliminatecounterNocombat));
		map.put("BaronMunchausen", new SpecialCharacter("specialcharacter_06", "BaronMunchausen",SpecialAbility.markmanCounter));
		map.put("GrandDuke", new SpecialCharacter("specialcharacter_07", "GrandDuke",SpecialAbility.markmanCounter));
		map.put("ElfLord", new SpecialCharacter("specialcharacter_08", "ElfLord",SpecialAbility.markmanCounter));
		map.put("MasterThief", new SpecialCharacter("specialcharacter_09", "MasterThief",SpecialAbility.masterCounterTheft));
		map.put("SwordMaster", new SpecialCharacter("specialcharacter_10", "SwordMaster",SpecialAbility.swordElimnator));
		map.put("ArchCleric", new SpecialCharacter("specialcharacter_11", "ArchCleric",SpecialAbility.markmanCounter));

		return null;
	}
}
