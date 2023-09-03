package com.api.Forget;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.api.JDBC.*;
import com.api.Email.*;
import com.api.Forget.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SendOtp extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Random random = new Random();

        PrintWriter printWriter = response.getWriter();
        String email = request.getParameter("femail");
        Connection connection = JdbcConnection.getConnection(printWriter);
        int id = 0;
        boolean isValid = false;
        ResultSet resultSet = null;
        if (connection != null) {
            String fetchQuery = "SELECT Id FROM studentdata where Email=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(fetchQuery);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt("Id");
                    isValid = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (isValid) {

                int otp = random.nextInt(1000, 10000);
                String insertOtp = "UPDATE studentdata SET OTP=? WHERE Id=?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertOtp);
                    preparedStatement.setInt(1, otp);
                    preparedStatement.setInt(2, id);
                    int rowAffected = preparedStatement.executeUpdate();
                    
                    SendOtpMail.sendOtpMail(email, otp);
                
                    RequestDispatcher htmlDispatcher = request.getRequestDispatcher("otp.html");
                    htmlDispatcher.include(request, response);

                } catch (SQLException e) {
                	                	
                    e.printStackTrace();
                }

            } else {
                printWriter.println("Not registered yet");
            }
        }

    }
}
