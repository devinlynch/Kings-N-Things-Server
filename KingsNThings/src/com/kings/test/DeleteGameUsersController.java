package com.kings.test;

import org.springframework.web.bind.annotation.RequestMapping;

import com.kings.database.DataAccess;

@RequestMapping("/tests")
public class DeleteGameUsersController {
	
	@RequestMapping(value="deleteGameUsers")
	public static void deleteGameUsers() {
		DataAccess access = new DataAccess();
		
		access.beginTransaction();
		access.getSession().createSQLQuery("delete from game_user");
		access.commit();
	}
	
	public static void main(String[] args) {
		deleteGameUsers();
	}
	
}
