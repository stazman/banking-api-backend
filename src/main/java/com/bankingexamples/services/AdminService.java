package com.bankingexamples.services;

import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.User;

public class AdminService {
	
private UserDAO userDao;
	
	public AdminService(UserDAO ud) {
		userDao = ud;
	}
	
	public int registerUser(User u) {
		return userDao.registerUser(u);	
	}
	
}