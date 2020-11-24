package com.bankingexamples.delegates;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.dao.AccountPostgres;
import com.bankingexamples.dao.UserPostgres;
import com.bankingexamples.models.Account;
import com.bankingexamples.models.User;
import com.bankingexamples.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountDelegate implements FrontControllerDelegate {
	
	AccountService as = new AccountService(new AccountPostgres(), new UserPostgres());
	
	ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		String path = (String) req.getAttribute("path");
		
		Integer userId = Integer.valueOf(path);
				
		User thisUser = (User) req.getSession().getAttribute("user");

		
		if (thisUser.equals(userId) || thisUser.getRole().toString() == "Admin" || thisUser.getRole().toString() == "Employee") { 
			
			if (path == null || path == "") {
		
				if ("POST".equals(req.getMethod())) {
				
					Account newAcct = null;
					
					Account acct = (Account) om.readValue(req.getInputStream(), Account.class);
					
					User u = (User) om.readValue(req.getInputStream(), User.class);
					
					acct.setAccountId(acct.getAccountId());
					
					newAcct = as.makeAccount(acct, u);
					
					resp.getWriter().write(om.writeValueAsString(acct));
					
					resp.setStatus(HttpServletResponse.SC_CREATED);
					
				} else if ("PUT".equals(req.getMethod())) {
					
					Account updatedAccount = null;
					
					req.getSession().getAttribute("account");
					
					updatedAccount = om.readValue(req.getInputStream(), Account.class);
					
						as.updateAccount(updatedAccount);
						
						resp.getWriter().write(om.writeValueAsString(updatedAccount));
					
						resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						
				} else  {
					
					if (path.contains("owner")) {
						
						int userId2 = Integer.valueOf(path);
						
						Set<Account> accts = null;
											
						if (thisUser.equals(userId2) || thisUser.getRole().toString() == "Admin" || thisUser.getRole().toString() == "Employee") { 
							
							accts = as.findAccountsByUser(thisUser);
	
						} else {
								
							resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
								
						}
						
					} else {
						
						int acctId = Integer.valueOf(path);
						
						Account accts = null;
							
						accts = om.readValue(req.getInputStream(), Account.class);
										
						accts = as.findAccountById(acctId);
							
						if (accts != null) 
								
							resp.getWriter().write(om.writeValueAsString(accts));
								
						else
								
							resp.sendError(404, "Accounts not found for this user.");											
						
					}
					
				}
				
			}
			
		} else {
			
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
	}
}

