
var app = require('express');
var router = app.Router();



router.test = function(req,res) {
	
	if(req.body.command == "battery")
	{		
		req.io.emit('battery', true);

	}

}

module.exports = router;