package com.bankingexamples.services;

import java.util.Set;

import com.bankingexamples.dao.AdminEmployeeUserDAO;
import com.bankingexamples.models.User;

public class AdminEmployeeUserService {
	
	private AdminEmployeeUserDAO adminEmployeeUserDao;
	
	public AdminEmployeeUserService(AdminEmployeeUserDAO aud) {
		adminEmployeeUserDao = aud;
	}
	
	public Set<User> findAllUsers() {
		return adminEmployeeUserDao.getAllUsers();
	}

}