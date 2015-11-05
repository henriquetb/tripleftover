
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
		selectedOffer: "",
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
		
		this.hasProp = this.props.hasProp || "eur" ;
		this.wantsProp = this.props.wantsProp || "aud" ;
		
		this.haveAmount = <TextInput type="number" id="haveAmount" ref="haveAmount" placeholder="Amount" autoFocus={true} />; 
		this.have = <SelectInput id="haveCurrency" ref="haveCurrency" options={this.availableCurrencies} defaultValue={this.hasProp} />;
		this.wantAmount = <TextInput type="number" id="wantAmount" ref="wantAmount" placeholder="Amount" autoFocus={false} onChange={this._setRate} />;
		this.want = <SelectInput id="wantCurrency" ref="wantCurrency" options={this.availableCurrencies} defaultValue={this.wantsProp} />;
		
	
		return (
				<section id="myOffers">
					<span className="formLabel">Offer id: </span>
					<TextInput  className="smallInput" type="number" id="offerId" ref="offerId" placeholder="" disabled={true} />  
					
					
					<br />
					<span className="formLabel">I have: </span>
					{this.haveAmount}
					{this.have}
					
					<br />
					<span className="formLabel">I want: </span>
					{this.wantAmount}
					{this.want}
					
					<br />
					<TextInput type="number" className="smallInput" id="rate" ref="rate" placeholder="Rate" autoFocus={false} onChange={this._setRate} />
					
					<br />
					<div className="floatRight"> 
						<span className="link" onClick={this._saveOffer}>Save</span>&nbsp;|&nbsp; 
						<span className="link" onClick={this._cleanOffer}>Clean</span> 
					</div>
					<br />
					
					<UserOffersTable offers={this.state.myOffers} ownOffers={true} onSelectOffer={this._setOffer}/> 
				
					
				</section>
		);
	},
	
	_saveOffer: function (){
		var offerId = this.state.selectedOffer;
		var has = this.refs.haveCurrency.getValue();
		var wants = this.refs.wantCurrency.getValue();
		var amount = this.refs.haveAmount.getValue();
		var rate = this.refs.rate.getValue();
		var user = UserStore.getLoggedUser();
		
		if (!has || !wants || !amount || !rate){
			alert("Missing values"); 
			return ;
		}
		
		var newOffer = {
				has: has.toUpperCase(),
				wants: wants.toUpperCase(),
				amount: amount,
				rate: rate,
				user: user
			}
		OfferWebAPIUtils.saveOffer(offerId, newOffer);
		this._cleanOffer(); 
	},
	
	_cleanOffer: function(){
		this.setState({ 
			selectedOffer: null
		});
		this.refs.offerId.setValue(null); 
		this.refs.haveAmount.setValue(null);
		this.refs.haveCurrency.setValue(this.hasProp);
		this.refs.wantCurrency.setValue(this.wantsProp);
		this.refs.wantAmount.setValue(null);
		this.refs.rate.setValue(null);
		
	}, 
	
	_setOffer: function(id){
		this.setState({ 
			selectedOffer: id
		});
		var o = this._getOfferById(id);
		this.refs.offerId.setValue(id); 
		this.refs.haveAmount.setValue(o.amount);
		this.refs.haveCurrency.setValue(o.has);
		this.refs.wantCurrency.setValue(o.wants);
		this.refs.wantAmount.setValue(o.amount*o.rate);
		this.refs.rate.setValue(o.rate);
	}, 
	 
	_getOfferById: function(id){
		for (var i in this.state.myOffers){
			if (this.state.myOffers[i].id == id)
				return this.state.myOffers[i];
		}
		return null;
	},
	
	_setRate: function(value, elem) {
		var id = elem.props.id;
		if (!value) {
			this.refs.wantAmount.setValue("");
			this.refs.rate.setValue("");
		}
		if (id === 'rate'){
			this.refs.wantAmount.setValue(this.refs.haveAmount.getValue() * value);
		} else {
			this.refs.rate.setValue(this.refs.haveAmount.getValue() / value);
		}
	},

	_onChange: function() {
		this.setState(getMyOffersState());
	}
});



module.exports = MyOffers;
