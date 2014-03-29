package com.kings.controllers.phases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kings.http.HttpResponseMessage;
import com.kings.model.Game;
import com.kings.model.GameChatMessage;
import com.kings.model.User;

@RequestMapping("game")
public class InGameController extends PhaseController {

	@RequestMapping(value="leaveGame")
	public String leaveGame(
			HttpServletRequest req,
			HttpServletResponse res) {
		
		try{
			User user = getUser(req);
			Game game = user.getGame();
			
			if(game != null) {
				game.end(true);
				getDataAccess().save(game);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return genericError().toJson();
		}
		
		return successMessage().toJson();
	}
	
	
	@RequestMapping(value="sendChat")
	public String sendChat(
			@RequestParam String message,
			HttpServletRequest req,
			HttpServletResponse res) {
		
		try{
			User user = getUser(req);
			Game game = user.getGame();
			game.sendChatMessage(user, message, getDataAccess());
			
			getDataAccess().save(game);
		} catch(Exception e) {
			e.printStackTrace();
			return genericError().toJson();
		}
		
		return successMessage().toJson();
	}
	
	
	@RequestMapping(value="getChatMessages")
	public String getChatMessages(
			@RequestParam String lastId,
			HttpServletRequest req,
			HttpServletResponse res) {
		
		try{
			User user = getUser(req);
			Game game = user.getGame();
			game.getChatMessages();
			
			List<GameChatMessage> messages  = getDataAccess().getChatMessagesAfterId(game, lastId);
			
			HttpResponseMessage msg = new HttpResponseMessage();
			msg.setType("batchOfChatMessages");
			
			List<Map<String, Object>> msgList = new ArrayList<Map<String, Object>>();
			for(GameChatMessage m : messages) {
				msgList.add(m.toSerializedFormat());
			}
			
			msg.addToData("messages", msgList);
			
			return msg.toJson();
			
		} catch(Exception e) {
			e.printStackTrace();
			return genericError().toJson();
		}
	}
	
	
}
