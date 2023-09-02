package com.api.Forget;

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

public class OTPVerify extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter printWriter=response.getWriter();
		
		Connection connection=JdbcConnection.getConnection(printWriter);
		
		String email =  request.getParameter("fEmail");

		int userOtp = Integer.parseInt(request.getParameter("otp"));

		String newPassword=request.getParameter("fpassword");
		String selectQuery="SELECT OTP FROM studentdata WHERE Email=?"; 
		
		int otp=0;
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, email);
			ResultSet resultSet= preparedStatement.executeQuery();
			if(resultSet.next()) {
				otp=resultSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(otp==userOtp) {
			
			
			String insertQuery="UPDATE studentdata SET Password=? WHERE Email=?";
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(insertQuery);
				preparedStatement.setString(1, newPassword);
				preparedStatement.setString(2, email);
				
				int rowAffected=preparedStatement.executeUpdate();
				if(rowAffected!=0) {
					printWriter.println("Success");
				}else {
					printWriter.println("Error");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			printWriter.println("Wrong otp");
		}
		
	}
}
