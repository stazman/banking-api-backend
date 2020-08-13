package com.bankingexamples.delegates;

import java.io.IOException;
import java.util.Set;
//import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.dao.AccountPostgres;
import com.bankingexamples.dao.UserPostgres;
import com.bankingexamples.models.Account;
import com.bankingexamples.models.AccountStatus;
import com.bankingexamples.models.User;
import com.bankingexamples.services.AdminEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminEmployeeDelegate implements FrontControllerDelegate {
	
	private AdminEmployeeService aes = new AdminEmployeeService(new UserPostgres(), new AccountPostgres());
	
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = (String) req.getAttribute("path");

	    boolean b = Pattern.matches("accounts+[\\d]", path);  
		
	     if ("GET".equals(req.getMethod())) {
			
			if (path.contains("users")) {
				
				Set<User> users = aes.findAllUsers();
					
				resp.getWriter().write(om.writeValueAsString(users));					
			

			} else if (b){ 
				
				AccountStatus acctStat = (AccountStatus) req.getAttribute("accountStatus");
			
				Set<Account> accts = aes.findAccountsByAccountStatus(acctStat);
				
				resp.getWriter().write(om.writeValueAsString(accts));
						
		    	
			} else {
				
				Set<Account> accts = aes.findAllAccounts();
				
				resp.getWriter().write(om.writeValueAsString(accts));
				
			}
			
		} else {
						
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						
		}
			
	}
		
}