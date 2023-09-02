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
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("    <title>Print in Middle of Web Page</title>");
			out.println("    <style>");
			out.println("        /* Your CSS code here */");
			out.println("    </style>");
			out.println("</head>");
			out.println("<body style=\"display: flex; flex-direction: column; justify-content: center; align-items: center; min-height: 100vh; margin: 0; font-family: Arial, sans-serif;\">");
			out.println("    <div class=\"content\" style=\"padding: 20px; border: 1px solid #ccc; background-color: #f8f8f8; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); text-align: center;\">");
			out.println("        <h1>Registration Successful</h1>");
			out.println("        <p>Your registration has been completed successfully.</p>");
			out.println("    </div>");
			out.println("</body>");


			out.close();

			SendRegistrationEmail.sendEmail(email, username, password);
//			RequestDispatcher requestDispatcher=request.getRequestDispatcher("index.html");
//			requestDispatcher.forward(request, response);
			
		} else {
			printWriter.println("Failed");
		}
	}
}
