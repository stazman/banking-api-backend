package com.bankingexamples.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.bankingexamples.models.Role;
import com.bankingexamples.models.User;
import com.bankingexamples.utilities.ConnectionUtil;

public class UserPostgres implements UserDAO {

	ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	

	public User getUserById(int id) {
		
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			String sql = "select persn.id, persn.usernm, "
					+ "persn.passwd, persn.fst_nm, persn.lst_nm, persn.eml, (select bank_role.bank_role from bank_role br where br.bank_role_id = bank_role.bank_role_id)"
					+ "from persn where persn_id = ?";
			
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
				r.setRoleId(rs.getInt("bank_role_id"));
				r.setRole(rs.getString("role_name"));
				
				u.setRole(r);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return u;
	}


	public String getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserByLastName(User lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<User> getUsersByRole(User r) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRoleByUser(Role r) {
		// TODO Auto-generated method stub
		return null;
	}

}
