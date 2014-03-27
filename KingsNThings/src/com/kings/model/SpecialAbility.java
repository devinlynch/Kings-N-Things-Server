package com.kings.model;


public class SpecialAbility {
	private String name;
	private String id;
	
	public final static SpecialAbility eliminatecounterNocombat = new SpecialAbility("SA_eliminatecounterNocombat","eliminatecounterNocombat");
	public final static SpecialAbility hitsBeforeRoundStarts = new SpecialAbility("SA_hitsBeforeRoundStarts","hitsBeforeRoundStarts");
	public final static SpecialAbility movementAnyTerrain = new SpecialAbility("SA_movementAnyTerrain","movementAnyTerrain");
	public final static SpecialAbility goldValueDoubled = new SpecialAbility("SA_goldValueDoubled","goldValueDoubled");
	public final static SpecialAbility markmanCounter = new SpecialAbility("SA_markmanCounter","markmanCounter");
	public final static SpecialAbility masterCounterTheft = new SpecialAbility("SA_masterCounterTheft","masterCounterTheft");
	public final static SpecialAbility swordElimnator = new SpecialAbility("SA_swordElimnator","swordElimnator");
	public final static SpecialAbility supportingTerrainLord = new SpecialAbility("SA_supportingTerrainLord","supportingTerrainLord");
	public final static SpecialAbility warlordJoinMySide = new SpecialAbility("SA_warlordJoinMySide","warlordJoinMySide");

	public SpecialAbility(String id, String name) {
		super();
		this.id=id;
		this.name=name;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
