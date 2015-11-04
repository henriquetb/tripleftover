
var React = require('react');
var ReactPropTypes = React.PropTypes;
//var TLWebAPIUtils = require('../utils/TLWebAPIUtils');
var UserOffersTable = require('./UserOffersTable.react');
var OfferStore = require('../stores/OfferStore');
var OfferConstants = require('../constants/OfferConstants');
var OfferWebAPIUtils = require('../utils/OfferWebAPIUtils');

function getMyOffersState() {
	return {
		myOffers: OfferStore.getOffers() || [],
	};
}

var MyOffers = React.createClass({
	

	getInitialState: function() {
		return getMyOffersState() ;
	},

	componentWillMount: function() {
		OfferStore.addChangeListener(OfferConstants.CHANGE_OFFERS_EVENT, this._onChange);
		OfferWebAPIUtils.getOffersPerUser(1);
		this.setState(getMyOffersState());
	},


	componentWillUnmount: function() {
		OfferStore.removeChangeListener(OfferConstants.CHANGE_OFFERS_EVENT, this._onChange);
	},


	render: function() {

		return (
				<section id="myOffers">
					<UserOffersTable offers={this.state.myOffers} ownOffers={true} />
				</section>
		);
	},

	_onChange: function() {
		this.setState(getMyOffersState());
	}
});



module.exports = MyOffers;
