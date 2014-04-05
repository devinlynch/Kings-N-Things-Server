package com.kings.test.phases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.kings.http.HttpResponseMessage;
import com.kings.model.Game;
import com.kings.model.GamePiece;
import com.kings.model.GameState;
import com.kings.model.HexLocation;
import com.kings.model.Player;
import com.kings.model.SentMessage;
import com.kings.model.SpecialCharacter;
import com.kings.model.Stack;
import com.kings.model.User;
import com.kings.model.phases.CombatPhase;
import com.kings.model.phases.GoldCollectionPhase;
import com.kings.model.phases.MovementPhase;
import com.kings.model.phases.PlacementPhase;
import com.kings.model.phases.RecruitCharactersPhase;
import com.kings.model.phases.RecruitThingsPhase;
import com.kings.model.phases.SetupPhase;
import com.kings.model.phases.battle.CombatBattle;
import com.kings.model.phases.battle.CombatBattleRound;
import com.kings.model.phases.battle.MagicCombatBattleStep;
import com.kings.model.phases.battle.MeleeCombatBattleStep;
import com.kings.model.phases.battle.RangedCombatBattleStep;
import com.kings.model.phases.exceptions.NotYourTurnException;
import com.kings.util.Utils;

public class RunThroughPhasesTest {
	private Game game;
	private User u1;
	private User u2;
	private User u3;
	private User u4;
	
	public GameState getNewGameState() throws Exception{
		game = new Game();
		game.setDemo(true);
		game.setActive(true);
		game.setGameId("1");
		game.setStartedDate(new Date());
		Set<User> users = new HashSet<User>();
		u1=new User("1");
		u1.setPort(3004);
		u1.setHostName("localhost");
		u1.setUsername("devin");
		u2=new User("2");
		u2.setUsername("john");
		u3=new User("3");
		u3.setUsername("devin");
		u4=new User("4");
		u4.setUsername("richard");
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		game.setUsers(users);
		game.startAsTest();
		
		return game.getGameState();
	}
	
