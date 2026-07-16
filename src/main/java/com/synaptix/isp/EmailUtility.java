package com.synaptix.isp;

import java.io.InputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailUtility {
    private static Properties mailProps = new Properties();
    private static String smtpUser = "";
    private static String smtpPassword = "";
    private static boolean isConfigured = false;

    static {
        try (InputStream input = EmailUtility.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                
                smtpUser = prop.getProperty("mail.smtp.username", "").trim();
                smtpPassword = prop.getProperty("mail.smtp.password", "").trim();
                
                if (!smtpUser.isEmpty() && !smtpPassword.isEmpty()) {
                    mailProps.put("mail.smtp.host", prop.getProperty("mail.smtp.host", "smtp.gmail.com"));
                    mailProps.put("mail.smtp.port", prop.getProperty("mail.smtp.port", "587"));
                    mailProps.put("mail.smtp.auth", prop.getProperty("mail.smtp.auth", "true"));
                    mailProps.put("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable", "true"));
                    isConfigured = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isSmtpConfigured() {
        return isConfigured;
    }

    public static void sendEmail(String recipient, String subject, String body, java.io.File attachment) throws Exception {
        if (!isConfigured) {
            System.out.println("====== OFFLINE MAIL EMULATION ======");
            System.out.println("To: " + recipient);
            System.out.println("Subject: " + subject);
            System.out.println("Body:\n" + body);
            if (attachment != null) {
                System.out.println("Attachment: " + attachment.getAbsolutePath() + " (" + attachment.length() + " bytes)");
            }
            System.out.println("====================================");
            return;
        }

        Session session = Session.getInstance(mailProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(smtpUser));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if (attachment != null && attachment.exists()) {
            MimeBodyPart attachPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            attachPart.setDataHandler(new DataHandler(source));
            attachPart.setFileName(attachment.getName());
            multipart.addBodyPart(attachPart);
        }

        message.setContent(multipart);
        Transport.send(message);
    }
}
