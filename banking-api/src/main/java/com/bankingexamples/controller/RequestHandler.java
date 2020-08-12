package com.bankingexamples.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import javax.management.relation.Role;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.delegates.AccountDelegate;
import com.bankingexamples.delegates.AdminDelegate;
import com.bankingexamples.delegates.AdminEmployeeDelegate;
import com.bankingexamples.delegates.FrontControllerDelegate;
import com.bankingexamples.delegates.LoginDelegate;
import com.bankingexamples.delegates.LogoutDelegate;
import com.bankingexamples.delegates.TransactionDelegate;
import com.bankingexamples.delegates.UserDelegate;
import com.bankingexamples.models.User;
import com.bankingexamples.models.Role;

public class RequestHandler {
	
	private Map<String, FrontControllerDelegate> delegateMap;
	
	{
		delegateMap = new HashMap<String, FrontControllerDelegate>();
		
		delegateMap.put("login", new LoginDelegate());
		delegateMap.put("logout", new LogoutDelegate());
		delegateMap.put("register", new AdminDelegate());
		delegateMap.put("adminEmployee", new AdminEmployeeDelegate());
		delegateMap.put("users", new UserDelegate());
		delegateMap.put("transaction", new TransactionDelegate());
		delegateMap.put("accounts", new AccountDelegate());
	}
	
	public FrontControllerDelegate handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if ("OPTIONS".equals(req.getMethod())) {
			return (r1, r2) -> {};
		}
		
		StringBuilder uriString = new StringBuilder(req.getRequestURI());

		uriString.replace(0, req.getContextPath().length()+1, "");

		if (uriString.indexOf("/") != -1) {
			req.setAttribute("path", uriString.substring(uriString.indexOf("/")+1));
			uriString.replace(uriString.indexOf("/"), uriString.length(), "");

		}
		
		if (!uriString.toString().equals("login") && !uriString.toString().equals("logout")) {
			
			if (req.getSession().getAttribute("user")!=null) {
		
				if (uriString.toString().equals("users")) {
					
					User user = (User) req.getSession().getAttribute("user");
					Role role = user.getRole();
					
					return delegateMap.get(role.getRole());
				}
				
				return delegateMap.get(uriString.toString());
				
			} else {
				
				resp.sendError(401,"The requested action is not permitted");
				
				return (r1, r2) -> {};
			}
		}
		
		return delegateMap.get(uriString.toString());
	}
}

