	@Test
	public void testRunThroughGame() throws Exception{
		GameState gs = getNewGameState();
		
		/** 
		 * Setup Phase
		 */
		
		assertEquals("setup", gs.getCurrentPhase().getPhaseId());
		
		// Go through setup phase
		SetupPhase sPhase = (SetupPhase)gs.getCurrentPhase();
		sPhase.playerIsReadyForPlacement("1");
		sPhase.playerIsReadyForPlacement("3");
		sPhase.playerIsReadyForPlacement("4");
		
		assertEquals("setup", gs.getCurrentPhase().getPhaseId());
		sPhase.playerIsReadyForPlacement("2");
		assertEquals("placement", gs.getCurrentPhase().getPhaseId());
		
		/**
		 * Placement Phase
		 */
		
		// Now do placement phase
		PlacementPhase pPhase = (PlacementPhase) gs.getCurrentPhase();
		pPhase.didPlaceControlMarkers("player1", "hexLocation_1", "hexLocation_2", "hexLocation_3");
		pPhase.didPlaceControlMarkers("player2", "hexLocation_4", "hexLocation_5", "hexLocation_6");
		pPhase.didPlaceControlMarkers("player3", "hexLocation_7", "hexLocation_8", "hexLocation_9");
		pPhase.didPlaceControlMarkers("player4", "hexLocation_10", "hexLocation_11", "hexLocation_12");
		
		assertEquals("player1", gs.getHexLocationsById("hexLocation_1").getOwner().getPlayerId());
		assertEquals("player2", gs.getHexLocationsById("hexLocation_5").getOwner().getPlayerId());
		assertEquals("player3", gs.getHexLocationsById("hexLocation_9").getOwner().getPlayerId());
		assertEquals("player4", gs.getHexLocationsById("hexLocation_12").getOwner().getPlayerId());
		
		Player p1 = gs.getPlayerByPlayerId("player1");
		GamePiece fort1 = (GamePiece) p1.getFortPieces().toArray()[0];
		assertEquals("Tower", fort1.getName());
		pPhase.didPlaceFort("player1", fort1.getId(), "hexLocation_1");
		assertEquals("hexLocation_1", fort1.getLocation().getId());
		
		// Try placing fort out of order
		boolean didGetNotTurnException = false;
		try{
			pPhase.didPlaceFort("player3", "", "");
		} catch(NotYourTurnException e) {
			didGetNotTurnException = true;
		}
		assertTrue(didGetNotTurnException);
		
		Player p2 = gs.getPlayerByPlayerId("player2");
		GamePiece fort2 = (GamePiece) p2.getFortPieces().toArray()[0];
		assertEquals("Tower", fort2.getName());
		pPhase.didPlaceFort("player2", fort2.getId(), "hexLocation_4");
		assertEquals("hexLocation_4", fort2.getLocation().getId());
		
		Player p3 = gs.getPlayerByPlayerId("player3");
		GamePiece fort3 = (GamePiece) p3.getFortPieces().toArray()[0];
		assertEquals("Tower", fort3.getName());
		pPhase.didPlaceFort("player3", fort3.getId(), "hexLocation_7");
		assertEquals("hexLocation_7", fort3.getLocation().getId());
		
		Player p4 = gs.getPlayerByPlayerId("player4");
		GamePiece fort4 = (GamePiece) p4.getFortPieces().toArray()[0];
		assertEquals("Tower", fort4.getName());
		pPhase.didPlaceFort("player4", fort4.getId(), "hexLocation_10");
		assertEquals("hexLocation_10", fort4.getLocation().getId());
		
		
		/**
		 * Movement Phase
		 */
		
		MovementPhase mPhase1 = (MovementPhase) gs.getCurrentPhase();
		mPhase1.playerIsDoneMakingMoves("player1");
		mPhase1.playerIsDoneMakingMoves("player2");
		mPhase1.playerIsDoneMakingMoves("player3");
		mPhase1.playerIsDoneMakingMoves("player4");
		
		/**
		 * Gold Collection Phase
		 */
		
		// Gold phase should be started and handled automatically, players should now have gold assigned
		assertEquals("gold", gs.getCurrentPhase().getPhaseId());
		
		assertEquals(new Integer(14), p1.getGold());
		assertEquals(new Integer(14), p2.getGold());
		assertEquals(new Integer(14), p3.getGold());
		assertEquals(new Integer(14), p4.getGold());
		
		GoldCollectionPhase cPhase = (GoldCollectionPhase) gs.getCurrentPhase();
		
		cPhase.playerIsReadyForNextPhase("player1");
		cPhase.playerIsReadyForNextPhase("player2");
		cPhase.playerIsReadyForNextPhase("player3");
		assertEquals("gold", gs.getCurrentPhase().getPhaseId());
		cPhase.playerIsReadyForNextPhase("player4");
		
		
		/**
		 * Special Character Recruitment
		 */
		assertEquals("recruitCharacters", gs.getCurrentPhase().getPhaseId());
		RecruitCharactersPhase rcPhase = (RecruitCharactersPhase)gs.getCurrentPhase(); 
		
		
		SpecialCharacter p1RCSPC = (SpecialCharacter)gs.getSideLocation().getGamePieceById("specialcharacter_01");
		SpecialCharacter p2RCSPC = (SpecialCharacter)gs.getSideLocation().getGamePieceById("specialcharacter_02");
		SpecialCharacter p3RCSPC = (SpecialCharacter)gs.getSideLocation().getGamePieceById("specialcharacter_03");
		SpecialCharacter p4RCSPC = (SpecialCharacter)gs.getSideLocation().getGamePieceById("specialcharacter_04");
		
		// Player 1 Special Characters - Did not recruit, no pre or post rolls
		assertEquals(rcPhase.getActualCurrentRound().getPlayer().getPlayerId(), p1.getPlayerId());
		gs.addDiceRollForTest(7);
		rcPhase.makeRollForPlayer(p1.getPlayerId(), p1RCSPC.getId(), 0);
		rcPhase.postRoll(p1.getPlayerId(), 0);
		assertFalse(rcPhase.getRoundForPlayer(p1.getPlayerId()).isDidRecruit());
		assertTrue(rcPhase.getRoundForPlayer(p1.getPlayerId()).isRoundOver());
		
		// Player 2 Special Characters - Recruited on first roll, no pre or post rolls
		assertEquals(rcPhase.getActualCurrentRound().getPlayer().getPlayerId(), p2.getPlayerId());
		gs.addDiceRollForTest(10);
		rcPhase.makeRollForPlayer(p2.getPlayerId(), p2RCSPC.getId(), 0);
		assertTrue(rcPhase.getRoundForPlayer(p2.getPlayerId()).isDidRecruit());
		assertTrue(rcPhase.getRoundForPlayer(p2.getPlayerId()).isRoundOver());
		
		// Player 3 Special Characters - Paid for 1 pre roll and did recruit
		assertEquals(rcPhase.getActualCurrentRound().getPlayer().getPlayerId(), p3.getPlayerId());
		gs.addDiceRollForTest(11);
		rcPhase.makeRollForPlayer(p3.getPlayerId(), p3RCSPC.getId(), 1);
		assertTrue(rcPhase.getRoundForPlayer(p3.getPlayerId()).isDidRecruit());
		assertTrue(rcPhase.getRoundForPlayer(p3.getPlayerId()).isRoundOver());
		
		// Player 4 Special Characters - Did not recruit on first roll, paid for 1 post roll
		assertEquals(rcPhase.getActualCurrentRound().getPlayer().getPlayerId(), p4.getPlayerId());
		gs.addDiceRollForTest(9);
		rcPhase.makeRollForPlayer(p4.getPlayerId(), p4RCSPC.getId(), 0);
		rcPhase.postRoll(p4.getPlayerId(), 1);
		assertTrue(rcPhase.getRoundForPlayer(p4.getPlayerId()).isDidRecruit());
		assertTrue(rcPhase.getRoundForPlayer(p4.getPlayerId()).isRoundOver());
		assertEquals(p4, p4RCSPC.getOwner());
		
		/**
		 * Recruit Things
		 */
		
		assertEquals("recruitThings", gs.getCurrentPhase().getPhaseId());
		RecruitThingsPhase rtPhase = (RecruitThingsPhase)gs.getCurrentPhase(); 
		rtPhase.didRecruitAndPlaceThing("player1", "T_Mountains_050-01", "player1_rack1", true);
		rtPhase.didRecruitAndPlaceThing("player1", "T_Mountains_034-01", "player1_rack2", false);
		rtPhase.didRecruitAndPlaceThing("player1", "T_Mountains_038-01", "player1_rack2", false);
		//assertEquals(1, p1.getRack1().getGamePieces().size());
		//assertEquals(2, p1.getRack2().getGamePieces().size());
		assertEquals(9, (int)p1.getGold());
		
		rtPhase.playerIsReadyForNextPhase("player1");
		rtPhase.playerIsReadyForNextPhase("player3");
		rtPhase.playerIsReadyForNextPhase("player4");
		rtPhase.playerIsReadyForNextPhase("player2");
		assertEquals("movement", gs.getCurrentPhase().getPhaseId());
		
		/**
		 * Movement
		 */
		
		MovementPhase mPhase = (MovementPhase)gs.getCurrentPhase();
		HexLocation p1HexForMov1 = p1.getOwnedLocationsAsList().get(0);
		HexLocation p1HexForMov2 = p1.getOwnedLocationsAsList().get(1);
		mPhase.didMoveGamePiece(p1.getPlayerId(), p1HexForMov1.getId(), "T_Mountains_050-01");
		assertEquals(p1HexForMov1.getId(), p1.getGamePieceById("T_Mountains_050-01").getLocation().getId());
		mPhase.didMoveGamePiece(p1.getPlayerId(), p1.getRack1().getId(), "T_Mountains_050-01");
		assertNotEquals(p1HexForMov1.getId(), p1.getGamePieceById("T_Mountains_050-01").getLocation().getId());
		assertEquals(p1.getRack1().getId(), p1.getGamePieceById("T_Mountains_050-01").getLocation().getId());
		
		List<String> p1MoveStackCreateList = new ArrayList<String>();
		p1MoveStackCreateList.add("T_Mountains_038-01");
		mPhase.didCreateStack(p1.getPlayerId(),  p1HexForMov1.getId(), p1MoveStackCreateList);
		assertEquals("Stack", p1.getGamePieceById("T_Mountains_038-01").getLocation().getName());
		List<Stack> p1StacksHex1 = new ArrayList<Stack>(p1HexForMov1.getStacks());
		assertEquals(1, p1StacksHex1.get(0).getGamePieces().size());
		List<String> p1MoveStackAddList = new ArrayList<String>();
		p1MoveStackAddList.add("T_Mountains_034-01");
		mPhase.didAddPiecesToStack(p1.getPlayerId(), p1StacksHex1.get(0).getId(), p1MoveStackAddList);
		assertEquals(2, p1StacksHex1.get(0).getGamePieces().size());
		
		mPhase.didMoveStack(p1.getPlayerId(), p1HexForMov2.getId(), p1StacksHex1.get(0).getId());
		assertEquals(0, p1HexForMov1.getStacks().size());
		assertEquals(1, p1HexForMov2.getStacks().size());
		
		mPhase.playerIsDoneMakingMoves(p1.getPlayerId());
		
		//play 2 moves
		p2.assignGamePieceToPlayer(gs.getGamePiece("T_Swamp_074-01"));
		List<String> p2ListOfPiecesForMove = new ArrayList<String>();
		p2ListOfPiecesForMove.add("T_Swamp_074-01");
		mPhase.didCreateStack(p2.getPlayerId(), p1HexForMov2.getId(), p2ListOfPiecesForMove);
		assertEquals(2, p1HexForMov2.getPlayersWhoAreOnMe().size());
		
		mPhase.playerIsDoneMakingMoves(p2.getPlayerId());
		mPhase.playerIsDoneMakingMoves(p3.getPlayerId());
		mPhase.playerIsDoneMakingMoves(p4.getPlayerId());
		
		/**
		 * Combat
		 */
		
		CombatPhase cp = (CombatPhase)gs.getCurrentPhase();
		CombatBattle battle = cp.getCombatBattles().get(0);
		
		// Send chat message
		HttpResponseMessage chatMsg = game.sendChatMessage(u1, "Hi Chat!", null);
		System.out.println("Sent chat message: [" + chatMsg.toJson() + "]");
		
		
		for(SentMessage msg : gs.getSentMessages()){
			System.out.println(msg.getJson());
		}
	}
	
