 package com.api.Authorization;

import java.io.*;
import java.sql.*;

import com.api.JDBC.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class Authorize extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter printWriter=response.getWriter();
		JdbcConnection.getConnection(printWriter);
		
		String userType=request.getParameter("user-type");
		System.out.println("Login");
		
		if(userType.equals("admin")) {
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("admin");
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printWriter.println("Forward... to admin page");
		}else if(userType.equals("teacher")) {
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("teacher");
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printWriter.println("Forward... to teacher page");
		}else if(userType.equals("student")) {
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("student");
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printWriter.println("Forward... to student Page");
		}else {
			printWriter.println("Forward... to no selection Page");
		}
		
	}
}
