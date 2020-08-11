package com.bankingexamples.services;

import java.util.Set;

import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.User;

public class UserService { 

	private UserDAO userDao;
		
	public UserService(UserDAO ud) {
		userDao = ud;
	}

	public User findUserById(Integer id) {
		return userDao.getUserById(id);
	}

	public User updateUser(User u) {
		return userDao.updateUser(u);	
	}
	
	public User findUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

}