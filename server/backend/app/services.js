var mongo = require('mongoskin');
var ObjectId = require('mongodb').ObjectID;
var db = mongo.db("mongodb://localhost:27017/cityKiosk", {native_parser: true});
db.bind('service');
db.bind('organization');

module.exports = {

	getListOfServices: function(callback) {
		db.service.find({$where: "this.category.length > 0"}).toArray(function(err, items) {
			callback(null, items);
		});
	},	
	getServiceById: function(id, callback) {
		db.service.find({_id: ObjectId(id) }).toArray(function(err, items) {
			callback(null, items);
		});
	}	
}
