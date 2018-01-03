
var app = require('express');
var router = app.Router();



router.test = function(req,res) {

	if(req.body.command == "battery")
	{		
		req.io.emit('battery', true);
		res.json({status: true, message: "I'll notify you the battery percentage soon."})

	}

	

}

module.exports = router;