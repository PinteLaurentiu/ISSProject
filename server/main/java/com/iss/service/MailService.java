package com.iss.service;

import com.iss.ApplicationConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailService implements IService {
    private void sendActivationMailRun(String to, Integer id, String uuid) {
        final String username = "noreply.donare.sange@gmail.com";
        final String password = "sangedonare";
        final String from = "noreply.donare.sange@gmail.com";
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
        props.put("mail.smtp.socketFactory.class", config.getSSLSocketFactoryClass());

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
    private void createThreadAndWait(String to, Integer id, String uuid) {
        try {
            Thread thread = new Thread(() -> sendActivationMailRun(to, id, uuid));
            thread.start();
            for (int i = 0; i < 30/*TODO*/; i++) {
                if (!thread.isAlive())
                    break;
                    Thread.sleep(1000);
            }
            if (!thread.isAlive())
                //noinspection deprecation
                thread.stop();
        } catch (Exception ignored) {}
    }
    public void sendActivationMail(String to, Integer id, String uuid) {
        new Thread(()->createThreadAndWait(to, id, uuid)).start();
    }
}
