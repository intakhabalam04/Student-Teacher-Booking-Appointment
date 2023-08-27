package com.api.Authorization;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api.JDBC.JdbcConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Admin extends HttpServlet {
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
		
		response.setContentType("text/html");
		
		PrintWriter printWriter=response.getWriter();		
		
		Connection connection=JdbcConnection.getConnection(printWriter);
		
		String userName=request.getParameter("uname");
		String password=request.getParameter("upass");
		
		String databaseUserName=null;
		String databasePassword=null;
		
		String query ="SELECT * FROM adminlogin WHERE USERID=?";
		
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		boolean isValidUser=false;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				databaseUserName=resultSet.getString(2);
				databasePassword=resultSet.getString(3);
				isValidUser=true;
			}
			
		} catch (SQLException e) {
			printWriter.println("Error "+e.getMessage());
		}
		if(isValidUser) {
			if(databasePassword.equals(password) && databaseUserName.equals(userName)) {
				printWriter.println("Successfully login");
			}else {
//				printWriter.println("Invalid detais");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("index.html");
				requestDispatcher.include(request, response);
			}
		}else {
//			printWriter.println("Invalid detais");
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("index.html");
			requestDispatcher.include(request, response);
		}
		
		
	}
}
