var natural = require('natural');
var serviceApi = require('./services.js');

module.exports = {
	processText: function(text, callback) {
		serviceApi.getListOfServices(function(err, serviceList) {		
		var tokens = text.split(/[-:'"?\s><]+/).filter(function(item) { return item !== '' });
		var i, j, k;
		var tempMax = 0;
		var tempToken = 0;

		for(i=0; i<serviceList.length; i++) {
			var serviceTokens = serviceList[i].title.split(/[-:'"?\s><]+/).filter(function(item) { return item !== '' });
			var counter = 0;
			var currentTotal = 0;
			for(j=0; j<serviceTokens.length; j++) {
				if(serviceTokens[j].length>4) {
					var maxCurrentScore = 0;
					counter++;
					for(k=0; k<tokens.length; k++) {
						if(tokens[k].length>4) {
							var currentScore = natural.JaroWinklerDistance(tokens[k],serviceTokens[j]);
							if(currentScore>maxCurrentScore) {
								maxCurrentScore = currentScore;
							}
						}
					}
					currentTotal += maxCurrentScore;
				}
			}
			var finalScore = currentTotal/counter;
			if(finalScore > tempMax) {tempToken = i; tempMax = finalScore;}
		}
		callback(null, serviceList[tempToken]);
	});}
	
}


