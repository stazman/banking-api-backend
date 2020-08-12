package com.bankingexamples.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.bankingexamples.models.Role;
import com.bankingexamples.models.User;
import com.bankingexamples.utilities.ConnectionUtil;

public class UserPostgres implements UserDAO {

	ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public Integer registerUser(User u) {
		
		Integer userId = 0;
		
		try (Connection conn = cu.getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "insert into persn values (default, ?, ?, ?, ?, ?, ?)";
			
			String[] keys = {"persn_id"};
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getEmail());
			pstmt.setInt(6, u.getRole().getRoleId());
			
			pstmt.executeUpdate();
			
			System.out.println(pstmt);
			
			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				userId = rs.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userId;
	}

	
	public User getUserById(int id) {
		
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			
			String sql = "select persn.persn_id, persn.usernm, persn.passwd, persn.fst_nm, persn.lst_nm, persn.eml, (select bank_role.bank_role from bank_role where bank_role.bank_role_id = persn.bank_role_id) from persn where persn_id = ?";
						
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {

				u = new User();
				
				u.setUserId(rs.getInt("persn_id"));
				u.setUsername(rs.getString("usernm"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstName(rs.getString("fst_nm"));
				u.setLastName(rs.getString("lst_nm"));
				u.setEmail(rs.getString("eml"));
				
				Role r = new Role();
				r.setRole(rs.getString("bank_role"));
				
				u.setRole(r);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return u;
	}

	@Override
	public User getUserByUsername(String username) {
		
		try (Connection conn = cu.getConnection()) {

			String sql = "select * from persn join bank_role on persn.bank_role_id = bank_role.bank_role_id where persn.usernm = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt("persn_id"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstName(rs.getString("fst_nm"));
				u.setLastName(rs.getString("lst_nm"));
				u.setEmail(rs.getString("eml"));

				
				Role r = new Role();
				r.setRoleId(rs.getInt("bank_role_id"));
				r.setRole(rs.getString("bank_role"));
				u.setRole(r);
				
				return u;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Set<User> getAllUsers() {
		Set<User> users = new HashSet<>();

		try {
			Connection conn = cu.getConnection();
			String sql = "select persn_id, usernm, fst_nm, lst_nm, eml, bank_role.bank_role_id, bank_role.bank_role from "
			+ "persn join bank_role on persn.bank_role_id = bank_role.bank_role_id";		
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				User u = new User();

				u.setUserId(rs.getInt("id"));
				u.setUsername(rs.getString("usernm"));
				u.setFirstName(rs.getString("fst_nm"));
				u.setLastName(rs.getString("lst_nm"));
				u.setEmail(rs.getString("eml"));
				
				Role role = new Role();
				role.setRoleId(rs.getInt("bank_role_id"));
				role.setRole(rs.getString("bank_role"));
				u.setRole(role);
				
				users.add(u);
				
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	@Override
	public User updateUser(User u) {
		
		try (Connection conn = cu.getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "update persn set usernm = ?, passwd = ?, fst_name = ?, lst_nm = ?, eml = ?"
					+ "bank_role_id = ? where persn_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
				pstmt.setString(1, u.getUsername());
				pstmt.setString(2, u.getPassword());
				pstmt.setString(3, u.getFirstName());
				pstmt.setString(4, u.getLastName());
				pstmt.setString(5, u.getEmail());
				pstmt.setInt(6, u.getRole().getRoleId());
				pstmt.setInt(7,  u.getUserId());
				
				int rs = pstmt.executeUpdate();
				
				if(rs > 0) {
					return u;
				}
				
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}

}