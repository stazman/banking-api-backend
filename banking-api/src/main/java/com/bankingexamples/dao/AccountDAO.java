package com.bankingexamples.dao;

import java.util.Set;

import com.bankingexamples.models.Account;
import com.bankingexamples.models.AccountStatus;
import com.bankingexamples.models.AccountType;
import com.bankingexamples.models.User;

public interface AccountDAO {

	public Integer createAccount(Integer userId, Account acct);
	public Double getAccountBalanceByAcctId(Integer acctId);
	public Integer updateAccount(Integer id);
	public Set<Account> getAccountsByStatus(Account status);
	public Set<Account> getAccountsByUserId(Integer userId);

}
