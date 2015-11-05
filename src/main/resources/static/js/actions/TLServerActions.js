
var AppDispatcher = require('../dispatcher/AppDispatcher');
var OfferConstants = require('../constants/OfferConstants');

var TLServerAction = {
		receiveAll: function(rawUsers) {
			AppDispatcher.dispatch({
				actionType: TodoConstants.RECEIVE_RAW_USERS,
				rawUsers: rawUsers
			});
		},
		

		receiveAllOffers: function(allOffers) {
			AppDispatcher.dispatch({
				actionType: OfferConstants.RECEIVE_RAW_OFFERS,
				allOffers: allOffers
			});
		},


		/*receiveOffersHasCurrency: function(hasOffers) {
			AppDispatcher.dispatch({
				actionType: OfferConstants.RECEIVE_HAS_OFFERS,
				hasOffers: hasOffers
			});
		},

		receiveOffersWantsCurrency: function(wantsOffers) {
			AppDispatcher.dispatch({
				actionType: OfferConstants.RECEIVE_WANTS_OFFERS,
				wantsOffers: wantsOffers
			});
		},*/
		
		receiveOffersPerMarket: function(marketsOffers) {
			AppDispatcher.dispatch({
				actionType: OfferConstants.RECEIVE_MARKETS_OFFERS,
				marketsOffers: marketsOffers
			});
		},
		
		receiveOffersPerUser: function(userOffers) {
			AppDispatcher.dispatch({
				actionType: OfferConstants.RECEIVE_USER_OFFERS,
				userOffers: userOffers
			});
		},
		
		deleteOffer: function(deletedOffer) {
			AppDispatcher.dispatch({
				actionType: OfferConstants.RECEIVE_OFFER_DELETED,
				deletedOffer: deletedOffer
			});
		},
		
		saveOffer: function(savedOffer) {
			AppDispatcher.dispatch({
				actionType: OfferConstants.RECEIVE_OFFER_SAVED,
				savedOffer: savedOffer
			});
		},
}

module.exports = TLServerAction;