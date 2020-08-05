package com.bankingexamples.dao;

import java.util.Set;

import com.bankingexamples.models.Account;
import com.bankingexamples.models.AccountStatus;
import com.bankingexamples.models.AccountType;

public interface AccountDAO {

	public Integer createAccount(int id);
	public Account getAccountById(int id);
	public Set<Account> getAccountsByStatus(AccountStatus status);
	public Set<Account> getAccountsByType(AccountType type);
	public Account makeDeposit(int id);
	public Account makeWithdrawal(int id);
	public Account makeTransfer(int id);
	public void updateAccount(int id);

}
