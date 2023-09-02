package com.api.Email;

import java.util.Random;

public class SendOtpMail {
	public static void sendOtpMail(String to,int otp) {
	    	
	
	        String from="teaminnovate.api@gmail.com";
	        String subject="OTP";
	        String text="OTP : "+otp;
	        boolean isSend= GEmailSender.gEmailSender(to,from,subject,text);
	    }
	}
