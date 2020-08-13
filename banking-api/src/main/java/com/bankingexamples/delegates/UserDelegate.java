package com.bankingexamples.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.dao.UserPostgres;
import com.bankingexamples.models.User;
import com.bankingexamples.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDelegate implements FrontControllerDelegate {

	private UserService uServ = new UserService(new UserPostgres());
	
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = (String) req.getAttribute("path");
		
		System.out.println(path);
		
		int userId = Integer.valueOf(path);
		
		User user = null;
		
		User thisUser = (User) req.getSession().getAttribute("user");
		
		System.out.println(userId);
		
		if (thisUser.getUserId() == userId || "Admin".equals(thisUser.getRole().getRole().toString()) || "Employee".equals(thisUser.getRole().getRole().toString())) { 
			
			switch (req.getMethod()) {
			
			case "GET":
				
				user = uServ.findUserById(userId);
			
				if (user != null) 
					resp.getWriter().write(om.writeValueAsString(user));
				else if (user!=thisUser)
					resp.sendError(404, "User not found.");
				break;
			
			case "PUT":
				
				req.getSession().getAttribute("user");
									
				user = om.readValue(req.getInputStream(), User.class);
				
					uServ.updateUser(user);
					
					resp.getWriter().write(om.writeValueAsString(user));
		
			}

		} else {
			
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			
		}
	}
}