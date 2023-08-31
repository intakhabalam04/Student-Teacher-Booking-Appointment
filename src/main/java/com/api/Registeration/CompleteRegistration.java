package com.api.Registeration;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api.JDBC.JdbcConnection;

public class CompleteRegistration {
	public static int completeRegistration(int id,String firstName,String lastName,String section,String email,String mobile,String username,String password,String gender,PrintWriter printWriter) {
		
		Connection connection= JdbcConnection.getConnection(printWriter);
		
		String insertData="INSERT INTO studentdata (Id, First_Name, Last_Name, Class, Email, Mobile_No,UserId, Password, Gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		int isInserted=0;
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(insertData);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, section);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, mobile);
			preparedStatement.setString(7, username);
			preparedStatement.setString(8, password);
			preparedStatement.setString(9, gender);
			
			isInserted= preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			printWriter.println("Error "+e.getMessage());
			e.printStackTrace();
		}
		return isInserted;
	}
}
