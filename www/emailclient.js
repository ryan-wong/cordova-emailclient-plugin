/*global cordova, module*/
module.exports = {
    retrieveEmails: function(options, successCallback, errorCallback){
    	var data = [
    	options.host,
    	options.port,
    	options.email,
    	options.password
    	];
    	cordova.exec(successCallback, errorCallback, "Emailclient", "retrieveEmails", data);
    },
    sendEmail: function(options, successCallback, errorCallback){
    	var data = [
    	options.host,
    	options.port,
    	options.email,
    	options.password,
    	options.to,
    	options.from,
    	options.fromName,
    	options.subject,
    	options.body
    	];
    	cordova.exec(successCallback, errorCallback, "Emailclient", "sendEmail", data);
    },
    retrieveEmailDetail: function(options, successCallback, errorCallback){
    	var data = [
    	options.host,
    	options.port,
    	options.email,
    	options.password,
    	options.messageId
    	];
    	cordova.exec(successCallback, errorCallback, "Emailclient", "retrieveEmailDetail", data);
    },
    deleteEmail: function(options, successCallback, errorCallback){
    	var data = [
    	options.host,
    	options.port,
    	options.email,
    	options.password,
    	options.messageId
    	];
    	cordova.exec(successCallback, errorCallback, "Emailclient", "deleteEmail", data);
    }
};
