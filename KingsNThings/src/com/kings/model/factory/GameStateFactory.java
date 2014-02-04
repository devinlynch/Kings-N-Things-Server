package com.kings.model.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kings.model.CityVill;
import com.kings.model.CombatType;
import com.kings.model.Creature;
import com.kings.model.Fort;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.HexTile;
import com.kings.model.MagicItem;
import com.kings.model.NonCityVill;
import com.kings.model.PlayingCup;
import com.kings.model.RandomEvent;
import com.kings.model.SideLocation;
import com.kings.model.SpecialAbility;
import com.kings.model.SpecialCharacter;
import com.kings.model.Terrain;
import com.kings.model.Treasure;

/**
 * This class populates a given GameState object with all needed objects, pieces, etc. for playing a game
 * @author devinlynch
 *
 */
public class GameStateFactory {
	
	//TODO implement
	
	public static GameState makeGameState(GameState gameState, int numberOfPlayers) throws Exception {
		if(numberOfPlayers < 2 || numberOfPlayers > 4)
			throw new Exception("Number of players must be between 2 and 4");
		
		boolean is23PlayerGame = numberOfPlayers==2 || numberOfPlayers==3;
		if(is23PlayerGame) {
			addHexLocations(gameState, 19);
		} else{
			addHexLocations(gameState, 37);
		}
		
		addPiecesToDefaultLocations(gameState);
		
		
		switch(numberOfPlayers){
			case 2: {
			}
			case 3: {
			}
			case 4: {
				
			}
		}
		
		return gameState;
	}
	
	public static void addHexLocations(GameState gameState, int numHexes){
		for(int i=0; i<numHexes; i++) {
			HexLocation hex = new HexLocation("hexLocation_"+i);
			gameState.getHexlocations().add(hex);
		}
	}
	
	public static void addPiecesToDefaultLocations(GameState gameState) {
		Set<GamePiece> allGamePieces = new HashSet<GamePiece>();
		SideLocation side = gameState.getSideLocation();
		PlayingCup cup = gameState.getPlayingCup();
		
		// Counters defaulted to playing cup
		Set<GamePiece> countersForPlayingCup = getCountersDefaultedToPlayingCup();
		allGamePieces.addAll(countersForPlayingCup);
		cup.addGamePiecesToLocation(countersForPlayingCup);
		
		// Hex Tiles
		Set<GamePiece> hexTiles = getHexTiles();
		allGamePieces.addAll(hexTiles);
		side.addGamePiecesToLocation(hexTiles);
		
		// Special Characters
		Set<GamePiece> spChars = getSpecialCharacters();
		allGamePieces.addAll(spChars);
		side.addGamePiecesToLocation(spChars);
		
		gameState.setGamePieces(allGamePieces);
	}
	
	public static Set<GamePiece> getSpecialCharacters() {
		Set<GamePiece> tiles = new HashSet<GamePiece>();
		tiles.addAll(getSpecialCharacterMap().values());
		return tiles;
	}
	
	public static Set<GamePiece> getHexTiles() {
		Set<GamePiece> tiles = new HashSet<GamePiece>();
		tiles.addAll(getHexTileMap().values());
		return tiles;
	}
	
