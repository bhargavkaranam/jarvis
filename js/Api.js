let api = {

	endPoints : {
		'newsapi': {
			'hindu': 'https://newsapi.org/v2/top-headlines?sources=the-hindu&apiKey=' + config.apiKeys.newsapi
		}
	},

	makeRequest : function(url, type, data, callback) {
		$.ajax({
			url: url,
			type: type,
			data: data,
			dataType: 'json',
			success: function(data) {
				return callback(data)
			}
		})
	}
}