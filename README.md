# Cordova Email Client Plugin

Simple plugin that lets you retrieve your emails, view email details, send emails and delete emails.

## Using
Clone the plugin

    $ git clone https://github.com/ryan-wong/cordova-emailclient-plugin.git

Create a new Cordova Project

    $ cordova create emailclient com.example.emailclient EmailClientDemo

Install the plugin

    $ cd emailclient
    $ cordova plugin install ../cordova-emailclient-plugin
    $ cordova platform add android
    $ cordova run android --device

##API

###Retrieving Emails
This query lets you query your smtp server for all emails excluding the body portion

Parameters:
- host your smtp host name
- port your smtp port
- email your email address
- password your email password

```javascript
emailclient.retrieveEmails({
    host: '**************',
    port: '993',
    email: '******@gmail.com',
    password: '**********'
}, function(data){
    console.log('data', JSON.parse(data));
}, function(){
    console.log('failed');
});
```
<br/>
Response:
```json
{
  "isRead": true,
  "hasContainAttachment": true,
  "replySign": false,
  "from": "*******@gmail.com",
  "to": "****@gmail.com",
  "cc": "",
  "bcc": "",
  "sendDate": "2014-11-26 16:15",
  "messageId": "<73559F54-00B5-4567-864F-E3BC5C31EEC9@gmail.com>",
  "title": "Title",
  "content": ""
}
```

###Retrieve Email Detail
This query lets you get the full email details given a messageId

Parameters:
- host your smtp host name
- port your smtp port
- email your email address
- password your email password
- messageId the message id

```javascript
emailclient.retrieveEmailDetail({
    host: '**************',
    port: '993',
    email: '******@gmail.com',
    password: '**********'
    messageId: "<73559F54-00B5-4567-864F-E3BC5C31EEC9@gmail.com>"
}, function(data){
    console.log('data', JSON.parse(data));
}, function(){
    console.log('failed');
});
```
<br/>
Response:
```json
{
  "isRead": true,
  "hasContainAttachment": true,
  "replySign": false,
  "from": "*******@gmail.com",
  "to": "****@gmail.com",
  "cc": "",
  "bcc": "",
  "sendDate": "2014-11-26 16:15",
  "messageId": "<73559F54-00B5-4567-864F-E3BC5C31EEC9@gmail.com>",
  "title": "Title",
  "content": "Blah"
}
```

###Send Email
This query lets you send an email from your smtp server

Parameters:
- host your smtp host name
- port your smtp port
- email your email address
- password your email password
- messageId the message id

```javascript
emailclient.sendEmail({
    host: '**************',
    port: '993',
    email: '******@gmail.com',
    password: '**********'
    from: '*****@gmail.com',
    fromName: 'User',
    subject: 'Send Test Email',
    body: 'Heres a test email'
}, function(data){
    console.log('data', JSON.parse(data));
}, function(){
    console.log('failed');
});
```
<br/>
Response:
```json
{
  "error": false,
  "message": "Message not found"
}
```


###Delete Email
This query lets you get delete an email given a messageId

Parameters:
- host your smtp host name
- port your smtp port
- email your email address
- password your email password
- messageId the message id

```javascript
emailclient.deleteEmail({
    host: '**************',
    port: '993',
    email: '******@gmail.com',
    password: '**********'
    messageId: "<73559F54-00B5-4567-864F-E3BC5C31EEC9@gmail.com>"
}, function(data){
    console.log('data', JSON.parse(data));
}, function(){
    console.log('failed');
});
```
<br/>
Response:
```json
{"error":false, "message":"Email Deleted"}
```

