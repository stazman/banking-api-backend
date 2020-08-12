package com.bankingexamples.delegates;

import java.io.IOException;
import java.util.Set;

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
		
		int userId = Integer.valueOf(path);
		
		User user = null;
		
		User thisUser = (User) req.getSession().getAttribute("user");
		
		if (path.contains("users") && (thisUser.equals(userId) || thisUser.getRole().toString() == "Admin" || thisUser.getRole().toString() == "Employee")) { 
			switch (req.getMethod()) {
			
			case "GET":
				
				user = uServ.findUserById(userId); //3
			
				if (user != null) 
					resp.getWriter().write(om.writeValueAsString(user));
				else
					resp.sendError(404, "User not found.");
				break;
			
			case "PUT":
				
				req.getSession().getAttribute("user");
									
				user = om.readValue(req.getInputStream(), User.class);
				
					uServ.updateUser(user); //2
					
					resp.getWriter().write(om.writeValueAsString(user));
				
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}

		}			
	}
}

