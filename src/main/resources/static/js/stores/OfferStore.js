
var AppDispatcher = require('../dispatcher/AppDispatcher');
var EventEmitter = require('events').EventEmitter;
var OfferConstants = require('../constants/OfferConstants');
var assign = require('object-assign');

var CHANGE_EVENT = 'change';

var _offers = [];

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

/**
 * Update an Offer.
 * @param  {string} id
 * @param {object} updates An object literal containing only the data to be
 *     updated.
 */
function update(id, updates) {
  _offers[id] = assign({}, _offers[id], updates);
}

var OfferStore = assign({}, EventEmitter.prototype, {

  /**
   * Get the entire collection of Offers.
   * @return {object}
   */
  getAll: function() {
    return _offers;
  },

  emitChange: function() {
    this.emit(CHANGE_EVENT);
  },

  /**
   * @param {function} callback
   */
  addChangeListener: function(callback) {
    this.on(CHANGE_EVENT, callback);
  },

  /**
   * @param {function} callback
   */
  removeChangeListener: function(callback) {
    this.removeListener(CHANGE_EVENT, callback);
  }
});

// Register callback to handle all updates
AppDispatcher.register(function(action) {
  var text;

  switch(action.actionType) {
    case OfferConstants.OFFER_CREATE:
      text = action.text.trim();
      if (text !== '') {
        create(text);
        OfferStore.emitChange();
      }
      break;

    case OfferConstants.OFFER_EDIT:
      text = action.text.trim();
      if (text !== '') {
        update(action.id, {text: text});
        OfferStore.emitChange();
      }
      break;

    case OfferConstants.RECEIVE_RAW_OFFERS:
    	_offers = action.allOffers;
    	OfferStore.emitChange();
      break;

    case OfferConstants.RECEIVE_HAS_OFFERS:
    	_offers = action.hasOffers;
    	OfferStore.emitChange();
      break;

    case OfferConstants.RECEIVE_WANTS_OFFERS:
    	_offers = action.wantsOffers;
    	OfferStore.emitChange();
      break;
      
    case  OfferConstants.RECEIVE_MARKETS_OFFERS:
    	_offers = action.marketsOffers;
    	OfferStore.emitChange();
      break;
      
    default:
      // no op
  }
});

module.exports = OfferStore;
