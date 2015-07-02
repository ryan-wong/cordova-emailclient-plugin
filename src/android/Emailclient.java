package com.sixtooth.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.sixtooth.plugin.AndroidEmailReceiver;
import com.sixtooth.plugin.AndroidEmailSender;

public class Emailclient extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("retrieveEmails")){
            String host = data.getString(0);
            String port = data.getString(1);
            String email = data.getString(2);
            String password = data.getString(3);
            AndroidEmailReceiver emailObject = new AndroidEmailReceiver(host,port,email,password);
            String json = emailObject.receiveEmail();
            callbackContext.success(json);
            return true;
        }  else if (action.equals("sendEmail")){
            String host = data.getString(0);
            String port = data.getString(1);
            String email = data.getString(2);
            String password = data.getString(3);
            String to = data.getString(4);
            String from = data.getString(5);
            String fromName = data.getString(6);
            String subject = data.getString(7);
            String body = data.getString(8);
            AndroidEmailSender emailObject = new AndroidEmailSender(host,port,email,password);
            String json = emailObject.sendMail(from, fromName, to, subject, body);
            callbackContext.success(json);
            return true;
        } else if (action.equals("retrieveEmailDetail")){
            String host = data.getString(0);
            String port = data.getString(1);
            String email = data.getString(2);
            String password = data.getString(3);
            String messageId = data.getString(4);
            AndroidEmailReceiver emailObject = new AndroidEmailReceiver(host,port,email,password);
            emailObject.receiveEmail();
            String json = emailObject.getDetailEmailJsonByMessageID(messageId);
            callbackContext.success(json);
            return true;
        } else if (action.equals("deleteEmail")){
            String host = data.getString(0);
            String port = data.getString(1);
            String email = data.getString(2);
            String password = data.getString(3);
            String messageId = data.getString(4);
            AndroidEmailReceiver emailObject = new AndroidEmailReceiver(host,port,email,password);
            String json = emailObject.deleteMessage(messageId);
            callbackContext.success(json);
            return true;
        } else {
            return false;

        }
    }
}
