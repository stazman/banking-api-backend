package com.bankingexamples.services;

import java.util.Set;

import com.bankingexamples.dao.AccountDAO;
import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.Account;
import com.bankingexamples.models.AccountStatus;
import com.bankingexamples.models.User;

public class AdminEmployeeService {

	private UserDAO userDao;
	private AccountDAO acctDao;
	
	public AdminEmployeeService(UserDAO ud, AccountDAO ad) {
		userDao = ud;
		acctDao = ad;
	}
	
	public Set<User> findAllUsers() {
		return userDao.getAllUsers();
	}
	
	public Set<Account> findAccountsByAccountStatus(AccountStatus acctStatus) {
		return acctDao.getAccountsByAccountStatus(acctStatus);
	}

}