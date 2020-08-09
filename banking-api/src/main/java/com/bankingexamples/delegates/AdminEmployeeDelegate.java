package com.bankingexamples.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminEmployeeDelegate implements FrontControllerDelegate {
	
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}


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
