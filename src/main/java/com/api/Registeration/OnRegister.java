package com.api.Registeration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import com.api.Email.SendRegistrationEmail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OnRegister extends HttpServlet {
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
		
		response.setContentType("text/html");
		
		PrintWriter printWriter=response.getWriter();
		
		
		Random random=new Random();
		int id=random.nextInt(1000);
		
		String firstName=request.getParameter("fname");
		String lastName=request.getParameter("lname");
		String section=request.getParameter("class"); 
		String email=request.getParameter("email");
		String mobile=request.getParameter("phoneNumber");
		String password=request.getParameter("password");
		String gender=request.getParameter("gender");
		String username=firstName.replace(" ", "_")+id;
		
		int isInserted = CompleteRegistration.completeRegistration(id, firstName, lastName, section, email, mobile,username, password, gender, printWriter);
		
		if (isInserted!=0) {
			printWriter.println("Success");	
			SendRegistrationEmail.sendEmail(email, username, password);
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("index.html");
			requestDispatcher.forward(request, response);
			
		} else {
			printWriter.println("Failed");
		}
	}
}
