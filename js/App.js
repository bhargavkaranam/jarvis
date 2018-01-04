let socket = io('http://localhost:3000');


socket.on('battery', function(data){
	
	getBatteryPercentage(false).then(function(percentage){
		socket.emit('battery', percentage);
	});
})

socket.on('sleep', function(data){
	let shell = require('shelljs');
	
	shell.exec('pmset sleepnow', function(code, stdout, stderr){
		socket.emit('sleep', true);
	})
  
		
	
})

socket.on('file', function(data){
	let fs = require('fs');
	let path = require('path');
	
	let content = fs.readFileSync(path.join(global.__dirname, 'cloud', data));
	alert(content);
	api.sendEmail(config.email.SELF, "File content", content.toString());
})

function getBatteryPercentage(sendEmail)
{
	return navigator.getBattery().then(function(battery) {

		var level = battery.level;

		if(sendEmail)
		{

			if(level <= 0.15)
			{

				api.sendEmail(config.email.SELF, "Charge your Mac!", "Your Mac's battery is running at " + (level * 100) + "%");
				
				
			}
		}
		else
			return (level * 100);
	})
}

function getNews()
{

	api.makeRequest(api.endPoints.newsapi.hindu, "get", "", function(data){
		let total = data.totalResults;
		let count = 0;
		$.each(data.articles, function(k,v){
			$(".cards").append('<div class="col s12 m12"><div class="card horizontal"><div class="card-image"><img src="https://lorempixel.com/100/190/nature/6"></div><div class="card-stacked"><div class="card-content"><p>' + v.title + '</p></div><div class="card-action"><a href="' + v.url + '">View</a></div></div></div></div>');
			
		})
	})
}

getNews();

setInterval(function(){
	getBatteryPercentage(true);
}, 600000);

