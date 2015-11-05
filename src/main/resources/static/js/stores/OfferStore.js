
var AppDispatcher = require('../dispatcher/AppDispatcher');
var EventEmitter = require('events').EventEmitter;
var OfferConstants = require('../constants/OfferConstants');
var assign = require('object-assign');

var _offers = [];
var _marketsLists = [];

/**
 * Create an Offer item.
 */
function create(user, has, wants, amount, rate) {
  // Hand waving here -- not showing how this interacts with XHR or persistent
  // server-side storage.
  // Using the current timestamp + random number in place of a real id.
  var id = (+new Date() + Math.floor(Math.random() * 999999)).toString(36);
  _offers[id] = {
    id: id,
    user: user,
    has: has,
    wants: wants,
    amount: amount, 
    rate: rate
  };
}

function savedOffer(offer) {
	var hasOffer = offer.has;
	var wantsOffer = offer.wants;
	
	var marketsUpdated = false;
	var marketPosition = -1;
	
	for ( var key in _marketsLists) {
		for (var o in _marketsLists[key]){
			//checks if this the market of the offer
			if (marketPosition > -1 || (_marketsLists[key][o].has == hasOffer && _marketsLists[key][o].wants == wantsOffer)){
				marketPosition = key;
				
				//found the offer to edit 
				if (_marketsLists[key][o].id == offer.id){
					_marketsLists[key][o] = offer;
					marketsUpdated = true;
				}
			} else {
				break;
			}
		}
		
		if (marketPosition > -1 && !marketsUpdated){
			_marketsLists[marketPosition].push(offer);
		}
		if (marketPosition > -1) break;
	}
	
	var offerPosition = -1;
	for (var key in _offers){
		if (_offers[key].id == offer.id){
			offerPosition = key;
			_offers[key] = offer;
		}
	}
	if (offerPosition == -1){
		_offers.push(offer)
	}
	
} 

/**
 * Update an Offer.
 * @param  {string} id
 * @param {object} updates An object literal containing only the data to be
 *     updated.
 */
function update(id, updates) {
  _offers[id] = assign({}, _offers[id], updates);
}


/**
 * Delete an Offer item.
 * @param  {string} id
 */
function destroy(id) {
	for ( var key in _marketsLists) {
		for (var o in _marketsLists[key]){
			if (_marketsLists[key][o].id == id){
				_marketsLists[key].splice(o, 1);
			}
		}
	}
	
	for (var key in _offers){
		if (_offers[key].id == id){
			_offers.splice(key, 1);
		}
	}
}


var OfferStore = assign({}, EventEmitter.prototype, {

  /**
   * Get the entire collection of Offers.
   * @return {object}
   */
  getMarketsLists: function() {
    return _marketsLists;
  },
  getOffers: function() {
	  return _offers;
  },

  emitChange: function(event) {
    this.emit(OfferConstants[event]);
  },
  

  /**
   * @param {function} callback
   */
  addChangeListener: function(event, callback) {
    this.on(event, callback);
  },

  /**
   * @param {function} callback
   */
  removeChangeListener: function(event, callback) {
    this.removeListener(event, callback);
  }
});

// Register callback to handle all updates
AppDispatcher.register(function(action) {

  switch(action.actionType) {

    case OfferConstants.RECEIVE_RAW_OFFERS:
    	_offers = action.allOffers;
    	OfferStore.emitChange(OfferConstants.CHANGE_OFFERS_EVENT);
      break;

    /*case OfferConstants.RECEIVE_HAS_OFFERS:
    	_offers = action.hasOffers;
    	OfferStore.emitChange(CHANGE_MARKETS_EVENT);
      break;

    case OfferConstants.RECEIVE_WANTS_OFFERS:
    	_offers = action.wantsOffers;
    	OfferStore.emitChange(CHANGE_MARKETS_EVENT);
      break;*/
      
    case  OfferConstants.RECEIVE_MARKETS_OFFERS:
    	_marketsLists = action.marketsOffers;
    	OfferStore.emitChange(OfferConstants.CHANGE_MARKETS_EVENT);
      break;
      
    case  OfferConstants.RECEIVE_USER_OFFERS:
    	_offers = action.userOffers;
    	OfferStore.emitChange(OfferConstants.CHANGE_OFFERS_EVENT);
      break;
      
    case  OfferConstants.RECEIVE_OFFER_DELETED:
    	destroy(action.deletedOffer.id);
    	OfferStore.emitChange(OfferConstants.CHANGE_OFFERS_EVENT);
    	OfferStore.emitChange(OfferConstants.CHANGE_MARKETS_EVENT);
      break;
      
    case  OfferConstants.RECEIVE_OFFER_SAVED:
    	savedOffer(action.savedOffer);
    	OfferStore.emitChange(OfferConstants.CHANGE_OFFERS_EVENT);
    	OfferStore.emitChange(OfferConstants.CHANGE_MARKETS_EVENT);
      break;
      
     
    default:
      // no op
  }
});

module.exports = OfferStore;
