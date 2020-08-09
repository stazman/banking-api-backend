package com.bankingexamples.dao;

import java.util.Set;

import com.bankingexamples.models.Account;
import com.bankingexamples.models.AccountStatus;
import com.bankingexamples.models.User;

public interface AccountDAO {

	public Account createAccount(Account acct, User userId);
	public Account getAccountById(Integer acctId);
	Set<Account> getAccountsByAccountStatus(AccountStatus acctStatus);
	Set<Account> getAccountsByUser(User user);
	public Account updateAccount(Account acct);

}