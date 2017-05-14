var express = require('express');
var app = express();
var https = require('https');
var bodyParser = require('body-parser');
var databaseHandler = require('./app/databaseHandler.js');
var nluApi = require('./app/nlu.js');

//THIS SHOULD CHANGE!!!!
var googlePlacesApiKey = 'AIzaSyA2PfSbibBdYFmX6CD9jtyB-NS5trBzHSg';


app.use(bodyParser.json());
//Passes the text from the voice recognition to the NLU api
app.post('/processText', function (req, res) {
	var text = req.body.text;
	//Get list of services to pass to the NLU api
	databaseHandler.getListOfServices(function (err, serviceList) {
		if (err) {
			res.status(400);
			res.end();
		}
		else {
			//Call the NLU api
			nluApi.processText(text, serviceList, function (err, selectedService) {
				if (err) {
					res.status(400);
					res.end();
				}
				else {
					//Send the service, the NLU selected, as a response
					res.send(selectedService);
					res.end();
				}
			});
		}
	});
});

//Gets a keyword and the location of the user and sends back the places near him that stick to the keyword
app.get('/getMapPlaces', function (req, res) {
	var text = req.query.text;
	var longitude = req.query.longitude;
	var latitude = req.query.latitude;

	var options = {
		host: 'maps.googleapis.com',
		port: 443,
		path: '/maps/api/place/nearbysearch/json?language=el&location=' + latitude + ',' + longitude + '&radius=2000&keyword=' + text + '&key=' + googlePlacesApiKey,
		method: 'GET',
		headers: {
			accept: '*/*'
		}
	};

	var googlePlacesRequest = https.request(options, function (googlePlacesResponse) {
		var body = "";
		googlePlacesResponse.on('data', function (data) {
			body += data;
		});
		googlePlacesResponse.on('end', function () {
			var places = [];
			var json = JSON.parse(body);
			for (var i = 0; i < json.results.length; i++) {
				places[i] = { name: json.results[i].name, longitude: json.results[i].geometry.location.lng, latitude: json.results[i].geometry.location.lat };
			}
			//Send the response back to the user
			res.write(JSON.stringify({ results: places }));
			res.end();
		});
	});
	googlePlacesRequest.end();

	googlePlacesRequest.on('error', function (error) {
		console.error(error);
		res.status(400);
		res.end();
	});
});

//Get the list of all services
app.get('/getListOfServices', function (req, res) {
	databaseHandler.getListOfServices(function (err, services) {
		if (err) {
			res.status(400);
			res.end();
		}
		else {
			res.send(services);
			res.end();
		}
	});
});

//Get a single service from it's id field
app.get('/getServiceById', function (req, res) {
	var serviceId = req.query.serviceId;
	databaseHandler.getServiceById(serviceId, function (err, services) {
		if (err) {
			res.status(400);
			res.end();
		}
		else {
			res.send(services);
			res.end();
		}
	});
});

//Start the server
app.listen(54870, function () {
	console.log("Server listening at port 54870");
});

