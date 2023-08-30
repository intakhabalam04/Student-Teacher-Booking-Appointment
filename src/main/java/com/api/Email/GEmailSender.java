package com.api.Email;

import java.util.*;

import jakarta.mail.*;
import jakarta.mail.internet.*;

class GEmailSender {
    public static boolean gEmailSender(String to,String from,String subject,String text){
        boolean flag=false;

        Properties properties=new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","smtp.gmail.com");

        final String user="Teaminnovate.api@gmail.com";
        final String password="laorfiulnbeqxeot";
        
        Session session=null;
        
        

        session= Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,password);
            }
        });
        

        try{
            Message message=new MimeMessage(session);

            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            flag=true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return flag;
    }
}
