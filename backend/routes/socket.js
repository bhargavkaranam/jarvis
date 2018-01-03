
var app = require('express');
var router = app.Router();



router.test = function(req,res) {
	
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

	

}

module.exports = router;