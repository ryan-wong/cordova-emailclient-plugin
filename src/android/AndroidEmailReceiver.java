package com.sixtooth.plugin;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sixtooth.plugin.Email;
import com.sixtooth.plugin.RecieveMail;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import java.util.ArrayList;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
/**
 * Created by sixtooth on 15-06-30.
 */
public class AndroidEmailReceiver {

    private  Session session = null;
    private String host = "";
    private  int port = 993;
    private  String user = "";
    private  String password = "";
    private Gson gson;

    private String jsonResult;

    private ArrayList<Email> emailList;

    public String getJsonResult() {
        return jsonResult;
    }

    public AndroidEmailReceiver(String host, String port, String user, String password){
        this.host = host;
        this.port = Integer.parseInt(port);
        this.user = user;
        this.password = password;
        this.session = createSession();
        this.gson = new Gson();
        this.emailList = new ArrayList<Email>();

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();

        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);

    }


    public String receiveEmail(){
        if(emailList != null) emailList.clear();
        String errorMsg;
        try {
            jsonResult = getImapEmail1();
            return jsonResult;
        }catch (Exception e){
            errorMsg = e.getMessage();
            return "{\"message\":\"" + errorMsg + "\"}";
        }
    }


    private Session createSession() {
        Properties prop = new Properties();
        prop.put("mail.store.protocol", "imaps");
        prop.put("mail.imap.ssl.enable", "true");


        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(user, password);
            }
        });

        return session;
    }


    public String getDetailEmailJsonByMessageID(String messageId){

        for(Email mail: emailList){
            if(mail.getMessageId().equals(messageId)){
                return gson.toJson(mail, Email.class);
            }
        }
        return "{\"message\":\"Message not found\"}";
    }

    public String deleteMessage(String messageId){
        String notFound = "{\"error\":true, \"message\":\"Message not found\"}";
        if(host == null || port == 0 || user == null || password == null)
            return "{\"error\":true, \"message\":\"Invalid server setting\"}";

        ArrayList<Email> emailSimpleList = new ArrayList<Email>();

        IMAPStore store;
        try {
            store = (IMAPStore) session.getStore();
            store.connect(host, user, password);
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();

            if (messages.length > 0) {
                RecieveMail pmm = null;
                for (int i = 0; i < messages.length; i++) {
                    pmm = new RecieveMail((MimeMessage) messages[i]);
                    if (pmm.getMessageId().equals(messageId)){
                        messages[i].setFlag(Flags.Flag.DELETED, true);
                        folder.close(true);
                        return "{\"error\":false, \"message\":\"Email Deleted\"}";
                    }
                    pmm = null;
                }
            }else{
                return notFound;
            }
        }catch (javax.mail.AuthenticationFailedException e){
                return "{\"error\":true, \"message\":\"Wrong email or password\"}";
        } catch (javax.mail.NoSuchProviderException e) {
            return "{\"error\":true, \"message\":\"Mailing Server is down\"}";
        } catch (MessagingException e) {
            return "{\"error\":true, \"message\":\"Mailing Server is down\"}";
        } catch (Exception e){
            return notFound;
        }
            return notFound;
    }

    private String getImapEmail1() {
        if(host == null || port == 0 || user == null || password == null)
            return "{\"message\":\"Invalid server setting\"}";
        ArrayList<Email> emailSimpleList = new ArrayList<Email>();

        IMAPStore store;
        try {
            store = (IMAPStore) session.getStore(); // ??imap??????????
            store.connect(host, user, password);
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // ???
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();

            if (messages.length > 0) {
                RecieveMail pmm = null;
                for (int i = 0; i < messages.length; i++) {
                    try {
                        Email email = new Email();
                        pmm = new RecieveMail((MimeMessage) messages[i]);

                        Flags flags = messages[i].getFlags();

                        email.setIsRead(flags.contains(Flags.Flag.SEEN));

                        email.setHasContainAttachment(pmm.isContainAttach((Part) messages[i]));
                        email.setFrom(pmm.getFrom());
                        email.setTo(pmm.getMailAddress("to"));
                        email.setCc(pmm.getMailAddress("cc"));
                        email.setBcc(pmm.getMailAddress("bcc"));

                        pmm.setDateFormat("yyyy-MM-dd HH:mm");
                        email.setSendDate(pmm.getSentDate());
                        email.setMessageId(pmm.getMessageId());

                        email.setTitle(pmm.getSubject());
                        pmm.getMailContent((Part) messages[i]);
                        // email.setContent(pmm.getBodyText());
                        email.setContent("");
//                        String file_path = File.separator + "mnt"
//                                + File.separator + "sdcard" + File.separator;
//
//                        System.out.println( file_path);
//
//                        pmm.setAttachPath(file_path);
//                        pmm.saveAttachMent((Part) messages[i]);
                        emailSimpleList.add(email);
                        Email detailEmail = createDetailEmail(email, pmm.getBodyText());
                        emailList.add(detailEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (javax.mail.AuthenticationFailedException e){
                return "{\"message\":\"Wrong email or password\"}";
        } catch (javax.mail.NoSuchProviderException e) {
            return "{\"message\":\"Mailing Server is down\"}";
        } catch (MessagingException e) {
            return "{\"message\":\"Mailing Server is down\"}";
        }

        String ret;

        TypeToken<ArrayList<Email>> token = new TypeToken<ArrayList<Email>>() {};

        ret = gson.toJson(emailSimpleList, token.getType());
        return ret;

    }

    private Email createDetailEmail(Email email, String body) {

        Email newEmail = new Email(
                email.isRead(),
                email.isHasContainAttachment(),
                email.isReplySign(),
                email.getFrom(),
                email.getTo(),
                email.getCc(),
                email.getBcc(),
                email.getSendDate(),
                email.getMessageId(),
                email.getTitle(),
                body

        );

        return newEmail;
    }
}
