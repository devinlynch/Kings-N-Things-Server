package com.kings.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.kings.http.GameMessage;
import com.kings.model.GameState;
import com.kings.model.Player;
import com.kings.model.phases.exceptions.NotYourTurnException;
import com.kings.model.phases.recruitcharacters.RecruitCharactersPhaseRound;

public class RecruitCharactersPhase extends Phase {
	private int currentRound;
	private List<RecruitCharactersPhaseRound> rounds;

	public RecruitCharactersPhase(GameState gameState,
			List<Player> playersInOrderOfTurn) {
		super(gameState, playersInOrderOfTurn);
		setPhaseId("recruitCharacters");
		
		rounds = new ArrayList<RecruitCharactersPhaseRound>();
		currentRound = 0;
	}

	@Override
	public void handleStart() {
		GameMessage message = newGameMessageForAllPlayers("didStartRecruitCharactersPhase");
		getGameState().queueUpGameMessageToSendToAllPlayers(message);
		
		for(Player p: getPlayersInOrderOfTurn()) {
			RecruitCharactersPhaseRound round = new RecruitCharactersPhaseRound(p, this);
			rounds.add(round);
		}
		
		handleStartNextRoundIfNeeded();
	}
	
	public void handleStartNextRoundIfNeeded() {
		if(currentRound > rounds.size()-1) {
			end();
			return;
		}
		
		RecruitCharactersPhaseRound round = rounds.get(currentRound);
		
		if(!round.isStarted()) {
			round.start();
		}
		
		if(round.isRoundOver()){
			currentRound++;
			if(currentRound > rounds.size()-1) {
				end();
				return;
			} else{
				rounds.get(currentRound).start();
				return;
			}
		}
	}
	
	
	public void makeRollForPlayer(String playerId, String recruitingCharacterId, int numRequestedPreRollChanges) throws NotYourTurnException {
		if(isOver()){
			return;
		}
		
		if( ! getActualCurrentRound().getPlayer().getPlayerId().equals(playerId) ) {
			throw new NotYourTurnException();
		}
		
		RecruitCharactersPhaseRound round = getRoundForPlayer(playerId);
		round.makeRollForPlayer(recruitingCharacterId, numRequestedPreRollChanges);
	}
	
	public void postRoll(String playerId, int numPostRollChanges) throws NotYourTurnException {
		if(isOver()){
			return;
		}
		
		if( ! getActualCurrentRound().getPlayer().getPlayerId().equals(playerId) ) {
			throw new NotYourTurnException();
		}
		
		RecruitCharactersPhaseRound round = getRoundForPlayer(playerId);
		round.postRoll(numPostRollChanges);
	}
	
	public RecruitCharactersPhaseRound getActualCurrentRound() {
		return rounds.get(currentRound);
	}
	
	public RecruitCharactersPhaseRound getRoundForPlayer(String playerId) {
		for(RecruitCharactersPhaseRound round: rounds) {
			if(playerId.equals(round.getPlayer().getPlayerId())) {
				return round;
			}
		}
		return null;
	}
	
	@Override
	public void setupNextPhase() {
		setNextPhase(new RecruitThingsPhase(getGameState(), getPlayersInOrderOfTurn()));
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
