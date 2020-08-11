package com.bankingexamples.dao;

import java.util.Set;

import com.bankingexamples.models.User;

public interface UserDAO {

	public Integer registerUser(User u);
	public User getUserById(int id);
	public User getUserByUsername(String username);
	public Set<User> getAllUsers();
	public User updateUser (User u);

}