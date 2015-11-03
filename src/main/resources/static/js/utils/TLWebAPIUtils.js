
var $ = require('jquery');
var TLServerActions = require('../actions/TLServerActions');

var TLWebAPIUtils = {
		getAllUsers: function(){
			var allUsers = [];
			$.get("/users", function(result) {
				allUsers = result;

				TLServerActions.receiveAll(allUsers);
				
			}.bind(this));
		}.bind(this),
		
		getAllOffers: function(){
			var allOffers = [];
			$.get("/offers", function(result) {
				allOffers = result;

				TLServerActions.receiveAllOffers(allOffers);
				
			}.bind(this));
		}.bind(this),
		

		/**
		 * receives an array of currency codes (or only one)
		 * returns a list of lists with the offers for each HAS currency
		 * */
		getOffersHasCurrency: function(currencyCodes){
			if ( (currencyCodes instanceof Array) != true) {
				currencyCodes = [currencyCodes];
			};			
			var multipleHasOffers = [];
			var i = 0;
			
			for ( var key in currencyCodes) {
				var hasOffers = [];
				$.get("/offers/has/"+currencyCodes[key], function(result) {
					hasOffers = result;
					if (hasOffers.length > 0) multipleHasOffers.push(hasOffers);
					if (++i == currencyCodes.length){
						TLServerActions.receiveOffersHasCurrency(multipleHasOffers);
					}
				}.bind(this));
				
			}
		}.bind(this),
		

		/**
		 * receives an array of currency codes (or only one)
		 * returns a list of lists with the offers for each WANTS currency
		 * */
		getOffersWantsCurrency: function(currencyCodes){
			if ( (currencyCodes instanceof Array) != true) {
				currencyCodes = [currencyCodes];
			};			
			var multipleWantsOffers = [];
			var i = 0;
			
			for ( var key in currencyCodes) {
				var wantsOffers = [];
				$.get("/offers/wants/"+currencyCodes[key], function(result) {
					wantsOffers = result;
					if (wantsOffers.length > 0) multipleWantsOffers.push(wantsOffers);
					if (++i == currencyCodes.length){
						TLServerActions.receiveOffersHasCurrency(multipleWantsOffers);
					}
				}.bind(this));
				
			}
		}.bind(this),
		
		
}


module.exports = TLWebAPIUtils;