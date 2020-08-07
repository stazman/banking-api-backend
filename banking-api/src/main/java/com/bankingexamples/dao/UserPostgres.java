package com.bankingexamples.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.bankingexamples.models.Role;
import com.bankingexamples.models.User;
import com.bankingexamples.utilities.ConnectionUtil;

public class UserPostgres implements UserDAO {

	
	public static void main(String[] args) {
		
//		UserPostgres up = new UserPostgres();
//		
//		User u = up.getUserById(1);
//		
//		System.out.println(u);		
		
//		UserPostgres up = new UserPostgres();
////		
//		User u = up.registerUser("a", "a", "a", "a", "a", "Standard");
////		
//		System.out.println(u);		
		
		
	}
	
	ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public Integer registerUser(User u) {
		
		Integer id = 0;
		
		try (Connection conn = cu.getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "insert into persn values (default, ?, ?, ?, ?, ?, ?)";
			
			String[] keys = {"id"};
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getEmail());
			pstmt.setInt(6, u.getRole().getRoleId());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
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
	public boolean updateUser(User u) {
		
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
				
				int rowsAffected = pstmt.executeUpdate();
				
				if (rowsAffected > 0) {
			
					conn.commit();
					
					return true;
			
				} else {
					
					conn.rollback();
					
				}
				
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public User getUserByUsername(String username) {
		
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			String sql = "select persn.id, persn.usernm, persn.passwd, "
					+ "bank_role_id, bank_role.bank_role as "
					+ "bank_role from persn join bank_role on "
					+ "persn.id = bank_role.id where usernm = ? and "
					+ "passwd = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				u = new User();
				u.setUserId(rs.getInt("id"));
				u.setPassword(rs.getString("passwd"));
				u.setUsername(rs.getString("usernm"));
				u.setFirstName(rs.getString("fst_nm"));
				u.setLastName(rs.getString("lst_nm"));
				u.setEmail(rs.getString("eml"));

				
				Role r = new Role();
				r.setRoleId(rs.getInt("bank_role_id"));
				r.setRole(rs.getString("bank_role"));
				u.setRole(r);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return u;
	}

	@Override
	public Set<User> getAllUsers() {
		Set<User> users = new HashSet<>();

		try {
			Connection conn = cu.getConnection();
			String sql = "SELECT * FROM Users";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				User u = new User();

				u.setUserId(rs.getInt("id"));
				u.setPassword(rs.getString("passwd"));
				u.setUsername(rs.getString("usernm"));
				u.setFirstName(rs.getString("fst_nm"));
				u.setLastName(rs.getString("lst_nm"));
				u.setEmail(rs.getString("eml"));
				
				users.add(u);
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return users;
	}

}