
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

		else if(req.body.command === "camera")
		{
			req.io.emit('camera', true);
			res.end("I'll notify you soon.");
		}
	}
	else
		res.end('Validation failed');

	

}

module.exports = router;