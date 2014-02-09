package com.kings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class DemoGameState extends GameState {
	
	public DemoGameState() {
		super();
	}
	
	public static GameState createGameStateFromGame(Game game) throws Exception {
		DemoGameState gameState = new DemoGameState();
		initializeNewGameState(gameState, game);
 		return gameState;
	}
	
	@Override 
	public HashMap<Player, List<String>> getPossibleThingsToRecruitForPlayers() {
		List<Thing> playingCupPieces = new ArrayList<Thing>(getPlayingCup().getThings());
		// Max Free Recruits: 2
		// Max Trades: 5
		// Max Buys: 5
		
		int totalFreeRecruitsPerPlayer = 2;
		int maxNumberOfTrades = 5;
		int maxNumberOfBuys = 5;
		int costOfBuyingPiece=5;
		
		List<String> play1HardCodedThings = new ArrayList<String>();
		play1HardCodedThings.add(getGamePiece("T_Mountains_050-01").getId());
		play1HardCodedThings.add(getGamePiece("T_Mountains_034-01").getId());
		play1HardCodedThings.add(getGamePiece("T_Mountains_038-01").getId());
		
		if(getPhaseTurn() == 1) {
			playingCupPieces.removeAll(play1HardCodedThings);
		}
		
		HashMap<Player, List<String>> map = new HashMap<Player, List<String>>();
		for(Player p: getPlayers()) {
			int numberOfThingsInPlayersRacks = p.getAllThingsInRacks().size();
			
			int thisPlayersMaxNumebrOfTrades = maxNumberOfTrades;
			if(numberOfThingsInPlayersRacks < 5) {
				thisPlayersMaxNumebrOfTrades = numberOfThingsInPlayersRacks;
			}
			
			int thisPlayersMaxNumberOfBuys = maxNumberOfBuys;
			int playersGold = p.getGold();
			int totalPossibleBuys = playersGold/costOfBuyingPiece;
			if(totalPossibleBuys < maxNumberOfBuys) {
				thisPlayersMaxNumberOfBuys = totalPossibleBuys;
			}
			
			int thisPlayersTotalNumberOfRecruitmentThings =  thisPlayersMaxNumberOfBuys + thisPlayersMaxNumebrOfTrades + totalFreeRecruitsPerPlayer;
			
			if(playingCupPieces.size() < thisPlayersTotalNumberOfRecruitmentThings) {
				thisPlayersTotalNumberOfRecruitmentThings = playingCupPieces.size();
			}
			
			List<String> thisPlayersThingsToRecruit = new ArrayList<String>();
			if(p.getPlayerId().equals("player1") && getPhaseTurn() == 1) {
				thisPlayersThingsToRecruit.addAll(play1HardCodedThings);
			} else{
				for(int i = 0; i < thisPlayersTotalNumberOfRecruitmentThings; i++) {
					Thing thing = playingCupPieces.get(new Random().nextInt(playingCupPieces.size()));
					playingCupPieces.remove(thing);
					thisPlayersThingsToRecruit.add(thing.getId());
				}
			}
			
			map.put(p, thisPlayersThingsToRecruit);
		}
		
		return map;
	}
	
}
