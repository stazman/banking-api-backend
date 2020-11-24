package com.bankingexamples.services;

import java.util.Set;

import com.bankingexamples.dao.AccountDAO;
import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.Account;
import com.bankingexamples.models.User;

public class AccountService {

	private AccountDAO acctDao;
	
	public AccountService(AccountDAO ad, UserDAO ud) {
		acctDao = ad;		
	}

	public Account makeAccount(Account acct, User user) {
		return acctDao.createAccount(acct, user);
	}
	
	public Account findAccountById(Integer acctId) {
		return acctDao.getAccountById(acctId);
	}
	
	public Set<Account> findAccountsByUser(User u) {
		return acctDao.getAccountsByUser(u);
	}
	
	public Account updateAccount(Account acct) {
		return acctDao.updateAccount(acct);
	};

}