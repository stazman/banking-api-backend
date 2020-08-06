package com.bankingexamples.dao;

import com.bankingexamples.models.User;

public interface UserDAO {

	public User getUserById(int id);
	public String updateUser (User u);
	public User getUserByUsername(String username); 
	
}