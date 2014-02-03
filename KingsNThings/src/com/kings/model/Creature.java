package com.kings.model;

import java.util.HashMap;

public class Creature extends Thing {
	private int combatValue;
	private CombatType combatType;
	private Terrain terrainType;
	
	public Creature(String id, String name,CombatType combatType,int combatValue, Terrain terrainType) {
		super(id, name);
		this.combatValue=combatValue;
		this.combatType=combatType;
		this.terrainType=terrainType;
	}
	
	public int getCombatValue() {
		return combatValue;
	}
	public void setCombatValue(int combatValue) {
		this.combatValue = combatValue;
	}
	public CombatType getCombatType() {
		return combatType;
	}
	public void setCombatType(CombatType combatType) {
		this.combatType = combatType;
	}
	public Terrain getTerrainType() {
		return terrainType;
	}
	public void setTerrainType(Terrain terrainType) {
		this.terrainType = terrainType;
	}

	@Override
	public HashMap<String, GamePiece> getMapOfInstances() {
		// TODO GABE
		HashMap<String, GamePiece> map = new HashMap<String, GamePiece>();
		map.put("AlterDrache", new Creature("T_Desert_105-01", "AlterDrache",CombatType.MAGIC,4, Terrain.DESERT_TERRAIN));
		map.put("Babydrache", new Creature("T_Desert_106-01", "Babydrache",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("Bussard", new Creature("T_Desert_107-01", "Bussard",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("Derwisch", new Creature("T_Desert_108-01", "Derwisch",CombatType.MAGIC,2, Terrain.DESERT_TERRAIN));
		map.put("Derwisch2", new Creature("T_Desert_108-02", "Derwisch2",CombatType.MAGIC,2, Terrain.DESERT_TERRAIN));
		map.put("Dschinn", new Creature("T_Desert_109-01", "Dschinn",CombatType.MAGIC,4, Terrain.DESERT_TERRAIN));
		map.put("Geier", new Creature("T_Desert_110-01", "Geier",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("GelberRitter", new Creature("T_Desert_111-01","GelberRitter",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("Greif", new Creature("T_Desert_112-01", "Greif",CombatType.MELEE,2, Terrain.DESERT_TERRAIN));
		map.put("Kamelreiter", new Creature("T_Desert_113-01", "Kamelreiter",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("Nomade", new Creature("T_Desert_114-01", "Nomade",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("Nomade2", new Creature("T_Desert_114-02", "Nomade2",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("Riesenspinne", new Creature("T_Desert_115-01", "Riesenspinne",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("Riesenwespe", new Creature("T_Desert_116-01", "Riesenwespe",CombatType.MELEE,4, Terrain.DESERT_TERRAIN));
		map.put("Riesenwespe2", new Creature("T_Desert_116-02", "Riesenwespe2",CombatType.MELEE,4, Terrain.DESERT_TERRAIN));
		map.put("Sandwurm", new Creature("T_Desert_117-01", "Sandwurm",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("Skelett", new Creature("T_Desert_118-01", "Skelett",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("Skelett2", new Creature("T_Desert_118-02", "Skelett2",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("Sphinx", new Creature("T_Desert_119-01", "Sphinx",CombatType.MAGIC,4, Terrain.DESERT_TERRAIN));
		map.put("Staubteufel", new Creature("T_Desert_120-01", "Staubteufel",CombatType.MELEE,4, Terrain.DESERT_TERRAIN));
		map.put("Wüstenfledermaus", new Creature("T_Desert_122-01", "Wüstenfledermaus",CombatType.MELEE,1,Terrain.DESERT_TERRAIN));
		
		//Forest
		map.put("Banditen", new Creature("T_Forest_086-01", "Banditen",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("Bären", new Creature("T_Forest_087-01", "Bären",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("Druide", new Creature("T_Forest_088-01", "Druide",CombatType.MAGIC,3, Terrain.FOREST_TERRAIN));
		map.put("Dryade", new Creature(" T_Forest_089-01", "Dryade",CombatType.MAGIC,1, Terrain.FOREST_TERRAIN));
		map.put("Einhorn", new Creature("T_Forest_090-01", "Einhorn",CombatType.MELEE,4, Terrain.FOREST_TERRAIN));
		map.put("Elf", new Creature("T_Forest_091-01", "Elf",CombatType.RANGE,2, Terrain.FOREST_TERRAIN));
		map.put("Elf2", new Creature("T_Forest_091-02", "Elf2",CombatType.RANGE,2, Terrain.FOREST_TERRAIN));
		map.put("Elf3", new Creature("T_Forest_092-01", "Elf3",CombatType.RANGE,3, Terrain.FOREST_TERRAIN));
		map.put("Elfenmagier", new Creature("T_Forest_093-01", "Elfenmagier",CombatType.MAGIC,2, Terrain.FOREST_TERRAIN));
		map.put("Flugeichhörnchen", new Creature("T_Forest_094-01", "Flugeichhörnchen",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("Flugeichhörnchen2", new Creature("T_Forest_095-01", "Flugeichhörnchen2",CombatType.MELEE,1,Terrain.FOREST_TERRAIN));
		map.put("Großeule", new Creature("T_Forest_096-01", "Großeule",CombatType.MELEE,4, Terrain.FOREST_TERRAIN));
		map.put("GrünerRitter", new Creature("T_Forest_097-01", "GrünerRitter",CombatType.MELEE,4, Terrain.FOREST_TERRAIN));
		map.put("Laufbaum", new Creature("T_Forest_098-01", "Laufbaum",CombatType.MELEE,5, Terrain.FOREST_TERRAIN));
		map.put("Lindwurm", new Creature("T_Forest_099-01", "Lindwurm",CombatType.MELEE,3, Terrain.FOREST_TERRAIN));
		map.put("Mörderwaschbär", new Creature("T_Forest_100-01", "Mörderwaschbär",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("Waldläufer", new Creature("T_Forest_101-01", "Waldläufer",CombatType.RANGE,2, Terrain.FOREST_TERRAIN));
		map.put("Wichtelmann", new Creature("T_Forest_102-01", "Wichtelmann",CombatType.MELEE,1, Terrain.FOREST_TERRAIN));
		map.put("Wichtelmann2", new Creature("T_Forest_102-02", "Wichtelmann2",CombatType.MELEE,1, Terrain.FOREST_TERRAIN));
		map.put("Wildkatze", new Creature("T_Forest_103-01", "Wildkatze",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("Yeti", new Creature("T_Forest_104-01", "Yeti",CombatType.MELEE,5, Terrain.FOREST_TERRAIN));
		
		//Frozen
		map.put("Drachenreiter", new Creature("T_Frozen_Waste_051-01", "Drachenreiter",CombatType.RANGE,3, Terrain.FROZEN_TERRAIN));
		map.put("Eisbär", new Creature("T_Frozen_Waste_052-01", "Eisbär",CombatType.MELEE,4, Terrain.FROZEN_TERRAIN));
		map.put("Eisfledermaus", new Creature("T_Frozen_Waste_053-01", "Eisfledermaus",CombatType.MELEE,1, Terrain.FROZEN_TERRAIN));
		map.put("Eisriese", new Creature(" T_Frozen_Waste_054-01", "Eisriese",CombatType.RANGE,5, Terrain.FROZEN_TERRAIN));
		map.put("Eiswurm", new Creature("T_Frozen_Waste_055-01", "Eiswurm",CombatType.MAGIC,4, Terrain.FROZEN_TERRAIN));
		map.put("Elchherde", new Creature("T_Frozen_Waste_056-01", "Elchherde",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("Eskimo", new Creature("T_Frozen_Waste_057-01", "Eskimo",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("Eskimo2", new Creature("T_Frozen_Waste_057-02", "Eskimo2",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("Eskimo3", new Creature("T_Frozen_Waste_057-03", "Eskimo3",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("Eskimo4", new Creature("T_Frozen_Waste_057-04", "Eskimo4",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("Mammut", new Creature("T_Frozen_Waste_058-01", "Mammut",CombatType.MELEE,5,Terrain.FROZEN_TERRAIN));
		map.put("Mörderpapageientaucher", new Creature("T_Frozen_Waste_059-01", "Mörderpapageientaucher",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("Mörderpinguin", new Creature("T_Frozen_Waste_060-01", "Mörderpinguin",CombatType.MELEE,3, Terrain.FROZEN_TERRAIN));
		map.put("Nordwind", new Creature("T_Frozen_Waste_061-01", "Nordwind",CombatType.MAGIC,2, Terrain.FROZEN_TERRAIN));
		map.put("Walroß", new Creature("T_Frozen_Waste_062-01", "Walroß",CombatType.MELEE,4, Terrain.FROZEN_TERRAIN));
		map.put("WeißerDrachen", new Creature("T_Frozen_Waste_063-01", "WeißerDrachen",CombatType.MAGIC,5, Terrain.FROZEN_TERRAIN));
		map.put("Wolf", new Creature("T_Frozen_Waste_064-01", "Wolf",CombatType.MELEE,3, Terrain.FROZEN_TERRAIN));
		
		//Jungle
		map.put("Dinosaurier", new Creature("T_Jungle_000-01", "Dinosaurier",CombatType.MELEE,4, Terrain.JUNGLE_TERRAIN));
		map.put("Dinosaurier2", new Creature("T_Jungle_000-02", "Dinosaurier2",CombatType.MELEE,4, Terrain.JUNGLE_TERRAIN));
		map.put("Elefant", new Creature("T_Jungle_001-01", "Elefant",CombatType.MELEE,4, Terrain.JUNGLE_TERRAIN));
		map.put("Flugsaurierkrieger", new Creature(" T_Jungle_002-01", "Flugsaurierkrieger",CombatType.RANGE,2, Terrain.JUNGLE_TERRAIN));
		map.put("Flugsaurierkrieger2", new Creature("T_Jungle_002-02", "Flugsaurierkrieger2",CombatType.RANGE,2, Terrain.JUNGLE_TERRAIN));
		map.put("Kletterranken", new Creature("T_Jungle_003-01", "Kletterranken",CombatType.MELEE,6, Terrain.JUNGLE_TERRAIN));
		map.put("Kopfjäger", new Creature("T_Jungle_004-01", "Kopfjäger",CombatType.RANGE,2, Terrain.JUNGLE_TERRAIN));
		map.put("Krokodile", new Creature("T_Jungle_005-01", "Krokodile",CombatType.MELEE,2, Terrain.JUNGLE_TERRAIN));
		map.put("Medizinmann", new Creature("T_Jungle_006-01", "Medizinmann",CombatType.MAGIC,2, Terrain.JUNGLE_TERRAIN));
		map.put("Paradiesvogel", new Creature("T_Jungle_007-01", "Paradiesvogel",CombatType.MELEE,1, Terrain.JUNGLE_TERRAIN));
		map.put("Pygmäe", new Creature("T_Jungle_008-01", "Pygmäe",CombatType.MELEE,2,Terrain.JUNGLE_TERRAIN));
		map.put("Riesenaffe", new Creature("T_Jungle_009-01", "Riesenaffe",CombatType.MELEE,5, Terrain.JUNGLE_TERRAIN));
		map.put("Riesenaffe2", new Creature("T_Jungle_009-02", "Riesenaffe2",CombatType.MELEE,5, Terrain.JUNGLE_TERRAIN));
		map.put("Riesenschlange", new Creature("T_Jungle_010-01", "Riesenschlange",CombatType.MELEE,3, Terrain.JUNGLE_TERRAIN));
		map.put("Tiger", new Creature("T_Jungle_011-01", "Tiger",CombatType.MELEE,3, Terrain.JUNGLE_TERRAIN));
		map.put("Tiger2", new Creature("T_Jungle_011-02", "Tiger2",CombatType.MELEE,3, Terrain.JUNGLE_TERRAIN));
		map.put("Watussi", new Creature("T_Jungle_012-01", "Watussi",CombatType.MELEE,2, Terrain.JUNGLE_TERRAIN));
		
		//Mountains
		map.put("Bergbewohner", new Creature("T_Mountains_034-01", "Bergbewohner",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("Bergbewohner2", new Creature("T_Mountains_034-02", "Bergbewohner2",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("Berglöwe", new Creature("T_Mountains_035-01", "Berglöwe",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("BraunerDrache", new Creature(" T_Mountains_036-01", "BraunerDrache",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("BraunerRitter", new Creature("T_Mountains_037-01", "BraunerRitter",CombatType.MELEE,4, Terrain.MOUNTAIN_TERRAIN));
		map.put("Goblin", new Creature("T_Mountains_038-01", "Goblin",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("Goblin2", new Creature("T_Mountains_038-02", "Goblin2",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("Goblin3", new Creature("T_Mountains_038-03", "Goblin3",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("Goblin4", new Creature("T_Mountains_038-04", "Goblin4",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("Großadler", new Creature("T_Mountains_039-01", "Großadler",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("Großfalke", new Creature("T_Mountains_040-01", "Großfalke",CombatType.MELEE,1,Terrain.MOUNTAIN_TERRAIN));
		map.put("KleinerRockvogel", new Creature("T_Mountains_041-01", "KleinerRockvogel",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("Oger", new Creature("T_Mountains_042-01", "Oger",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("Riese", new Creature("T_Mountains_043-01", "Riese",CombatType.RANGE,4, Terrain.MOUNTAIN_TERRAIN));
		map.put("Riesenkondor", new Creature("T_Mountains_044-01", "Riesenkondor",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("Riesenrockvogel", new Creature("T_Mountains_045-01", "Riesenrockvogel",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("Troll", new Creature("T_Mountains_046-01", "Troll",CombatType.MELEE,4, Terrain.MOUNTAIN_TERRAIN));
		map.put("Zwerg", new Creature("T_Mountains_047-01", "Zwerg",CombatType.RANGE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("Zwerg2", new Creature("T_Mountains_048-01", "Zwerg2",CombatType.RANGE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("Zwerg3", new Creature("T_Mountains_049-01", "Zwerg3",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("Zyklop", new Creature("T_Mountains_048-01", "Zyklop",CombatType.MELEE,5, Terrain.MOUNTAIN_TERRAIN));

		//Plains
		map.put("Adler", new Creature("T_Plains_013-01", "Adler",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Bauer", new Creature("T_Plains_014-01", "Bauer",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("Bauer2", new Creature("T_Plains_014-02", "Bauer2",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("Bauer3", new Creature("T_Plains_014-03", "Bauer3",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("Bauer4", new Creature("T_Plains_014-04", "Bauer4",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("Bösewicht", new Creature("T_Plains_015-01", "Bösewicht",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Büffelherde", new Creature("T_Plains_016-01", "Büffelherde",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("Büffelherde2", new Creature("T_Plains_017-01", "Büffelherde2",CombatType.MELEE,4, Terrain.PLAINS_TERRAIN));
		map.put("Flugbüffel", new Creature("T_Plains_018-01", "Flugbüffel",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Flugsaurier", new Creature("T_Plains_019-01", "Flugsaurier",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("Großfalke", new Creature("T_Plains_020-01", "Großfalke",CombatType.MELEE,2,Terrain.PLAINS_TERRAIN));
		map.put("Großjäger", new Creature("T_Plains_021-01", "Großjäger",CombatType.RANGE,4, Terrain.PLAINS_TERRAIN));
		map.put("Jäger", new Creature("T_Plains_022-01", "Jäger",CombatType.RANGE,1, Terrain.PLAINS_TERRAIN));
		map.put("Libelle", new Creature("T_Plains_023-01", "Libelle",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Pegasus", new Creature("T_Plains_024-01", "Pegasus",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Prachtlöwe", new Creature("T_Plains_025-01", "Prachtlöwe",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("Riesenkäfer", new Creature("T_Plains_026-01", "Riesenkäfer",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Stammeskrieger", new Creature("T_Plains_027-01", "Stammeskrieger",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Stammeskrieger2", new Creature("T_Plains_027-02", "Stammeskrieger2",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Stammeskrieger3", new Creature("T_Plains_028-01", "Stammeskrieger3",CombatType.RANGE,1, Terrain.PLAINS_TERRAIN));
		map.put("WeißerRitter", new Creature("T_Plains_029-01", "WeißerRitter",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("Wolfsmeute", new Creature("T_Plains_030-01", "Wolfsmeute",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("Zentaur", new Creature("T_Plains_031-01", "Zentaur",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("Zigeuner", new Creature("T_Plains_032-01", "Zigeuner",CombatType.MAGIC,1, Terrain.PLAINS_TERRAIN));
		map.put("Zigeuner2", new Creature("T_Plains_033-01", "Zigeuner2",CombatType.MAGIC,2, Terrain.PLAINS_TERRAIN));

		//Swamp
		map.put("Basilisk", new Creature("T_Swamp_065-01", "Basilisk",CombatType.MAGIC,3, Terrain.SWAMP_TERRAIN));
		map.put("Ding", new Creature("T_Swamp_066-01", "Ding",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("Flugpiranha", new Creature("T_Swamp_067-01", "Flugpiranha",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("Geist", new Creature("T_Swamp_068-01", "Geist",CombatType.MAGIC,2, Terrain.SWAMP_TERRAIN));
		map.put("Gespenst", new Creature("T_Swamp_069-01", "Gespenst",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("Gespenst2", new Creature("T_Swamp_069-02", "Gespenst2",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("Gespenst3", new Creature("T_Swamp_069-03", "Gespenst3",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("Gespenst4", new Creature("T_Swamp_069-04", "Gespenst4",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("Giftfrosch", new Creature("T_Swamp_070-01", "Giftfrosch",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("Irrlicht", new Creature("T_Swamp_071-01", "Irrlicht",CombatType.MAGIC,2, Terrain.SWAMP_TERRAIN));
		map.put("Kobold", new Creature("T_Swamp_072-01", "Kobold",CombatType.MAGIC,1, Terrain.SWAMP_TERRAIN));
		map.put("Krokodile", new Creature("T_Swamp_073-01", "Krokodile",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("Piraten", new Creature("T_Swamp_074-01", "Piraten",CombatType.MELEE,2,Terrain.SWAMP_TERRAIN));
		map.put("Riesenblutegel", new Creature("T_Swamp_075-01", "Riesenblutegel",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("Riesenechse", new Creature("T_Swamp_076-01", "Riesenechse",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("Riesenechse2", new Creature("T_Swamp_076-02", "Riesenechse2",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("Riesenmoskito", new Creature("T_Swamp_077-01", "Riesenmoskito",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("Riesenschlange", new Creature("T_Swamp_078-01", "Riesenschlange",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("Schleimbestie", new Creature("T_Swamp_079-01", "Schleimbestie",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("SchwartzeRitter", new Creature("T_Swamp_080-01", "SchwartzeRitter",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("Schwarzmagier", new Creature("T_Swamp_081-01", "Schwarzmagier",CombatType.MAGIC,1, Terrain.SWAMP_TERRAIN));
		map.put("Sumpfgas", new Creature("T_Swamp_082-01", "Sumpfgas",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("Sumpfratte", new Creature("T_Swamp_083-01", "Sumpfratte",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("Vampirfledermaus", new Creature("T_Swamp_084-01", "Vampirfledermaus",CombatType.MELEE,4, Terrain.SWAMP_TERRAIN));
		map.put("Wasserschlange", new Creature("T_Swamp_085-01", "Wasserschlange",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		
		return map;
	}
}
