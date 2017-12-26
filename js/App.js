


function getBatteryPercentage()
{
	navigator.getBattery().then(function(battery) {

		var level = battery.level;

		if(level <= 0.15)
		{

			api.sendEmail(config.email.SELF, "Charge your Mac!", "Your Mac's battery is running at " + (level * 100) + "%");
			
			
		}
	})
}

function getNews()
{

	api.makeRequest(api.endPoints.newsapi.hindu, "get", "", function(data){
		let total = data.totalResults;
		let count = 0;
		$.each(data.articles, function(k,v){
			$(".cards").append('<div class="col s12 m12"><div class="card horizontal"><div class="card-image"><img src="https://lorempixel.com/100/190/nature/6"></div><div class="card-stacked"><div class="card-content"><p>' + v.title + '</p></div><div class="card-action"><a href="' + v.url + '">View</a></div></div></div></div>');
			// if(count == 2)
			// 	return false;
			// count++;
		})
	})
}

getNews();

setInterval(getBatteryPercentage, 20000);