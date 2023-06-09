package com.subscribe.mainp.service;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
@Service
public class EmailService {

    public Integer sendMail(String to)
    {
        //Variable for gmail
        String host="smtp.gmail.com";
        String from = "tvseeshow@gmail.com";
        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES "+properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //Step 1: to get the session object..
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tvseeshow@gmail.com", "glzpdfsopbkbkbat");
            }



        });

        session.setDebug(true);

        //Step 2 : compose the message [text,multi media]
        MimeMessage m = new MimeMessage(session);
        String subject = "SEESHOW OTP FOR PASSWORD RESET";
        Random random = new Random();
        int otp = random.nextInt(999999);
        String message = "Your SeeShow One Time Password is:"+otp;
        try {

            //from email
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);


            //adding text to message
            m.setText(message);

            //send

            //Step 3 : send the message using Transport class
            Transport.send(m);

            System.out.println("Sent successfully");

        }catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            return otp;
        }
    }
}
