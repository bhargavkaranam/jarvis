# Jarvis

Something I worked on to kill time. How many times have we encountered a situation where we were frustrated that our devices just don't communicate well with each other? What if your PC automatically recognizes the OTP when you are transacting online? Or if it alerts you that its battery is about to die? Well, Jarvis is your answer.

# Features

*  Battery alerts to your mobile device(s)
*  Sleep, turnoff your computer
*  Encrypt, decrypt and read files from your mobile device(s)
*  Lost your device? Fret not. 
*  Universal clipboard
*  Get SMS delivered to your computer in real time
*  Share images instantly  

more to be added soon...

# Config files
<br />
Two config files are needed to run the project. **js/Config.js** and **backend/routes/config.js**

In **js/Config.js**, the following variables need to be declared
```javascript
let config = {
	name: '', //Your Name
	apiKeys : {
		'newsapi': '', //API key from newsapi.org
		'sms': '', //Not required now
		'weather': '' //API key from OpenWeatherMap
	},

	email: {
		'username': '', //SMTP username
		'password': '', //SMTP password
		'SELF': '' //Email from which you want to send emails
	},

	SOCKET_SERVER: '', //URL of the socket server

	CLOUD_FOLDER_NAME: '', //Folder name where you want to store your cloud files

	ENCRYPT_DECRYPT_PASSWORD: '' //Password to encypt files - needs to be the same in the android app



};
```

In **backend/routes/config.js**, the following variables need to be declared
```javascript
exports.FCMServerKey = ""; //FCM Server key

exports.deviceToken = ""; //FCM device token of your mobile

exports.password = ""; //Password for devices to authenticate themselves

exports.ENCRYPT_DECRYPT_PASSWORD = ''; //Encrypt and decrypt password for devices to authenticat
```