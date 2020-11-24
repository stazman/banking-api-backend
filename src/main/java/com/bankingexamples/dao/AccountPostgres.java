package com.bankingexamples.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.bankingexamples.models.Account;
import com.bankingexamples.models.AccountStatus;
import com.bankingexamples.models.AccountType;
import com.bankingexamples.models.User;
import com.bankingexamples.utilities.ConnectionUtil;

public class AccountPostgres implements AccountDAO {

	ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	@Override
	public Account createAccount(Account acct, User user) {
					
		try (Connection conn = cu.getConnection()) {
			
			conn.setAutoCommit(false);
						
			
			String sql = "INSERT INTO acct values(default,?,?,?)";

			String[] keys = {"acct_id"};
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			
			pstmt.setDouble(1, acct.getBalance());
			pstmt.setInt(2, acct.getStatus().getStatusId());
			pstmt.setInt(3, acct.getType().getTypeId());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				
				Integer acctId = rs.getInt(1);
				
				sql = "INSERT INTO persn_acct VALUES(?,?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, acctId);
				pstmt.setInt(2, user.getUserId());
				
				if (pstmt.executeUpdate() > 0) {
					acct.setAccountId(acctId);
					conn.commit();
				} else {
				conn.rollback();
			}
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return acct;
	}
	
			
	@Override
	public Account getAccountById(Integer acct_id) {
		
		Account account = new Account();
		
		try(Connection conn = cu.getConnection()){
			
			String sql = "select acct_id, bal, acct.acct_stat_id, acct_stat, acct.acct_type_id, "
					+ "acct_type from acct join acct_stat on acct.acct_stat_id = acct_stat.acct_stat_id "
					+ "join acct_type on acct.acct_type_id = acct_type.acct_type_id where acct.acct_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, acct_id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				
				account.setAccountId(rs.getInt("acct_id"));
				account.setBalance(rs.getDouble("bal"));
				
				AccountStatus status = new AccountStatus();
				status.setStatus(rs.getString("acct_stat"));
				status.setStatusId(rs.getInt("acct_stat_id"));
				account.setStatus(status);
				
				AccountType type = new AccountType();
				type.setType(rs.getString("acct_type"));
				type.setTypeId(rs.getInt("acct_type_id"));
				account.setType(type);	
				
			}
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return account;
	}

	@Override
	public Set<Account> getAccountsByUser(User user) {
		
		Set<Account> accts = new HashSet<>();
		
		try	(Connection conn = cu.getConnection()){
			String sql = "select acct.acct_id, bal, acct.stat_id,"
					+ " acct_stat, acct.acct_type_id, acct_type from acct join acct_stat on"
					+ " acct.acct_stat_id = acct_stat.stat_id join acct_type on acct.acct_type_id ="
					+ " acct_type.acct_type_id join persn_acct on pers_acct.acct_id = "
					+ " acct.acct_id where persn_acct.persn_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, user.getUserId());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("acct_id"));
				account.setBalance(rs.getDouble("bal"));
				
				AccountStatus status = new AccountStatus();
				status.setStatus(rs.getString("acct_stat"));
				status.setStatusId(rs.getInt("acct_stat_id"));
				account.setStatus(status);
				
				AccountType type = new AccountType();
				type.setType(rs.getString("acct_type"));
				type.setTypeId(rs.getInt("acct_type_id"));
				account.setType(type);
				
				accts.add(account);	
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accts;
	}

	@Override
	public Set<Account> getAccountsByAccountStatus(AccountStatus acctStatus) {
		
		System.out.println(acctStatus);
		
		
		Set<Account> accts = new HashSet<>();
		
		try (Connection conn = cu.getConnection()) {
			
			String sql = "select acct_id, bal, acct.acct_stat_id, acct_stat, acct.acct_type_id, acct_type"
					+ " acct_type from acct join acct_stat on acct.acct_stat_id = acct_stat.acct_stat_id"
					+ "	join acct_type on acct.acct_type_id = acct_type.acct_type_id where acct.acct_stat_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			System.out.println(acctStatus.getStatusId());
			
			pstmt.setInt(1, acctStatus.getStatusId());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("acct_id"));
				account.setBalance(rs.getDouble("bal"));
				
				AccountStatus status = new AccountStatus();
				status.setStatus(rs.getString("acct_stat"));
				status.setStatusId(rs.getInt("acct_stat_id"));
				account.setStatus(status);
				
				AccountType type = new AccountType();
				type.setType(rs.getString("acct_type"));
				type.setTypeId(rs.getInt("acct_type_id"));
				account.setType(type);
				
				accts.add(account);
			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return accts;
	}
	
	@Override
	public Account updateAccount(Account acct) {
		
		try (Connection conn = cu.getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "update acct set bal = ?, acct_stat_id = ?, acct_type_id = ? where acct_id = ?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			
			pst.setDouble(1, acct.getBalance());
			pst.setInt(2, acct.getStatus().getStatusId());
			pst.setInt(3, acct.getType().getTypeId());
			pst.setInt(4, acct.getAccountId());
			
			int rowsEffected = pst.executeUpdate();

			if (rowsEffected == 1) {
					conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return acct;
	}


	@Override
	public Set<Account> getAllAccounts() {
		
		Set<Account> accts = new HashSet<>();
		
		try (Connection conn = cu.getConnection()) {
			
			String sql = "select acct_id, bal, acct.acct_stat_id, acct_stat, acct.acct_type_id, acct_type from acct "
					+ "join acct_stat on acct.acct_stat_id = acct_stat.acct_stat_id "
					+ "join acct_type on acct.acct_type_id = acct_type.acct_type_id;";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("acct_id"));
				account.setBalance(rs.getDouble("bal"));
				
				AccountStatus status = new AccountStatus();
				status.setStatus(rs.getString("acct_stat"));
				status.setStatusId(rs.getInt("acct_stat_id"));
				account.setStatus(status);
				
				AccountType type = new AccountType();
				type.setType(rs.getString("acct_type"));
				type.setTypeId(rs.getInt("acct_type_id"));
				account.setType(type);
				
				accts.add(account);
			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return accts;
	}
	
}