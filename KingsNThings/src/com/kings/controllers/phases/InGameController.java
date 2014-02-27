package com.kings.controllers.phases;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.kings.model.Game;
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
			
			game.end();
			getDataAccess().save(game);
		} catch(Exception e) {
			e.printStackTrace();
			return genericError().toJson();
		}
		
		return successMessage().toJson();
	}
}
