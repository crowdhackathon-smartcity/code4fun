var natural = require('natural');

module.exports = {
	processText: function (text, serviceList, callback) {
		//Tokenize the text to the array tokens
		var textTokens = text.split(/[-:'"?\s><]+/).filter(function (item) { return item !== '' });
		var currentMaxScore = 0;
		var currentSelectedService = 0;

		//Get the services one at a time
		for (var i = 0; i < serviceList.length; i++) {
			//Tokenize the current service title to the array currentServiceTokens
			var currentServiceTokens = serviceList[i].title.split(/[-:'"?\s><]+/).filter(function (item) { return item !== '' });
			var currentServiceCounter = 0;
			var currentServiceScore = 0;
			//Get the service title tokens one at a time
			for (var j = 0; j < currentServiceTokens.length; j++) {
				//Limit the checks to words larger than 3 characters for better results
				if (currentServiceTokens[j].length > 3) {
					var currentServiceTokenMaxScore = 0;
					currentServiceCounter++;
					//Get the text tokens one at a time
					for (var k = 0; k < textTokens.length; k++) {
						//Limit the checks to words larger than 3 characters for better results
						if (textTokens[k].length > 3) {
							//Find the JaroWinklerDistance between the two words, this will be our score
							var currentServiceTokenScore = natural.JaroWinklerDistance(textTokens[k], currentServiceTokens[j]);
							if (currentServiceTokenScore > currentServiceTokenMaxScore) {
								//We keep only the best score for each token
								currentServiceTokenMaxScore = currentServiceTokenScore;
							}
						}
					}
					//We sum each token's score
					currentServiceScore += currentServiceTokenMaxScore;
				}
			}
			//The final score for each service is the average of each
			var finalScore = currentServiceScore / currentServiceCounter;
			//We need the service with the best average score
			if (finalScore > currentMaxScore) {
				currentSelectedService = i;
				currentMaxScore = finalScore;
			}
		}
		//We return the selected service from the serviceList
		callback(null, serviceList[currentSelectedService]);
	}
}


