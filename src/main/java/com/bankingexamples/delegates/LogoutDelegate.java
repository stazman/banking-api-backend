package com.bankingexamples.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.models.User;

public class LogoutDelegate implements FrontControllerDelegate {

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User thisUser = (User) req.getSession().getAttribute("user");

		if ("DELETE".equals(req.getMethod())) {			
			
			if(req.getSession().getAttribute("user") != null) {
				
				resp.getWriter().write("You have successfully logged out, " + thisUser.getUsername() + ".");
				
				req.getSession().invalidate();
				
			} else {
				
				resp.sendError(400, "There was no user logged into the session");
				
			}
			
		} else {
			
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);}

	}

}