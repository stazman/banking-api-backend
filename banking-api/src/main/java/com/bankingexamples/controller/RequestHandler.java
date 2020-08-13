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

public class RequestHandler {
	
	private Map<String, FrontControllerDelegate> delegateMap;
	
	{
		delegateMap = new HashMap<String, FrontControllerDelegate>();
		
		delegateMap.put("login", new LoginDelegate());
		delegateMap.put("logout", new LogoutDelegate());
		delegateMap.put("register", new AdminDelegate());
		delegateMap.put("employee", new AdminEmployeeDelegate());
		delegateMap.put("users", new UserDelegate());
		delegateMap.put("transaction", new TransactionDelegate());
		delegateMap.put("accounts", new AccountDelegate());
	}
	
	public FrontControllerDelegate handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if ("OPTIONS".equals(req.getMethod())) {
			return (r1, r2) -> {};
		}
		
		StringBuilder uriString = new StringBuilder(req.getRequestURI());
		
		System.out.println(uriString);

		uriString.replace(0, req.getContextPath().length()+1, "");

		if (uriString.indexOf("/") != -1) {
			req.setAttribute("path", uriString.substring(uriString.indexOf("/")+1));
			uriString.replace(uriString.indexOf("/"), uriString.length(), "");

		}
		
//		System.out.println(uriString);
		
//		System.out.println(delegateMap.get(uriString.toString()));
		
		return delegateMap.get(uriString.toString());
	}
}

















