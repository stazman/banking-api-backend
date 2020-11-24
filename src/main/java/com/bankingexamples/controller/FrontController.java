package com.bankingexamples.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankingexamples.delegates.FrontControllerDelegate;

public class FrontController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestHandler rh = new RequestHandler();
	
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		FrontControllerDelegate fcd = rh.handle(req, resp);
		
		if (fcd != null)
			fcd.process(req, resp);
		else
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
}
