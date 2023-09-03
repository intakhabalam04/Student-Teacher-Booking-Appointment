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

public class Student extends HttpServlet {
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
		
		response.setContentType("text/html");
		
		PrintWriter printWriter=response.getWriter();		
		
		Connection connection=JdbcConnection.getConnection(printWriter);
		
		String userName=request.getParameter("uname");
		String password=request.getParameter("upass");
		
		String databaseUserName=null;
		String databasePassword=null;
		String firstName=null;
		String lastName=null;
		
		String query ="SELECT * FROM studentdata WHERE USERID=?";
		
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		boolean isValidUser=false;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				databaseUserName=resultSet.getString("UserId");
				databasePassword=resultSet.getString("Password");
				firstName=resultSet.getString("First_Name");
				lastName=resultSet.getString("Last_Name");
				isValidUser=true;
			}
			
		} catch (SQLException e) {
			printWriter.println("Error "+e.getMessage());
		}
		
		String name=firstName+" "+lastName;
		
		if(isValidUser) {
			if(databasePassword.equals(password) && databaseUserName.equals(userName)) {
				printWriter.println("<html>");
				printWriter.println("<head>");
				printWriter.println("<style>");
				printWriter.println("  body {");
				printWriter.println("    display: flex;");
				printWriter.println("    justify-content: center;");
				printWriter.println("    align-items: center;");
				printWriter.println("    height: 100vh;");
				printWriter.println("    font-size: 24px;");
				printWriter.println("    font-weight: bold;");
				printWriter.println("  }");
				printWriter.println("</style>");
				printWriter.println("</head>");
				printWriter.println("<body>");
				printWriter.println("Welcome " + name + "<br>");
				printWriter.println("Work in progress: \uD83D\uDEA7"); // "Work in Progress" emoji
				printWriter.println("</body>");
				printWriter.println("</html>");



				
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
