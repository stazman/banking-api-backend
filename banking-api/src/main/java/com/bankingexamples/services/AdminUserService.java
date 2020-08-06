package com.bankingexamples.services;

import com.bankingexamples.dao.AdminUserDAO;
import com.bankingexamples.models.User;

public class AdminUserService {

	private AdminUserDAO adminUserDao;
	
	public AdminUserService(AdminUserDAO aud) {
		adminUserDao = aud;
	}
	
	public Integer registerUser(User u) {
		return adminUserDao.registerUser(u);	
	}
	
}