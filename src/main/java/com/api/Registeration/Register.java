package com.api.Registeration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import com.api.JDBC.JdbcConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {
	public void service(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
		
		PrintWriter printWriter=response.getWriter();
		
//		response.setContentType("text/html");
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("register.html");
		requestDispatcher.include(request, response);
		
	}
}
