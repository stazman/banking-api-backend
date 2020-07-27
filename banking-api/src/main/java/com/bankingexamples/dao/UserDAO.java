package com.bankingexamples.dao;

import com.bankingexamples.models.User;
import com.bankingexamples.models.Role;

public interface UserDAO {

	public Integer createUser (User u);
	public int getUserById(int id);
	public String getUserByEmail(String email);
	public String getUserByLastName(User lastName);
	public String updateUser (User u);
	public boolean deleteUser (User u);
	public String getRole (Role r);
	
}
