let FCM = require('fcm-node');
let config = require('./config');

let serverKey = config.FCMServerKey; 


let fcm = new FCM(serverKey);

let message = { 
	to: config.deviceToken, 
	

	notification: {
		title: 'Title of your push notification', 
		body: 'Body of your push notification' 
	}
};


let notificationHandler = {
	sendNotification: function(data, purpose) {
		if(purpose === "battery") {
			message.notification.title = "Battery status";
			message.notification.body = "Mac's running at " + data + '%';
			fcm.send(message, function(err, response){
				if (err) {
					console.log("Something has gone wrong!");
				} else {
					console.log("Successfully sent with response: ", response);
				}
			});
		}

		else if(purpose === "copy") {
			message.notification.title = "Copied text";
			message.notification.body = data;
			fcm.send(message, function(err, response){
				if (err) {
					console.log("Something has gone wrong!");
				} else {
					console.log("Successfully sent with response: ", response);
				}
			});
		}
	}
}

module.exports = notificationHandler;