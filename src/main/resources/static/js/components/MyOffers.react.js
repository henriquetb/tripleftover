
var React = require('react');
var ReactPropTypes = React.PropTypes;
//var TLWebAPIUtils = require('../utils/TLWebAPIUtils');
var UserOffersTable = require('./UserOffersTable.react');
var OfferStore = require('../stores/OfferStore');
var OfferConstants = require('../constants/OfferConstants');
var OfferWebAPIUtils = require('../utils/OfferWebAPIUtils');
var TextInput = require('./TextInput.react');
var SelectInput = require('./SelectInput.react');
var Utils = require('../utils/Utils');
var UserStore = require('../stores/UserStore');

function getMyOffersState() {
	return {
		myOffers: OfferStore.getOffers() || [],
	};
}

var MyOffers = React.createClass({

	propTypes: {
		hasProp: ReactPropTypes.string,
		wantsProp: ReactPropTypes.string 
	},
	
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
		this.availableCurrencies = Utils.getAvailableCurrencies();
		
		var hasProp = this.props.hasProp || "eur" ;
		var wantsProp = this.props.wantsProp || "aud" ;
		
		this.haveAmount = <TextInput type="number" id="haveAmount" ref="haveAmount" placeholder="Amount" autoFocus={true}/>;
		this.have = <SelectInput id="haveCurrency" ref="haveCurrency" options={this.availableCurrencies} defaultValue={hasProp} />;
		this.wantAmount = <TextInput type="number" id="wantAmount" ref="wantAmount" placeholder="Amount" autoFocus={false} />;
		this.want = <SelectInput id="wantCurrency" ref="wantCurrency" options={this.availableCurrencies} defaultValue={wantsProp} />;
		
	
		return (
				<section id="myOffers">
				
					<span className="formLabel">I have: </span>
					{this.haveAmount}
					{this.have}
					
					<br />
					<span className="formLabel">I want: </span>
					{this.wantAmount}
					{this.want}
					
					<br />
					<TextInput type="number" className="smallInput" id="rate" ref="rate" placeholder="Rate" autoFocus={false} />
					
					<br />
					<span className="floatRight link" onClick={this._saveOffer}>Save</span>
					
					<UserOffersTable offers={this.state.myOffers} ownOffers={true} />
				
					
				</section>
		);
	},
	
	_saveOffer: function (){
		var offerId = null;
		var has = this.refs.haveCurrency.getValue();
		var wants = this.refs.wantCurrency.getValue();
		var amount = this.refs.haveAmount.getValue();
		var rate = this.refs.rate.getValue();
		var user = UserStore.getLoggedUser();
		var newOffer = {
				has: has.toUpperCase(),
				wants: wants.toUpperCase(),
				amount: amount,
				rate: rate,
				user: user
			}
		OfferWebAPIUtils.saveOffer(offerId, newOffer);
	},

	_onChange: function() {
		this.setState(getMyOffersState());
	}
});



module.exports = MyOffers;
