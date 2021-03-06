let api = {

	endPoints : {
		'newsapi': {
			'hindu': 'https://newsapi.org/v2/top-headlines?sources=the-hindu&apiKey=' + config.apiKeys.newsapi
		},
		'location': 'http://freegeoip.net/json',
		'weather': 'http://api.openweathermap.org/data/2.5/weather?units=metric&lat='
	},

	makeRequest : function(url, type, data, callback) {
		$.ajax({
			url: url,
			type: type,
			data: '' + data,
			dataType: 'json',
			success: function(data) {
				
				return callback(data)
			},
			error: function(a,b,c) {
				alert(c);
			}
		})
	},

	sendEmail: function(to, subject, body) {
		Email.send("bhargav.karanam@gmail.com",
			to,
			subject,
			body,
			"smtp.gmail.com",
			config.email.username,
			config.email.password);
	},

	sendEmailWithAttachment: function(to,subject,body,dataURI) {
		Email.sendWithAttachment(
			"bhargav.karanam@gmail.com",
			to,
			subject,
			body,
			"smtp.gmail.com",
			config.email.username,
			config.email.password,
			dataURI);
	}


}