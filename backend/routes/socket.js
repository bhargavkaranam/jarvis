
var app = require('express');
var router = app.Router();



router.test = function(req,res) {
	console.log(req.body);
	if(req.body.command == "battery")
	{		
		req.io.emit('battery', true);
		res.end("I'll notify you soon.");

	}

	

}

module.exports = router;