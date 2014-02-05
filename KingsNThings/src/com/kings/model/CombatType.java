package com.kings.model;


public class CombatType {
	private String name;
	private String id; 
	public final static CombatType MAGIC = new CombatType("Magic", "CT_Magic");
	public final static CombatType RANGE = new CombatType("Range", "CT_Range");
	public final static CombatType MELEE = new CombatType("Melee", "CT_Melee");
	public final static CombatType FLYING = new CombatType("Flying", "CT_Flying");
	public final static CombatType CHARGED = new CombatType("Charged", "CT_Charged");
	
	public CombatType(String name, String id) {
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