	public static Set<GamePiece> getCountersDefaultedToPlayingCup() {
		Set<GamePiece> counters = new HashSet<GamePiece>();
		
		counters.addAll(getFortMap().values());
		counters.addAll(getCreatureMap().values());
		counters.addAll(getTreasureMap().values());
		counters.addAll(getMagicItemMap().values());
		counters.addAll(getNonCityVillMap().values());
		counters.addAll(getCityVillMap().values());
		counters.addAll(getRandomEventMap().values());
		
		return counters;
	}
	
	
	public static Map<String, Fort> getFortMap() {
		//To get a citadel it costs 5 gold but you must have a castle and you have more than 20 gold
		HashMap<String, Fort> map = new HashMap<String, Fort>();
		map.put("Fort_01-01", new Fort("Fort_01-01", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-02", new Fort("Fort_01-02", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-03", new Fort("Fort_01-03", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-04", new Fort("Fort_01-04", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-05", new Fort("Fort_01-05", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-06", new Fort("Fort_01-06", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-07", new Fort("Fort_01-07", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-08", new Fort("Fort_01-08", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-09", new Fort("Fort_01-09", "Fort",1,5,CombatType.MELEE));
		map.put("Fort_01-10", new Fort("Fort_01-10", "Fort",1,5,CombatType.MELEE));

		map.put("Fort_02-01", new Fort("Fort_02-01", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-02", new Fort("Fort_02-02", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-03", new Fort("Fort_02-03", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-04", new Fort("Fort_02-04", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-05", new Fort("Fort_02-05", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-06", new Fort("Fort_02-06", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-07", new Fort("Fort_02-07", "Fort",2,5,CombatType.MELEE));
		map.put("Fort_02-08", new Fort("Fort_02-08", "Fort",2,5,CombatType.MELEE));
		
		map.put("Fort_03-01", new Fort("Fort_03-01", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-02", new Fort("Fort_03-02", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-03", new Fort("Fort_03-03", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-04", new Fort("Fort_03-04", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-05", new Fort("Fort_03-05", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-06", new Fort("Fort_03-06", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-07", new Fort("Fort_03-07", "Fort",3,5,CombatType.RANGE));
		map.put("Fort_03-08", new Fort("Fort_03-08", "Fort",3,5,CombatType.RANGE));
		
		map.put("Fort_04-01", new Fort("Fort_04-01", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-02", new Fort("Fort_04-02", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-03", new Fort("Fort_04-03", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-04", new Fort("Fort_04-04", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-05", new Fort("Fort_04-05", "Fort",4,5,CombatType.MAGIC));
		map.put("Fort_04-06", new Fort("Fort_04-06", "Fort",4,5,CombatType.MAGIC));
		return map;
	}
	
	public static Map<String, Creature> getCreatureMap() {
		HashMap<String, Creature> map = new HashMap<String, Creature>();
		map.put("T_Desert_105-01", new Creature("T_Desert_105-01", "AlterDrache",CombatType.MAGIC,4, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_106-01", new Creature("T_Desert_106-01", "Babydrache",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_107-01", new Creature("T_Desert_107-01", "Bussard",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_108-01", new Creature("T_Desert_108-01", "Derwisch",CombatType.MAGIC,2, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_108-02", new Creature("T_Desert_108-02", "Derwisch2",CombatType.MAGIC,2, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_109-01", new Creature("T_Desert_109-01", "Dschinn",CombatType.MAGIC,4, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_110-01", new Creature("T_Desert_110-01", "Geier",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_111-01", new Creature("T_Desert_111-01","GelberRitter",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_112-01", new Creature("T_Desert_112-01", "Greif",CombatType.MELEE,2, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_113-01", new Creature("T_Desert_113-01", "Kamelreiter",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_114-01", new Creature("T_Desert_114-01", "Nomade",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_114-02", new Creature("T_Desert_114-02", "Nomade2",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_115-01", new Creature("T_Desert_115-01", "Riesenspinne",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_116-01", new Creature("T_Desert_116-01", "Riesenwespe",CombatType.MELEE,4, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_116-02", new Creature("T_Desert_116-02", "Riesenwespe2",CombatType.MELEE,4, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_117-01", new Creature("T_Desert_117-01", "Sandwurm",CombatType.MELEE,3, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_118-01", new Creature("T_Desert_118-01", "Skelett",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_118-02", new Creature("T_Desert_118-02", "Skelett2",CombatType.MELEE,1, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_119-01", new Creature("T_Desert_119-01", "Sphinx",CombatType.MAGIC,4, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_120-01", new Creature("T_Desert_120-01", "Staubteufel",CombatType.MELEE,4, Terrain.DESERT_TERRAIN));
		map.put("T_Desert_122-01", new Creature("T_Desert_122-01", "Wüstenfledermaus",CombatType.MELEE,1,Terrain.DESERT_TERRAIN));
		map.put("T_Forest_086-01", new Creature("T_Forest_086-01", "Banditen",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_087-01", new Creature("T_Forest_087-01", "Bären",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_088-01", new Creature("T_Forest_088-01", "Druide",CombatType.MAGIC,3, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_089-01", new Creature("T_Forest_089-01", "Dryade",CombatType.MAGIC,1, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_090-01", new Creature("T_Forest_090-01", "Einhorn",CombatType.MELEE,4, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_091-01", new Creature("T_Forest_091-01", "Elf",CombatType.RANGE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_091-02", new Creature("T_Forest_091-02", "Elf2",CombatType.RANGE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_092-01", new Creature("T_Forest_092-01", "Elf3",CombatType.RANGE,3, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_093-01", new Creature("T_Forest_093-01", "Elfenmagier",CombatType.MAGIC,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_094-01", new Creature("T_Forest_094-01", "Flugeichhörnchen",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_095-01", new Creature("T_Forest_095-01", "Flugeichhörnchen2",CombatType.MELEE,1,Terrain.FOREST_TERRAIN));
		map.put("T_Forest_096-01", new Creature("T_Forest_096-01", "Großeule",CombatType.MELEE,4, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_097-01", new Creature("T_Forest_097-01", "GrünerRitter",CombatType.MELEE,4, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_098-01", new Creature("T_Forest_098-01", "Laufbaum",CombatType.MELEE,5, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_099-01", new Creature("T_Forest_099-01", "Lindwurm",CombatType.MELEE,3, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_100-01", new Creature("T_Forest_100-01", "Mörderwaschbär",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_101-01", new Creature("T_Forest_101-01", "Waldläufer",CombatType.RANGE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_102-01", new Creature("T_Forest_102-01", "Wichtelmann",CombatType.MELEE,1, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_102-02", new Creature("T_Forest_102-02", "Wichtelmann2",CombatType.MELEE,1, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_103-01", new Creature("T_Forest_103-01", "Wildkatze",CombatType.MELEE,2, Terrain.FOREST_TERRAIN));
		map.put("T_Forest_104-01", new Creature("T_Forest_104-01", "Yeti",CombatType.MELEE,5, Terrain.FOREST_TERRAIN));
		map.put("T_Frozen_Waste_051-01", new Creature("T_Frozen_Waste_051-01", "Drachenreiter",CombatType.RANGE,3, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_052-01", new Creature("T_Frozen_Waste_052-01", "Eisbär",CombatType.MELEE,4, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_053-01", new Creature("T_Frozen_Waste_053-01", "Eisfledermaus",CombatType.MELEE,1, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_054-01", new Creature("T_Frozen_Waste_054-01", "Eisriese",CombatType.RANGE,5, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_055-01", new Creature("T_Frozen_Waste_055-01", "Eiswurm",CombatType.MAGIC,4, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_056-01", new Creature("T_Frozen_Waste_056-01", "Elchherde",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_057-01", new Creature("T_Frozen_Waste_057-01", "Eskimo",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_057-02", new Creature("T_Frozen_Waste_057-02", "Eskimo2",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_057-03", new Creature("T_Frozen_Waste_057-03", "Eskimo3",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_057-04", new Creature("T_Frozen_Waste_057-04", "Eskimo4",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_058-01", new Creature("T_Frozen_Waste_058-01", "Mammut",CombatType.MELEE,5,Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_059-01", new Creature("T_Frozen_Waste_059-01", "Mörderpapageientaucher",CombatType.MELEE,2, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_060-01", new Creature("T_Frozen_Waste_060-01", "Mörderpinguin",CombatType.MELEE,3, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_061-01", new Creature("T_Frozen_Waste_061-01", "Nordwind",CombatType.MAGIC,2, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_062-01", new Creature("T_Frozen_Waste_062-01", "Walroß",CombatType.MELEE,4, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_063-01", new Creature("T_Frozen_Waste_063-01", "WeißerDrachen",CombatType.MAGIC,5, Terrain.FROZEN_TERRAIN));
		map.put("T_Frozen_Waste_064-01", new Creature("T_Frozen_Waste_064-01", "Wolf",CombatType.MELEE,3, Terrain.FROZEN_TERRAIN));
		map.put("T_Jungle_000-01", new Creature("T_Jungle_000-01", "Dinosaurier",CombatType.MELEE,4, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_000-02", new Creature("T_Jungle_000-02", "Dinosaurier2",CombatType.MELEE,4, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_001-01", new Creature("T_Jungle_001-01", "Elefant",CombatType.MELEE,4, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_002-01", new Creature("T_Jungle_002-01", "Flugsaurierkrieger",CombatType.RANGE,2, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_002-02", new Creature("T_Jungle_002-02", "Flugsaurierkrieger2",CombatType.RANGE,2, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_003-01", new Creature("T_Jungle_003-01", "Kletterranken",CombatType.MELEE,6, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_004-01", new Creature("T_Jungle_004-01", "Kopfjäger",CombatType.RANGE,2, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_005-01", new Creature("T_Jungle_005-01", "Krokodile",CombatType.MELEE,2, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_006-01", new Creature("T_Jungle_006-01", "Medizinmann",CombatType.MAGIC,2, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_007-01", new Creature("T_Jungle_007-01", "Paradiesvogel",CombatType.MELEE,1, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_008-01", new Creature("T_Jungle_008-01", "Pygmäe",CombatType.MELEE,2,Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_009-01", new Creature("T_Jungle_009-01", "Riesenaffe",CombatType.MELEE,5, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_009-02", new Creature("T_Jungle_009-02", "Riesenaffe2",CombatType.MELEE,5, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_010-01", new Creature("T_Jungle_010-01", "Riesenschlange",CombatType.MELEE,3, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_011-01", new Creature("T_Jungle_011-01", "Tiger",CombatType.MELEE,3, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_011-02", new Creature("T_Jungle_011-02", "Tiger2",CombatType.MELEE,3, Terrain.JUNGLE_TERRAIN));
		map.put("T_Jungle_012-01", new Creature("T_Jungle_012-01", "Watussi",CombatType.MELEE,2, Terrain.JUNGLE_TERRAIN));
		map.put("T_Mountains_034-01", new Creature("T_Mountains_034-01", "Bergbewohner",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_034-02", new Creature("T_Mountains_034-02", "Bergbewohner2",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_035-01", new Creature("T_Mountains_035-01", "Berglöwe",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_036-01", new Creature("T_Mountains_036-01", "BraunerDrache",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_037-01", new Creature("T_Mountains_037-01", "BraunerRitter",CombatType.MELEE,4, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_038-01", new Creature("T_Mountains_038-01", "Goblin",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_038-02", new Creature("T_Mountains_038-02", "Goblin2",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_038-03", new Creature("T_Mountains_038-03", "Goblin3",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_038-04", new Creature("T_Mountains_038-04", "Goblin4",CombatType.MELEE,1, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_039-01", new Creature("T_Mountains_039-01", "Großadler",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_040-01", new Creature("T_Mountains_040-01", "Großfalke",CombatType.MELEE,1,Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_041-01", new Creature("T_Mountains_041-01", "KleinerRockvogel",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_042-01", new Creature("T_Mountains_042-01", "Oger",CombatType.MELEE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_043-01", new Creature("T_Mountains_043-01", "Riese",CombatType.RANGE,4, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_044-01", new Creature("T_Mountains_044-01", "Riesenkondor",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_045-01", new Creature("T_Mountains_045-01", "Riesenrockvogel",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_046-01", new Creature("T_Mountains_046-01", "Troll",CombatType.MELEE,4, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_047-01", new Creature("T_Mountains_047-01", "Zwerg",CombatType.RANGE,2, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_048-01", new Creature("T_Mountains_048-01", "Zwerg2",CombatType.RANGE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_049-01", new Creature("T_Mountains_049-01", "Zwerg3",CombatType.MELEE,3, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Mountains_050-01", new Creature("T_Mountains_050-01", "Zyklop",CombatType.MELEE,5, Terrain.MOUNTAIN_TERRAIN));
		map.put("T_Plains_013-01", new Creature("T_Plains_013-01", "Adler",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_014-01", new Creature("T_Plains_014-01", "Bauer",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_014-02", new Creature("T_Plains_014-02", "Bauer2",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_014-03", new Creature("T_Plains_014-03", "Bauer3",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_014-04", new Creature("T_Plains_014-04", "Bauer4",CombatType.MELEE,1, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_015-01", new Creature("T_Plains_015-01", "Bösewicht",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_016-01", new Creature("T_Plains_016-01", "Büffelherde",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_017-01", new Creature("T_Plains_017-01", "Büffelherde2",CombatType.MELEE,4, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_018-01", new Creature("T_Plains_018-01", "Flugbüffel",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_019-01", new Creature("T_Plains_019-01", "Flugsaurier",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_020-01", new Creature("T_Plains_020-01", "Großfalke",CombatType.MELEE,2,Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_021-01", new Creature("T_Plains_021-01", "Großjäger",CombatType.RANGE,4, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_022-01", new Creature("T_Plains_022-01", "Jäger",CombatType.RANGE,1, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_023-01", new Creature("T_Plains_023-01", "Libelle",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_024-01", new Creature("T_Plains_024-01", "Pegasus",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_025-01", new Creature("T_Plains_025-01", "Prachtlöwe",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_026-01", new Creature("T_Plains_026-01", "Riesenkäfer",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_027-01", new Creature("T_Plains_027-01", "Stammeskrieger",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_027-02", new Creature("T_Plains_027-02", "Stammeskrieger2",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_028-01", new Creature("T_Plains_028-01", "Stammeskrieger3",CombatType.RANGE,1, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_029-01", new Creature("T_Plains_029-01", "WeißerRitter",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_030-01", new Creature("T_Plains_030-01", "Wolfsmeute",CombatType.MELEE,3, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_031-01", new Creature("T_Plains_031-01", "Zentaur",CombatType.MELEE,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_032-01", new Creature("T_Plains_032-01", "Zigeuner",CombatType.MAGIC,1, Terrain.PLAINS_TERRAIN));
		map.put("T_Plains_033-01", new Creature("T_Plains_033-01", "Zigeuner2",CombatType.MAGIC,2, Terrain.PLAINS_TERRAIN));
		map.put("T_Swamp_065-01", new Creature("T_Swamp_065-01", "Basilisk",CombatType.MAGIC,3, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_066-01", new Creature("T_Swamp_066-01", "Ding",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_067-01", new Creature("T_Swamp_067-01", "Flugpiranha",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_068-01", new Creature("T_Swamp_068-01", "Geist",CombatType.MAGIC,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_069-01", new Creature("T_Swamp_069-01", "Gespenst",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_069-02", new Creature("T_Swamp_069-02", "Gespenst2",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_069-03", new Creature("T_Swamp_069-03", "Gespenst3",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_069-04", new Creature("T_Swamp_069-04", "Gespenst4",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_070-01", new Creature("T_Swamp_070-01", "Giftfrosch",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_071-01", new Creature("T_Swamp_071-01", "Irrlicht",CombatType.MAGIC,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_072-01", new Creature("T_Swamp_072-01", "Kobold",CombatType.MAGIC,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_073-01", new Creature("T_Swamp_073-01", "Krokodile",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_074-01", new Creature("T_Swamp_074-01", "Piraten",CombatType.MELEE,2,Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_075-01", new Creature("T_Swamp_075-01", "Riesenblutegel",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_076-01", new Creature("T_Swamp_076-01", "Riesenechse",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_076-02", new Creature("T_Swamp_076-02", "Riesenechse2",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_077-01", new Creature("T_Swamp_077-01", "Riesenmoskito",CombatType.MELEE,2, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_078-01", new Creature("T_Swamp_078-01", "Riesenschlange",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_079-01", new Creature("T_Swamp_079-01", "Schleimbestie",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_080-01", new Creature("T_Swamp_080-01", "SchwartzeRitter",CombatType.MELEE,3, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_081-01", new Creature("T_Swamp_081-01", "Schwarzmagier",CombatType.MAGIC,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_082-01", new Creature("T_Swamp_082-01", "Sumpfgas",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_083-01", new Creature("T_Swamp_083-01", "Sumpfratte",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_084-01", new Creature("T_Swamp_084-01", "Vampirfledermaus",CombatType.MELEE,4, Terrain.SWAMP_TERRAIN));
		map.put("T_Swamp_085-01", new Creature("T_Swamp_085-01", "Wasserschlange",CombatType.MELEE,1, Terrain.SWAMP_TERRAIN));
		return map;
	}

	public static Map<String, Treasure> getTreasureMap() {
		HashMap<String, Treasure> map = new HashMap<String, Treasure>();
		map.put("treasure_01-01", new Treasure("treasure_01-01", "treasureChest",20));
		map.put("treasure_02-1", new Treasure("treasure_02-1", "ruby",10));
		map.put("treasure_03-01", new Treasure("treasure_03-01", "sapphire",5));
		map.put("treasure_04-01", new Treasure("treasure_04-01", "sliverMine",2));
		map.put("treasure_04-02", new Treasure("treasure_04-02", "sliverMine2",2));
		map.put("treasure_06-01", new Treasure("treasure_06-01", "pearl",5));
		map.put("treasure_07-01", new Treasure("treasure_07-01", "emerald",10)); 
		map.put("treasure_08-01", new Treasure("treasure_08-01", "diamond",5)); 
		return map;
	}
	
	public static Map<String, MagicItem> getMagicItemMap() {
		HashMap<String, MagicItem> map = new HashMap<String, MagicItem>();
		map.put("magic_01", new MagicItem("magic_01", "Abwehrstaub"));
		map.put("magic_02", new MagicItem("magic_02", "Ballon"));
		map.put("magic_03", new MagicItem("magic_03", "Blasebalg"));
		map.put("magic_04", new MagicItem("magic_04", "Elixier"));
		map.put("magic_05", new MagicItem("magic_05", "Feuerwand"));
		map.put("magic_06", new MagicItem("magic_06", "Glucksbringer"));
		map.put("magic_07", new MagicItem("magic_07", "Golem"));
		map.put("magic_08", new MagicItem("magic_08", "MagieBannen"));
		map.put("magic_09", new MagicItem("magic_09", "Talisman"));
		map.put("magic_10", new MagicItem("magic_10", "Zauberbogen"));
		map.put("magic_11", new MagicItem("magic_11", "Zauberschwert"));
		return map;
	}
	
	public static Map<String, CityVill> getCityVillMap() {
		HashMap<String, CityVill> map = new HashMap<String, CityVill>();
		map.put("city_01", new CityVill("city_01", "city",2,2));
		map.put("village_01", new CityVill("village_01", "village",1,1));
		
		return map;
	}

	public static Map<String, NonCityVill> getNonCityVillMap() {
		HashMap<String, NonCityVill> map = new HashMap<String, NonCityVill>();
		map.put("specialIncomeCounter_01", new NonCityVill("specialIncomeCounter_01", "timberland",1,Terrain.FOREST_TERRAIN));//must have forest to get gold logic later added
		map.put("specialIncomeCounter_02", new NonCityVill("specialIncomeCounter_02", "oilField",3,Terrain.FROZEN_TERRAIN)); //only if you have frozen waste to get gold
		map.put("specialIncomeCounter_03", new NonCityVill("specialIncomeCounter_03", "peatBog",1,Terrain.SWAMP_TERRAIN)); //swamp only
		map.put("specialIncomeCounter_04", new NonCityVill("specialIncomeCounter_04", "farmlands",1,Terrain.PLAINS_TERRAIN)); //plains only
		map.put("specialIncomeCounter_05", new NonCityVill("specialIncomeCounter_05", "goldMine",3,Terrain.MOUNTAIN_TERRAIN)); //mountain only
		map.put("specialIncomeCounter_06", new NonCityVill("specialIncomeCounter_06", "elephantGraveyard",3,Terrain.JUNGLE_TERRAIN)); //jungle  only
		map.put("specialIncomeCounter_07", new NonCityVill("specialIncomeCounter_07", "diamondField",1,Terrain.DESERT_TERRAIN)); //Desert Only
		map.put("specialIncomeCounter_08", new NonCityVill("specialIncomeCounter_08", "copperMine",1,Terrain.MOUNTAIN_TERRAIN)); //Mountain Only
		return map;
	}	
	
	public static Map<String, RandomEvent> getRandomEventMap() {
		HashMap<String, RandomEvent> map = new HashMap<String, RandomEvent>();
		map.put("RandomEvent_01", new RandomEvent("RandomEvent_01", "bidJuJu"));
		map.put("RandomEvent_02", new RandomEvent("RandomEvent_02", "darkPlague"));
		map.put("RandomEvent_03", new RandomEvent("RandomEvent_03", "defection"));
		map.put("RandomEvent_04", new RandomEvent("RandomEvent_04", "goodHarvest"));
		map.put("RandomEvent_05", new RandomEvent("RandomEvent_05", "motherLode"));
		map.put("RandomEvent_06", new RandomEvent("RandomEvent_06", "teeniepox"));
		map.put("RandomEvent_07", new RandomEvent("RandomEvent_07", "terrainDisaster"));
		map.put("RandomEvent_08", new RandomEvent("RandomEvent_08", "vandals"));
		map.put("RandomEvent_09", new RandomEvent("RandomEvent_09", "weatherControl"));
		map.put("RandomEvent_10", new RandomEvent("RandomEvent_10", "willingWorkers"));
		return map;
	}
	
	public static Map<String, SpecialCharacter> getSpecialCharacterMap() {
		HashMap<String, SpecialCharacter> map = new HashMap<String, SpecialCharacter>();
		map.put("specialcharacter_01", new SpecialCharacter("specialcharacter_01", "Marksman",SpecialAbility.markmanCounter));
		map.put("specialcharacter_02", new SpecialCharacter("specialcharacter_02", "SirLanceALot",SpecialAbility.markmanCounter));
		map.put("specialcharacter_03", new SpecialCharacter("specialcharacter_03", "ArchMage",SpecialAbility.markmanCounter));
		map.put("specialcharacter_04", new SpecialCharacter("specialcharacter_04", "DwarfKing",SpecialAbility.markmanCounter));
		map.put("specialcharacter_05", new SpecialCharacter("specialcharacter_05", "AssassinPrimus",SpecialAbility.eliminatecounterNocombat));
		map.put("specialcharacter_06", new SpecialCharacter("specialcharacter_06", "BaronMunchausen",SpecialAbility.markmanCounter));
		map.put("specialcharacter_07", new SpecialCharacter("specialcharacter_07", "GrandDuke",SpecialAbility.markmanCounter));
		map.put("specialcharacter_08", new SpecialCharacter("specialcharacter_08", "ElfLord",SpecialAbility.markmanCounter));
		map.put("specialcharacter_09", new SpecialCharacter("specialcharacter_09", "MasterThief",SpecialAbility.masterCounterTheft));
		map.put("specialcharacter_10", new SpecialCharacter("specialcharacter_10", "SwordMaster",SpecialAbility.swordElimnator));
		map.put("specialcharacter_11", new SpecialCharacter("specialcharacter_11", "ArchCleric",SpecialAbility.markmanCounter));
		return map;
	}
	
	public static Map<String, HexTile> getHexTileMap() {
		HashMap<String, HexTile> map = new HashMap<String, HexTile>();
		map.put("desert-tile-01", new HexTile("desert-tile-01", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-02", new HexTile("desert-tile-02", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-03", new HexTile("desert-tile-03", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-04", new HexTile("desert-tile-04", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-05", new HexTile("desert-tile-05", "desertTile",Terrain.DESERT_TERRAIN));
		map.put("desert-tile-06", new HexTile("desert-tile-06", "desertTile",Terrain.DESERT_TERRAIN));
		
		map.put("forest-tile-01", new HexTile("forest-tile-01", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-02", new HexTile("forest-tile-02", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-03", new HexTile("forest-tile-03", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-04", new HexTile("forest-tile-04", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-05", new HexTile("forest-tile-05", "forestTile",Terrain.FOREST_TERRAIN));
		map.put("forest-tile-06", new HexTile("forest-tile-06", "forestTile",Terrain.FOREST_TERRAIN));

		map.put("frozen-tile-01", new HexTile("frozen-tile-01", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-02", new HexTile("frozen-tile-02", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-03", new HexTile("frozen-tile-03", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-04", new HexTile("frozen-tile-04", "frozenTile",Terrain.FROZEN_TERRAIN));
		map.put("frozen-tile-05", new HexTile("frozen-tile-05", "frozenTile",Terrain.FROZEN_TERRAIN));
		
		map.put("jungle-tile-01", new HexTile("jungle-tile-01", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-02", new HexTile("jungle-tile-02", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-03", new HexTile("jungle-tile-03", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-04", new HexTile("jungle-tile-04", "jungleTile",Terrain.JUNGLE_TERRAIN));
		map.put("jungle-tile-05", new HexTile("jungle-tile-05", "jungleTile",Terrain.JUNGLE_TERRAIN));

		map.put("mountain-tile-01", new HexTile("mountain-tile-01", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-02", new HexTile("mountain-tile-02", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-03", new HexTile("mountain-tile-03", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-04", new HexTile("mountain-tile-04", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-05", new HexTile("mountain-tile-05", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		map.put("mountain-tile-06", new HexTile("mountain-tile-06", "mountainTile",Terrain.MOUNTAIN_TERRAIN));
		
		map.put("plains-tile-01", new HexTile("plains-tile-01", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-02", new HexTile("plains-tile-02", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-03", new HexTile("plains-tile-03", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-04", new HexTile("plains-tile-04", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-05", new HexTile("plains-tile-05", "plainsTile",Terrain.PLAINS_TERRAIN));
		map.put("plains-tile-06", new HexTile("plains-tile-06", "plainsTile",Terrain.PLAINS_TERRAIN));
		
		map.put("sea-tile-01", new HexTile("sea-tile-01", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-02", new HexTile("sea-tile-02", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-03", new HexTile("sea-tile-03", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-04", new HexTile("sea-tile-04", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-05", new HexTile("sea-tile-05", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-06", new HexTile("sea-tile-06", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-07", new HexTile("sea-tile-07", "seaTile",Terrain.SEA_TERRAIN));
		map.put("sea-tile-08", new HexTile("sea-tile-08", "seaTile",Terrain.SEA_TERRAIN));
		
		map.put("swamp-tile-01", new HexTile("swamp-tile-01", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-02", new HexTile("swamp-tile-02", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-03", new HexTile("swamp-tile-03", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-04", new HexTile("swamp-tile-04", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-05", new HexTile("swamp-tile-05", "swampTile",Terrain.SWAMP_TERRAIN));
		map.put("swamp-tile-06", new HexTile("swamp-tile-06", "swampTile",Terrain.SWAMP_TERRAIN));
		
		return map;
	}
	
}