	@Test
	public void testCombat1() throws Exception {
		GameState gameState = getNewGameState();
		Player player1 = gameState.getPlayerByPlayerId("player1");
		Player player2 = gameState.getPlayerByPlayerId("player2");
		Player player3 = gameState.getPlayerByPlayerId("player3");
		Player player4 = gameState.getPlayerByPlayerId("player4");
		
		/*
		  
		 Stack 1:
		  	• Crocodiles - Krokodile	
			• Mountain	Men	- Bergbewohner
			• Nomads	(Bluff)	- Nomade
			• Giant	Spider	(Bluff)	- Riesenspinne
			• Killer	Racoon	- Mörderwaschbär
			• Farmers	- Bauer
			• Ice	Giant	(R5)	 - Eisriese
			• White	Dragon	(Star5)	 - Weißer Drachen
			• Mammoth	(C5)	- Mammut
			• Head	Hunter	(R2)	- Kopfjäger
		
		 Stack 2:
			• Thing	- Ding
			• Giant	Lizard	- Riesenechse
			• Swamp	Rat	- Sumpfratte
			• Unicorn	- Einhorn
			• Bears	- Bären
			• Camel	Corps	- Kamelreiter
			• Sandworm	- Sandwurm
			• Black	Knight	(C3)	- Schwartze Ritter
			• Dervish	(Star2)	 - Derwisch
			• Forester	(R2)	- Waldläufer

		 */
		
		
		// Stack 1
		Stack stack1 = new Stack("stack1");
		stack1.setOwner(player1);
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Jungle_005-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Mountains_034-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Desert_114-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Desert_115-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Forest_100-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Plains_014-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Frozen_Waste_054-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Frozen_Waste_063-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Frozen_Waste_058-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Jungle_004-01"));

		// Stack 2
		Stack stack2 = new Stack("stack2");
		stack2.setOwner(player2);
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Swamp_066-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Swamp_076-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Swamp_083-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Forest_090-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Forest_087-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Desert_113-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Desert_117-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Swamp_080-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Desert_108-01"));
		stack2.addGamePieceToLocation(gameState.getGamePiece("T_Forest_101-01"));
		
		HexLocation hexLoc3 = gameState.getHexLocationsById("hexLocation_3");
		
		// It's player 2's hex, player 1 is attacking
		hexLoc3.capture(player2);
		hexLoc3.addStack(stack1);
		hexLoc3.addStack(stack2);
		
		HexLocation hexLoc7 = gameState.getHexLocationsById("hexLocation_7");
		hexLoc7.capture(player3);
		
		HexLocation hexLoc6 = gameState.getHexLocationsById("hexLocation_6");
		GamePiece fort = gameState.getGamePiece("Fort_03-01");
		player3.assignGamePieceToPlayer(fort);
		hexLoc6.addGamePieceToLocation(fort);
		GamePiece elf = gameState.getGamePiece("T_Forest_091-01");
		hexLoc6.addGamePieceToLocation(elf);
		player3.assignGamePieceToPlayer(elf);
		GamePiece mine = gameState.getGamePiece("specialIncomeCounter_09-01");
		hexLoc6.addGamePieceToLocation(mine);
		player3.assignGamePieceToPlayer(mine);
		GamePiece wasserchlange = gameState.getGamePiece("T_Swamp_085-01");
		hexLoc6.addGamePieceToLocation(wasserchlange);
		player3.assignGamePieceToPlayer(wasserchlange);
		
		GamePiece p4gp1 = gameState.getGamePiece("T_Mountains_047-01");
		GamePiece p4gp2 = gameState.getGamePiece("T_Mountains_048-01");
		GamePiece p4gp3	= gameState.getGamePiece("T_Mountains_049-01");
		GamePiece p4gp4 = gameState.getGamePiece("T_Swamp_067-01");
		player4.assignGamePieceToPlayer(p4gp1);
		player4.assignGamePieceToPlayer(p4gp2);
		player4.assignGamePieceToPlayer(p4gp3);
		player4.assignGamePieceToPlayer(p4gp4);
		hexLoc6.addGamePieceToLocation(p4gp1);
		hexLoc6.addGamePieceToLocation(p4gp2);
		hexLoc6.addGamePieceToLocation(p4gp3);
		hexLoc6.addGamePieceToLocation(p4gp4);
		
		hexLoc6.capture(player3);
		
		
		gameState.addDiceRollForTest(1);
		gameState.setCurrentPhase(new CombatPhase(gameState, gameState.getCurrentPhase().getPlayersInOrderOfTurn()));
		assertEquals("combat", gameState.getCurrentPhase().getPhaseId());
		CombatPhase cp = (CombatPhase)gameState.getCurrentPhase();
		cp.start();
		
		
		/**
		 * First Battle
		 */
		assertEquals(10, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player1).size());
		assertEquals(10, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player2).size());
		
		CombatBattle battle = cp.getCombatBattles().get(0);
		CombatBattleRound round = battle.getRound();
		
		assertEquals(player1, battle.getAttacker());
		assertEquals(player2, battle.getDefender());
		assertTrue(round.isStarted());
		assertTrue(round.getSteps().get(round.getCurrentStep()) instanceof MagicCombatBattleStep);
		
		/**
		 * Magic Step
		 */
		MagicCombatBattleStep magicStep = (MagicCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(magicStep.isStarted());

		// They both have 1 magic counter
		assertEquals(1, magicStep.getAttackerHitCount());
		assertEquals(1, magicStep.getDefenderHitCount());
		
		Set<String> player1MagicHits = new HashSet<String>();
		player1MagicHits.add("T_Jungle_005-01");
		magicStep.playerLockedInRollAndDamage(player1, player1MagicHits);
		
		Set<String> player2MagicHits = new HashSet<String>();
		player2MagicHits.add("T_Swamp_066-01");
		magicStep.playerLockedInRollAndDamage(player2, player2MagicHits);
		
		GamePiece player1PieceTakenDamage = gameState.getGamePiece("T_Jungle_005-01");
		GamePiece player2PieceTakenDamage = gameState.getGamePiece("T_Swamp_066-01");

		assertEquals("playingCup", player1PieceTakenDamage.getLocation().getId());
		assertEquals(null, player1.getGamePieceById(player1PieceTakenDamage.getId()));
		assertEquals("playingCup", player2PieceTakenDamage.getLocation().getId());
		assertEquals(null, player2.getGamePieceById(player2PieceTakenDamage.getId()));
		
		/**
		 * Range Step
		 */
		RangedCombatBattleStep rangeStep = (RangedCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(magicStep.isEnded());
		assertTrue(rangeStep.isStarted());
		
		assertEquals(2, rangeStep.getAttackerHitCount());
		assertEquals(1, rangeStep.getDefenderHitCount());
		
		Set<String> player1RangeHits = new HashSet<String>();
		player1RangeHits.add("T_Mountains_034-01");
		rangeStep.playerLockedInRollAndDamage(player1, player1RangeHits);
		
		Set<String> player2RangeHits = new HashSet<String>();
		player2RangeHits.add("T_Swamp_076-01");
		player2RangeHits.add("T_Swamp_083-01");
		rangeStep.playerLockedInRollAndDamage(player2, player2RangeHits);
		
		player1PieceTakenDamage = gameState.getGamePiece("T_Mountains_034-01");
		GamePiece player2PieceTakenDamage2 = gameState.getGamePiece("T_Swamp_083-01");
		player2PieceTakenDamage = gameState.getGamePiece("T_Swamp_076-01");

		assertEquals("playingCup", player1PieceTakenDamage.getLocation().getId());
		assertEquals(null, player1.getGamePieceById(player1PieceTakenDamage.getId()));
		assertEquals("playingCup", player2PieceTakenDamage2.getLocation().getId());
		assertEquals(null, player1.getGamePieceById(player2PieceTakenDamage2.getId()));
		assertEquals("playingCup", player2PieceTakenDamage.getLocation().getId());
		assertEquals(null, player2.getGamePieceById(player2PieceTakenDamage.getId()));
		
		assertEquals(8, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player1).size());
		assertEquals(7, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player2).size());
		
		
		/**
		 * Melee
		 */
		MeleeCombatBattleStep meleeStep = (MeleeCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(rangeStep.isEnded());
		assertTrue(meleeStep.isStarted());
		
		assertEquals(5, meleeStep.getAttackerHitCount());
		assertEquals(5, meleeStep.getDefenderHitCount());
		
		
		Set<String> player1MeleeHits = new HashSet<String>();
		player1MeleeHits.add("T_Desert_114-01");
		player1MeleeHits.add("T_Desert_115-01");
		player1MeleeHits.add("T_Forest_100-01");
		player1MeleeHits.add("T_Plains_014-01");
		player1MeleeHits.add("T_Frozen_Waste_054-01");
		meleeStep.playerLockedInRollAndDamage(player1, player1MeleeHits);
		
		Set<String> player2MeleeHits = new HashSet<String>();
		player2MeleeHits.add("T_Forest_090-01");
		player2MeleeHits.add("T_Forest_087-01");
		player2MeleeHits.add("T_Desert_113-01");
		player2MeleeHits.add("T_Desert_117-01");
		player2MeleeHits.add("T_Swamp_080-01");
		meleeStep.playerLockedInRollAndDamage(player2, player2MeleeHits);
		
		assertEquals(3, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player1).size());
		assertEquals(2, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player2).size());
		
		/**
		 * Post steps 
		 */
		assertTrue(round.isStepsAreOver());
		
		round.playerDidRetreatOrContinue(player1, false);
		round.playerDidRetreatOrContinue(player2, false);
		
		assertTrue(round.isEnded());
		
		/**
		 * Magic of new round
		 */
		round = battle.getRound();
		magicStep = (MagicCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(magicStep.isStarted());
		
		assertEquals(1, magicStep.getAttackerHitCount());
		assertEquals(1, magicStep.getDefenderHitCount());
		
		player1MagicHits = new HashSet<String>();
		player1MagicHits.add("T_Frozen_Waste_063-01");
		magicStep.playerLockedInRollAndDamage(player1, player1MagicHits);
		
		player2MagicHits = new HashSet<String>();
		player2MagicHits.add("T_Desert_108-01");
		magicStep.playerLockedInRollAndDamage(player2, player2MagicHits);
		
		assertEquals(2, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player1).size());
		assertEquals(1, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player2).size());
		
		/**
		 * Range of new round
		 */
		
		rangeStep = (RangedCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(magicStep.isEnded());
		assertTrue(rangeStep.isStarted());
		
		assertEquals(1, rangeStep.getAttackerHitCount());
		assertEquals(1, rangeStep.getDefenderHitCount());
		
		player1RangeHits = new HashSet<String>();
		player1RangeHits.add("T_Frozen_Waste_058-01");
		rangeStep.playerLockedInRollAndDamage(player1, player1RangeHits);
		
		player2RangeHits = new HashSet<String>();
		player2RangeHits.add("T_Forest_101-01");
		rangeStep.playerLockedInRollAndDamage(player2, player2RangeHits);
		
		// Attacker won
		
		assertTrue(round.isEnded());
		assertTrue(battle.isOver());
		
		assertEquals(player1, hexLoc3.getOwner());
		
		/***
		 * Now handling next battle
		 */
		
		battle = cp.getCombatBattles().get(1);
		
		round = battle.getRound();
		assertEquals(player4, battle.getAttacker());
		assertEquals(player3, battle.getDefender());
		assertTrue(round.isStarted());
		
		rangeStep = (RangedCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		
		assertEquals(2, rangeStep.getAttackerHitCount());
		assertEquals(2, rangeStep.getDefenderHitCount());
		
		player1RangeHits = new HashSet<String>();
		player1RangeHits.add("T_Forest_091-01");
		player1RangeHits.add("Fort_03-01");
		rangeStep.playerLockedInRollAndDamage(player3, player1RangeHits);
		
		player2RangeHits = new HashSet<String>();
		player2RangeHits.add("T_Mountains_047-01");
		player2RangeHits.add("T_Mountains_049-01");
		rangeStep.playerLockedInRollAndDamage(player4, player2RangeHits);
		
		assertEquals(3, hexLoc6.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player3).size());
		assertEquals(2, hexLoc6.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player4).size());
		
		meleeStep = (MeleeCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertEquals(1, meleeStep.getAttackerHitCount());
		assertEquals(1, meleeStep.getDefenderHitCount());
		
		player1MeleeHits = new HashSet<String>();
		player1MeleeHits.add("T_Swamp_085-01");
		meleeStep.playerLockedInRollAndDamage(player3, player1MeleeHits);
		player1MeleeHits = new HashSet<String>();
		player1MeleeHits.add("T_Swamp_067-01");
		meleeStep.playerLockedInRollAndDamage(player4, player1MeleeHits);
		
		round.playerDidRetreatOrContinue(player3, true);
		round.playerDidRetreatOrContinue(player4, false);
		
		assertTrue(round.isEnded());
		assertTrue(battle.isOver());
		
		assertEquals(null, wasserchlange.getOwner());
		assertEquals(gameState.getPlayingCup(), wasserchlange.getLocation());
		assertEquals(player4, mine.getOwner());
		assertEquals(hexLoc6, mine.getLocation());
	}
	
	
	@Test
	public void testCombat2() throws Exception {
		GameState gameState = getNewGameState();
		Player player1 = gameState.getPlayerByPlayerId("player1");
		
		
		gameState.setCurrentPhase(new MovementPhase(gameState, gameState.getCurrentPhase().getPlayersInOrderOfTurn(), false));
		MovementPhase mp = (MovementPhase) gameState.getCurrentPhase();
		
		HexLocation hexLoc1 = gameState.getHexLocationsById("hexLocation_1");

		
		// Stack 1
		Stack stack1 = new Stack("stack1");
		stack1.setOwner(player1);
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Jungle_005-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Mountains_034-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Desert_114-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Desert_115-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Forest_100-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Plains_014-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Frozen_Waste_054-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Frozen_Waste_063-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Frozen_Waste_058-01"));
		stack1.addGamePieceToLocation(gameState.getGamePiece("T_Jungle_004-01"));
		hexLoc1.addStack(stack1);
		mp.didExploreHex("player1", "hexLocation_3", null, "stack1", 5);
		

		HexLocation hexLoc3 = gameState.getHexLocationsById("hexLocation_3");
		/*hexLoc3.addGamePieceToLocation(gameState.getGamePiece("T_Swamp_066-01"));
		hexLoc3.addGamePieceToLocation(gameState.getGamePiece("T_Swamp_083-01"));
		hexLoc3.addGamePieceToLocation(gameState.getGamePiece("T_Forest_090-01"));
		hexLoc3.addGamePieceToLocation(gameState.getGamePiece("T_Forest_087-01"));
		hexLoc3.addGamePieceToLocation(gameState.getGamePiece("T_Forest_101-01"));*/
		
		
		
		
		gameState.addDiceRollForTest(1);
		gameState.setCurrentPhase(new CombatPhase(gameState, gameState.getCurrentPhase().getPlayersInOrderOfTurn()));
		assertEquals("combat", gameState.getCurrentPhase().getPhaseId());
		CombatPhase cp = (CombatPhase)gameState.getCurrentPhase();
		cp.start();
		
		
		/**
		 * First Battle
		 */
		
		Set<GamePiece> gps = hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(null);
		for(GamePiece gp: gps) {
			System.out.println(gp.getId());
		}
		
		assertEquals(10, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player1).size());
		assertEquals(5, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(null).size());
		
		CombatBattle battle = cp.getCombatBattles().get(0);
		CombatBattleRound round = battle.getRound();
		
		assertEquals(player1, battle.getAttacker());
		assertTrue(battle.isAIDefender());
		assertTrue(round.isStarted());
		assertTrue(round.getSteps().get(round.getCurrentStep()) instanceof MagicCombatBattleStep);
		
		/**
		 * Magic Step
		 */
		MagicCombatBattleStep magicStep = (MagicCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(magicStep.isStarted());

		assertEquals(1, magicStep.getAttackerHitCount());
		
		Set<String> player1MagicHits = new HashSet<String>();
		player1MagicHits.add("T_Jungle_005-01");
		magicStep.playerLockedInRollAndDamage(player1, player1MagicHits);
		

		/**
		 * Range Step
		 */
		RangedCombatBattleStep rangeStep = (RangedCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(magicStep.isEnded());
		assertTrue(rangeStep.isStarted());
		
		assertEquals(2, rangeStep.getAttackerHitCount());
		
		Set<String> player1RangeHits = new HashSet<String>();
		player1RangeHits.add("T_Mountains_034-01");
		rangeStep.playerLockedInRollAndDamage(player1, player1RangeHits);
		
		assertEquals(8, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player1).size());
		assertEquals(2, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(null).size());
		
		
		/**
		 * Melee
		 */
		MeleeCombatBattleStep meleeStep = (MeleeCombatBattleStep)round.getSteps().get(round.getCurrentStep());
		assertTrue(rangeStep.isEnded());
		assertTrue(meleeStep.isStarted());
		
		assertEquals(5, meleeStep.getAttackerHitCount());
		
		
		Set<String> player1MeleeHits = new HashSet<String>();
		player1MeleeHits.add("T_Desert_114-01");
		player1MeleeHits.add("T_Desert_115-01");
		player1MeleeHits.add("T_Forest_100-01");
		player1MeleeHits.add("T_Plains_014-01");
		player1MeleeHits.add("T_Frozen_Waste_054-01");
		meleeStep.playerLockedInRollAndDamage(player1, player1MeleeHits);
		
		assertEquals(3, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(player1).size());
		assertEquals(0, hexLoc3.getAllPiecesOnHexIncludingPiecesInStacksForPlayer(null).size());
		
		assertTrue(battle.isOver());
		assertEquals(player1, battle.getLocationOfBattle().getOwner());
	}
}
