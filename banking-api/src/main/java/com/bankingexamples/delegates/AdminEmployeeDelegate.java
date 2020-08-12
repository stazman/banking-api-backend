package com.bankingexamples.delegates;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.dao.AccountPostgres;
import com.bankingexamples.dao.UserPostgres;
import com.bankingexamples.models.Account;
import com.bankingexamples.models.AccountStatus;
import com.bankingexamples.models.User;
import com.bankingexamples.services.AccountService;
import com.bankingexamples.services.AdminEmployeeService;
import com.bankingexamples.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminEmployeeDelegate implements FrontControllerDelegate {
	
	private AdminEmployeeService aes = new AdminEmployeeService(new UserPostgres(), new AccountPostgres());
	
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = (String) req.getAttribute("path");
		
		 String pattern = "";

	     boolean b = Pattern.matches("accounts+[\\d]", path);  
		
		if ("GET".equals(req.getMethod())) {
			
			if (path.contains("users")) {
				
				Set<User> users = aes.findAllUsers();
					
				//???
					
			

			} else if (b){ 
			
		    	 AccountStatus status = (); //??? actually, return the right thing
			
		    	 Set<Account> accts = aes.findAccountsByAccountStatus(status);
						
		    	
			} else {
				
				
				Set<Account> accts = aes.findAllAccounts();
				
				
			}
			
			
			
		} else {
						
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						
		}
			
	}
		
}
						
						
//2. /accounts/user
						
						
//		public 
//			return acctDao.getAccountsByUser(u);
//		}
		



//public class YourDelegate  implements FrontControllerDelegate{
//
//	Instantiate and initialize service(s) you will use
//	Instantiate and initialize Object mapper
//	
//	public void process(HttpServletRequest req, HttpServletResponse resp) throws
//     ServletException, IOException {
//		
//		String path = (String) req.getAttribute(“path”);
//		
//If else or switch to find which path to take
//			In each path if else or switch to find which method to call
//				Call necessary methods in your service(s) to accomplish the goal
//			Else SC_METHOD_NOT_FOUND or METHOD_NOT_ALLOWED
//		Else BAD_REQUESTor 404
//	}
//}
