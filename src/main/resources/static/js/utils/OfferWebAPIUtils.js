
var $ = require('jquery');
var TLServerActions = require('../actions/TLServerActions');

//GETs
//offer by id - base/{id}
//offers by user - base/user/{id}
//offers by has currency - base/has/{code}
//offers by wants currency - base/wants/{code}
//offers by has and wants - base/{code}/{code}
//offers by has currency and has amount - base/has/{code}/{amount}
//offers by wants currency and wants amount - base/wants/{code}/{amount}
//offers by has currency, wants currency and wants amount - base/{code}/{code}/{amount}

/*private RequestBuilder getOffer(Long offerId, Long userId, CurrencyCode has, BigDecimal hasAmount, CurrencyCode wants, BigDecimal wantsAmount) throws Exception {
	StringBuilder url = new StringBuilder(this.baseUrl);
	
	if (offerId != null) url.append("/"+offerId.toString());
	else if (userId != null) url.append("/user/"+userId);
	else if (has != null && wants != null && wantsAmount != null) url.append("/"+has+"/"+wants+"/"+wantsAmount);
	else if (wants != null && wantsAmount != null) url.append("/wants/"+wants+"/"+wantsAmount);
	else if (has != null && hasAmount != null) url.append("/has/"+has+"/"+hasAmount);
	else if (has != null && wants != null) url.append("/"+has+"/"+wants);
	else if (wants != null) url.append("/wants/"+wants);
	else if (has != null) url.append("/has/"+has);
	
	return get(url.toString())
			.contentType(contentType);		
}*/

var baseUrl = "/offers";
var performRequest = function(options){
	var url = baseUrl;
	
	var callback = options.callback;
	var type = options.type || 'GET'
	var offerId = options.offerId;
	var userId = options.userId
	var has = options.has;
	var hasAmount = options.hasAmount;
	var wants = options.wants;
	var wantsAmount = options.wantsAmount;
	var newOfferData = options.newOfferData;
	
	if (offerId) url += "/"+offerId;
	else if (userId) url += "/user/"+userId;
	else if (has && wants && wantsAmount) url += "/"+has+"/"+wants+"/"+wantsAmount;
	else if (wants && wantsAmount) url += "/wants/"+wants+"/"+wantsAmount;
	else if (has && hasAmount) url += "/has/"+has+"/"+hasAmount;
	else if (has && wants) url += "/"+has+"/"+wants;
	else if (wants) url += "/wants/"+wants;
	else if (has) url += "/has/"+has;
	
	var requestResult;
	/*$.get(url, function(result) {
		return result;
	}.bind(this));*/
	
	$.ajax({
		"url": url,
		"datatype": "json",
		"type": type,
		"data": newOfferData,
		"success": function(data, textStatus, jqXHR){
			if (callback) callback(data);
		}
	});
	
};



var OfferWebAPIUtils = {
		getAllOffers: function(){
			//performRequest();
		},

		/**
		 * receives an array of currency codes (or only one)
		 * returns a list of lists with the offers for each HAS currency
		 * */
		/*getOffersHasCurrency: function(currencyCodes){
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
		}.bind(this),*/
		

		/**
		 * receives an array of offers with Wants = codes
		 * returns a list of lists with the offers for each WANTS currency
		 * */
		/*getOffersWantsCurrency: function(currencyCodes){
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
		}.bind(this),*/
		

		/**
		 * receives an array of markets (currency code to be sold and bought) 
		 *   - market = {"has":"aud", "wants":"eur"}
		 * returns a list of lists with the offers for each currency
		 * */
		getOffersPerMarket: function(marketCodes){
			if ( (marketCodes instanceof Array) != true) {
				marketCodes = [marketCodes];
			}
			
			var marketsOffers = [];
			var i = 0;
			
			var requestCallback = function(returnedData){
				//console.log(returnedData);
				if (returnedData && returnedData.length > 0) marketsOffers.push(returnedData); 
				if (++i == marketCodes.length){
					TLServerActions.receiveOffersPerMarket(marketsOffers);
				}
			}.bind(this);
			
			for ( var key in marketCodes) {
				
				performRequest({
					"callback": requestCallback,
					"has": marketCodes[key].has, 
					"wants": marketCodes[key].wants
				});
				
				
			}
			
		}.bind(this),

		/**
		 * receives an array of markets (currency code to be sold and bought) 
		 *   - market = {"has":"aud", "wants":"eur"}
		 * returns a list of lists with the offers for each currency
		 * */
		getOffersPerUser: function(userId){
			
			
			var requestCallback = function(returnedData){
				TLServerActions.receiveOffersPerUser(returnedData);
			}.bind(this);
			
			performRequest({
				"callback": requestCallback,
				"userId": userId, 
			});
		
		}.bind(this),

		
		deleteOffer:  function(offerId){
			
			var requestCallback = function(returnedData){
				TLServerActions.deleteOffer(returnedData);
			}.bind(this);
			
			performRequest({
				"callback": requestCallback,
				"offerId": offerId, 
				"type": "DELETE"
			});
		
		}.bind(this),
		
}


module.exports = OfferWebAPIUtils;