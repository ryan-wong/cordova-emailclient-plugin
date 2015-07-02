package com.sixtooth.plugin;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by sixtooth on 15-06-30.
 */
public class AndroidEmailSender {
    private String host = "";
    private int port = 587;
    private String username;
    private String password;

    private Session session = null;

    /**
     * AndroidEmailSender constructor. Upon creating the sender object, it will also create a new
     * Session.
     * @param host  the SMTP email server address
     * @param port  the SMTP email server port
     * @param user  the login user name
     * @param pass  the login password
     */

    public AndroidEmailSender(String host, String port, String user, String pass){
        this.host = host;
        this.port = Integer.parseInt(port);

        this.username = user;
        this.password = pass;

        session = createSessionObject(host, this.port, user, pass );

    }


    /**
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    private Session createSessionObject(String host, int port, final String username, final String password) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);// port

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }


    /**
     *
     * @param toEmailAddress the receipant email address
     * @param subject the sending email subject
     * @param messageBody the sending email message Body
     */
    public String sendMail(String fromEmail, String fromName, String toEmailAddress, String subject, String messageBody) {
        try {
            if(session == null) return "{\"message\":\"Email or password is incorrect\"}";
            Message message = createMessage(fromEmail, fromName, toEmailAddress, subject, messageBody);
            Transport.send(message);
            return "{\"error\": false, \"message\":\"Email Send Successfully\"}";
        } catch(javax.mail.AuthenticationFailedException e){
            return "{\"error\": true, \"message\":\"Email or password is incorrect\"}";
        } catch (AddressException e) {
            return "{\"error\": true, \"message\":\"" + e.getMessage() + "\"}";
        } catch (javax.mail.MessagingException e) {
            return "{\"error\": true, \"message\":\"" + e.getMessage() + "\"}";
        } catch (UnsupportedEncodingException e) {
            return "{\"error\": true, \"message\":\"" + e.getMessage() + "\"}";
        }catch (Exception e) {
            return "{\"error\": true, \"message\":\"" + e.getMessage() + "\"}";
        }
    }

    /**
     *
     * @param fromEmail
     * @param fromName
     * @param toEmail
     * @param subject
     * @param messageBody
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private Message createMessage(String fromEmail, String fromName, String toEmail, String subject, String messageBody) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail, fromName));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toEmail));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }
}

