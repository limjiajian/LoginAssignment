package com.optimum.connection;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;

import com.optimum.pojo.User;

import javax.activation.*;  
  
public class SendEmail  
{  
 public void sendMail(User refUser){  
      String to =refUser.getLogID();//change accordingly  
      String from = "theAssignment@theoptimum.net"; 
      String host = "smtp.theoptimum.net";//or IP address  
  
     //Get the session object  
      Properties properties = System.getProperties();  
      properties.setProperty("mail.smtp.host", host);  
      properties.put("mail.smtp.port", 587);
      Session session = Session.getDefaultInstance(properties); 
      
  
     //compose the message  
      try{  
         MimeMessage message = new MimeMessage(session);  
         message.setFrom(new InternetAddress(from));  
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
         message.setSubject("You have been registered");  
         message.setText("Hello, this is your temporary password is: "+refUser.getPassword());  
  
         // Send message  
         Transport.send(message);  
         System.out.println("message sent successfully....");  
  
      }catch (MessagingException mex) {//mex.printStackTrace(); 
      System.out.println("Email not from theoptimum.net");}
   }  
}  