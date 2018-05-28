package com.iss.service;

import com.iss.ApplicationConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailService implements IService {
    private void sendActivationMailRun(String to, Integer id, String uuid) {
        ApplicationConfiguration config = getApplicationConfiguration();
        if (config == null) return;
        final String username = config.getMailSender();
        final String password = config.getMailPass();
        final String from = config.getMailSender();

        Properties props = new Properties();
        props.put("mail.smtp.auth", config.getSmtpAuth());
        props.put("mail.smtp.starttls.enable", config.getSmtpStartle());
        props.put("mail.smtp.host", config.getSmtpHost());
        props.put("mail.smtp.port", config.getSmtpPort());
        props.put("mail.smtp.socketFactory.class", config.getSSLSocketFactoryClass());
        String messageEmail = String.format(config.getMailContentFormat(), config.getHost(),
                id, uuid);

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
            message.setSubject(config.getMailSubject());
            message.setContent(messageEmail,"text/html");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private ApplicationConfiguration getApplicationConfiguration() {
        ApplicationConfiguration config = null;
        try {
            config = ApplicationConfiguration.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (config == null)
            return null;
        return config;
    }

    private void createThreadAndWait(String to, Integer id, String uuid) {
        ApplicationConfiguration config = getApplicationConfiguration();
        try {
            Thread thread = new Thread(() -> sendActivationMailRun(to, id, uuid));
            thread.start();
            if (config == null) return;
            for (int i = 0; i < config.getMailTimeout(); i++) {
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
