
var app = require('express');
var router = app.Router();
let config = require('./config');


router.test = function(req,res) {
	
	if(req.body.password === config.password)
	{
		if(req.body.command == "battery")
		{		
			req.io.emit('battery', true);
			res.end("I'll notify you soon.");

		}
		else if(req.body.command === "sleep")
		{
			req.io.emit('sleep', true);
			res.end("I'll notify you soon.");
		}
		else if(req.body.command.indexOf("read") !== -1)
		{
			if(req.body.decryptPassword === config.ENCRYPT_DECRYPT_PASSWORD)
			{

				req.io.emit('file', req.body.command.split(' ')[1]);
				res.end("I'll notify you soon");
			}
			else
				res.end("Invalid decryption key, mate.");
		}

		else if(req.body.command === "lost")
		{
			req.io.emit('lost', true);
			res.end("I'll notify you soon.");
		}
		else if(req.body.command === "encrypt")
		{
			if(req.body.decryptPassword === config.ENCRYPT_DECRYPT_PASSWORD)
			{

				req.io.emit('encrypt', true);
				res.end("All the files will be encrypted.");
			}
			else
				res.end("Invalid decryption key, mate.");
		}
		else if(req.body.command === "copy")
		{
			req.io.emit('copy', true);
			res.end("I'll notify you soon.");
		}
	}
	else
		res.end('Validation failed');

	

}


router.newNotification = function(req,res) {

	let data = {
		'text': req.body.text,
		'package': req.body.package,
		'title': req.body.title
	};

	req.io.emit('NEW_NOTIFICATION', data);
	res.end("Notified.");
}

router.image = function(req,res) {

	req.io.emit('NEW_IMAGE', req.body.image);
	res.end('Image will be stored on your device.');

}


module.exports = router;