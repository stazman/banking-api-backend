package com.bankingexamples.services;

import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.User;

public class UserService { 

	private UserDAO userDao;
		
	public UserService(UserDAO ud) {
		userDao = ud;
	}

	public User findUserById(int id) {
		return userDao.getUserById(id);
	}

	public User updateUser(User u) {
		return userDao.updateUser(u);	
	}
	
	public User findUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

}