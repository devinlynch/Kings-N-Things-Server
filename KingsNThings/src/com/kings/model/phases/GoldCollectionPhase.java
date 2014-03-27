package com.kings.model.phases;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.Player;

public class GoldCollectionPhase extends Phase {
	private Set<String> playersReadyForNextPhase;
	
	public GoldCollectionPhase(GameState gameState, List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("gold");
		playersReadyForNextPhase = new HashSet<String>();
	}

	@Override
	public void handleStart() {
		GameMessage gameMessage = new GameMessage("goldCollection");
		
		for(Player p : getPlayersInOrderOfTurn()) {
			//Calculate income, add to players gold, and add to message
			int income = p.getIncome();
			getGameState().getBank().payoutGoldToPlayer(income, p);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("income", income);
			map.put("totalGold", p.getGold());
			gameMessage.addToData(p.getPlayerId(), map);
			gameMessage.addPlayerToSendTo(p);
		}
		
		getGameState().queueUpGameMessageToSendToAllPlayers(gameMessage);
	}
	
	public synchronized void playerIsReadyForNextPhase(String playerId) {
		if(isOver())
			return;
		playersReadyForNextPhase.add(playerId);
		if(playersReadyForNextPhase.size() >= getPlayersInOrderOfTurn().size()) {
			end();
		}
	}

	@Override
	public void setupNextPhase() {
		setNextPhase(new RecruitCharactersPhase(getGameState(), getPlayersInOrderOfTurn()));
	}

	@Override
	public GameMessage getPhaseOverMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameMessage getPhaseStartedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
