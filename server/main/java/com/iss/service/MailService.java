package com.iss.service;

import com.iss.ApplicationConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailService implements IService {

    public void sendActivationMail(String to, Integer id,String uuid) {
        final String username = "truewolf10@gmail.com";
        final String password = "741852963marcusg";
        final String from = "truewolf10@gmail.com";
        ApplicationConfiguration config = null;
        try {
            config = ApplicationConfiguration.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        assert config != null;
        props.put("mail.smtp.auth", config.getSmtpAuth());
        props.put("mail.smtp.starttls.enable", config.getSmtpStartle());
        props.put("mail.smtp.host", config.getSmtpHost());
        props.put("mail.smtp.port", config.getSmtpPort());
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");

        String sb = "<h2>Confirm Registration</h2>\n" +
                "<p><a href=\""+ config.getHost() +"/activate?id=" + id + "&uid=" + uuid;
        sb += "\">Click it to confirm the registration. </a></p>";

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setContent(sb,"text/html");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
