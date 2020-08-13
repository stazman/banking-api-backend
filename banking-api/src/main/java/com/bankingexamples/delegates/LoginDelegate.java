package com.bankingexamples.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.dao.UserPostgres;
import com.bankingexamples.models.User;
import com.bankingexamples.services.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;


public class LoginDelegate implements FrontControllerDelegate {
	
	private ObjectMapper om = new ObjectMapper();  
	
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		UserService us = new UserService(new UserPostgres());
		String path = (String) req.getAttribute("path");
			
		if (path == "" || path==null){
					
			if (req.getMethod().equals("POST")) {
				
				String username = req.getParameter("username");
				String password = req.getParameter("password");
				
				User u = us.findUserByUsername(username);
				
				if (u.getPassword().equals(password)) {
					
//					System.out.println(System.getProperty("username"));
					
					req.getSession().setAttribute("user", u);
					resp.getWriter().write(om.writeValueAsString(u));
					
//					User thisUser = (User) req.getSession().getAttribute("user");
//		
//					System.out.println(thisUser.getUsername());
					
				} else {
					
					resp.sendError(401,"Invalid Credentials");
				}
				
				
			} else {
				
				resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
			
		} else {
			
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		
	}

}