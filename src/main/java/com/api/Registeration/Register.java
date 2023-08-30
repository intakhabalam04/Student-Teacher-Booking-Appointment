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
		
		response.setContentType("text/html");
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("register.html");
		requestDispatcher.include(request, response);
		Random random=new Random();
		int id=random.nextInt(1000);
		
		String firstName=request.getParameter("fname");
		String lastName=request.getParameter("lname");
		String section=request.getParameter("class"); 
		String email=request.getParameter("email");
		String mobile=request.getParameter("phoneNumber");
		String password=request.getParameter("password");
		String gender=request.getParameter("gender");
		
		Connection connection= JdbcConnection.getConnection(printWriter);
		
		String insertData="INSERT INTO studentdata (Id, First_Name, Last_Name, Class, Email, Mobile_No, Password, Gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		int isInserted=0;
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(insertData);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, section);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, mobile);
			preparedStatement.setString(7, password);
			preparedStatement.setString(8, gender);
			
			isInserted= preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			printWriter.println("Error "+e.getMessage());
			e.printStackTrace();
		}	
		
		if (isInserted!=0) {
			printWriter.println("success");
		}else {
			printWriter.println("failed");
		}
	}
}
