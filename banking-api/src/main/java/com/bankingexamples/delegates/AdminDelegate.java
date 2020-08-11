package com.bankingexamples.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.dao.UserPostgres;
import com.bankingexamples.models.User;
import com.bankingexamples.services.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminDelegate implements FrontControllerDelegate{

	private AdminService aServ = new AdminService(new UserPostgres());
	
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		
		if (path == null || path.equals("")) {
			
			switch (req.getMethod()) {
				case "POST":

					User u = (User) om.readValue(req.getInputStream(), User.class);
					u.setUserId(aServ.registerUser(u));
					resp.getWriter().write(om.writeValueAsString(u));
					resp.setStatus(HttpServletResponse.SC_CREATED);
					break;
				default:
					resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
					break;
			}			
		}
	}
}