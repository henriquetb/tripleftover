
var AppDispatcher = require('../dispatcher/AppDispatcher');
var EventEmitter = require('events').EventEmitter;
var UserConstants = require('../constants/UserConstants');
var assign = require('object-assign');

var CHANGE_EVENT = 'change';

var _users = {};


/**
 * Create a TODO item.
 * @param  {string} text The content of the TODO
 */
function create(text) {
	// Hand waving here -- not showing how this interacts with XHR or persistent
	// server-side storage.
	// Using the current timestamp + random number in place of a real id.
	var id = (+new Date() + Math.floor(Math.random() * 999999)).toString(36);
	_users[id] = {
			id: id,
			complete: false,
			text: text
	};
}

/**
 * Update an user.
 * @param  {string} id
 * @param {object} updates An object literal containing only the data to be
 *     updated.
 */
function update(id, updates) {
	_users[id] = assign({}, _users[id], updates);
}

var UserStore = assign({}, EventEmitter.prototype, {

	getLoggedUser: function() {
		return {
			id: 1,
			name: "Henrique"
		}
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

//Register callback to handle all updates
AppDispatcher.register(function(action) {
	var text;

	switch(action.actionType) {
	case UserConstants.USER_CREATE:
		text = action.text.trim();
		if (text !== '') {
			create(text);
			UserStore.emitChange();
		}
		break;

	case UserConstants.USER_EDIT:
		text = action.text.trim();
		if (text !== '') {
			update(action.id, {text: text});
			UserStore.emitChange();
		}
		break;

	case UserConstants.RECEIVE_RAW_USERS:
		console.log("received");
		break;

	default:
		// no op
	}
});

module.exports = UserStore;
