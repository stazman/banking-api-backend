package com.bankingexamples.services;

import com.bankingexamples.dao.RoleDAO;
import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.User;

public class UserService { 

	private UserDAO userDao;
		
	public UserService(UserDAO ud, RoleDAO rd) {
		userDao = ud;
	}

	public User findPersonById(Integer id) {
		return userDao.getUserById(id);
	}

	public String updateUser(User u) {
		return userDao.updateUser(u);	
	}

}