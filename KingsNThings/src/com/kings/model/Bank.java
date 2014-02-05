package com.kings.model;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private int gold;
	public Bank(String id, int initialGold){
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
	
	public Map<String, Object> toSerializedFormat() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentTotal", gold);
		return map;
	}
}
