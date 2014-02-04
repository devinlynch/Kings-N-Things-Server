package com.kings.model;

import java.util.Map;

public class Bank extends BoardLocation {
	private int gold;
	public Bank(String id, int initialGold){
		super(id, "Bank");
		setGold(initialGold);
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void payoutGoldToPlayer(int gold, Player p) {
		this.gold = this.gold-gold;
		p.addGold(gold);
	}
	
	@Override
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = super.toSerializedFormat();
		map.put("gold", gold);
		return map;
	}
}
