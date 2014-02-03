package com.kings.model;

import java.util.HashMap;

public class MagicItem extends Thing {

	public MagicItem(String id, String name) {
		super(id, name);
	}

	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("Abwehrstaub", new MagicItem("magic_01", "Abwehrstaub"));
		map.put("Ballon", new MagicItem("magic_02", "Ballon"));
		map.put("Blasebalg", new MagicItem("magic_03", "Blasebalg"));
		map.put("Elixier", new MagicItem("magic_04", "Elixier"));
		map.put("Feuerwand", new MagicItem("magic_05", "Feuerwand"));
		map.put("Glucksbringer", new MagicItem("magic_06", "Glucksbringer"));
		map.put("Golem", new MagicItem("magic_07", "Golem"));
		map.put("MagieBannen", new MagicItem("magic_08", "MagieBannen"));
		map.put("Talisman", new MagicItem("magic_09", "Talisman"));
		map.put("Zauberbogen", new MagicItem("magic_10", "Zauberbogen"));
		map.put("Zauberschwert", new MagicItem("magic_11", "Zauberschwert"));
		return null;
	}

}
