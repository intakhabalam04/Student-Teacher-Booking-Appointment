package com.api.Email;

import java.util.Random;
import jakarta.mail.Authenticator;
import jakarta.mail.*;

public class SendRegistrationEmail
{
    public static void sendEmail(String to,String username,String password) {
    	
        Random random=new Random();
        int rand=random.nextInt(10000);

        String from="teaminnovate.api@gmail.com";
        String subject="Registration Details";
        String text="Thanks for Registration Your login detais is given below\nUsername : "+username+"\nPassword : "+password;
        boolean isSend= GEmailSender.gEmailSender(to,from,subject,text);
    }
}