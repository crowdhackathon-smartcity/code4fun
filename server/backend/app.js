var express = require('express');
var app = express();

var serviceApi = require('./app/services.js');

app.get('/getListOfServices', function(req, res) {
	serviceApi.getListOfServices(function(err, services) {
		if(err) {
			res.status(400);
			res.end();
		}
		else {
			res.send(services);
			res.end();
		}
	});	
});

app.get('/getServiceById', function(req, res) {
	var serviceId = req.query.serviceId;
	serviceApi.getServiceById(serviceId, function(err, services) {
		if(err) {
			res.status(400);
			res.end();
		}
		else {
			res.send(services);
			res.end();
		}
	});	
});

app.listen(54870, function() {
	console.log("Server listening at port 54870");
});

