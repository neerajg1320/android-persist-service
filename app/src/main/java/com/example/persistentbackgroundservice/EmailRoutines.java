package com.example.persistentbackgroundservice;

import android.util.Log;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


// Ref: https://www.youtube.com/watch?v=JQRcT_m4tsA
public class EmailRoutines {
    static final String TAG = "EmailRoutines";

    public static void send_email(String senderEmail,
                                  ArrayList<String> receiverEmails,
                                  String passwordSenderEmail,
                                  String subject,
                                  String text,
                                  String gmailHost) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", gmailHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, passwordSenderEmail);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            for (String receiverEmail: receiverEmails) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            }
            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);

            Log.i(TAG, "Sending the message.");
            send_message_using_thread(mimeMessage);
            // send_message(mimeMessage);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void send_message(MimeMessage mimeMessage) {
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void send_message_using_thread(MimeMessage mimeMessage) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Transport.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });

        Log.i(TAG, "Thread started");
        thread.start();
    }

    public static void send_email_using_gmail(String subject, String text, ArrayList<String> receiverEmails) {
        Log.i(TAG, "Got email request");

        String senderEmail = "neerajgupta.finance@gmail.com";

        String passwordSenderEmail = "chpdpemnebqfeoss";
        String gmailHost = "smtp.gmail.com";

        send_email(senderEmail, receiverEmails, passwordSenderEmail, subject, text, gmailHost);
    }
}
