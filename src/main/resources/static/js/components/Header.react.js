var React = require('react');
var OfferActions = require('../actions/OfferActions');
var TextInput = require('./TextInput.react');
var SelectInput = require('./SelectInput.react');

var Header = React.createClass({
	
	
	render: function() {
		var availableCurrencies = [{"text":"", "value":"0"},
								   {"text":"USD", "value":"usd"},
		                           {"text":"AUD", "value":"aud"},
		                           {"text":"EUR", "value":"eur"},
		                           {"text":"BRL", "value":"brl"}];
		
		return (
				<header id="header">
				<h1>I Have: </h1>
				<TextInput id="hasAmount" placeholder="100" onSave={this._onSave} autoFocus={true} />
				<SelectInput id="hasCurrency" options={availableCurrencies} defaultValue="aud" />
				<h1>I Need: </h1>
				<TextInput id="wantsAmount" placeholder="150" onSave={this._onSave} />
				<SelectInput id="wantsCurrency" options={availableCurrencies} />
				</header>
		);
	},

	
	/**
	 * Event handler called within TextInput.
	 * Defining this here allows TextInput to be used in multiple places
	 * in different ways.
	 * @param {string} text
	 */
	_onSave: function(text) {
		console.log("enter clicked on the input")
		//if (text.trim()){
			//OfferActions.create(text);
		//}

	}

});

module.exports = Header;
