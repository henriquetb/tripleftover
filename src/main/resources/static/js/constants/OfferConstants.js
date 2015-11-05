
var keyMirror = require('keymirror');

module.exports = keyMirror({  

	//server response events
	RECEIVE_RAW_OFFERS: null,
	RECEIVE_HAS_OFFERS: null,
	RECEIVE_MULTIPLE_HAS_OFFERS: null,
	RECEIVE_MARKETS_OFFERS: null, 
	RECEIVE_USER_OFFERS: null,
	RECEIVE_OFFER_DELETED: null,
	RECEIVE_OFFER_SAVED: null,


	OFFER_CREATE: null,
	OFFER_EDIT: null,

	//client triggered events
	CHANGE_MARKETS_EVENT: "changeMarkets",
	CHANGE_OFFERS_EVENT: "changeOffers",
	
});
