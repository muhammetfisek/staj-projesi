package com.fisek.ws.email;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fisek.ws.configuration.FisekProperties;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    JavaMailSenderImpl mailSender;
@Autowired
FisekProperties fisekProperties;
    @PostConstruct
    public void initalize(){
        this.mailSender=new JavaMailSenderImpl();
        mailSender.setHost(fisekProperties.getEmail().host());
        mailSender.setPort(fisekProperties.getEmail().port());
        mailSender.setUsername(fisekProperties.getEmail().ad());
        mailSender.setPassword(fisekProperties.getEmail().sifre());
Properties properties=mailSender.getJavaMailProperties();
properties.put("mail.smtp.starttls.enable", "true");


    } 

String acitvationEmail="""
        <html>
        <body>
        <h1>Activite Account </h1>
        <a href="${url}"> Click Here </a>
        </body>
        </html>
        """;


    public void sendActivationEmail(String email,String activationToken) {
        var activationUrl=fisekProperties.getClient().host()+"/activation/"+activationToken;
        var mailBody= acitvationEmail.replace("${url}", activationUrl);

        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper message=new MimeMessageHelper(mimeMessage);
       
    try {
        message.setFrom(fisekProperties.getEmail().from());
          message.setTo(email);
    message.setSubject("Account Activation");
    message.setText(mailBody,true);
    } catch (MessagingException e) {
        
        e.printStackTrace();
    }
  
    this.mailSender.send(mimeMessage);
    }
        
}
