var React = require('react');
var ReactPropTypes = React.PropTypes;
var OfferWebAPIUtils = require('../utils/OfferWebAPIUtils');
var OfferStore = require('../stores/OfferStore');
var Utils = require('../utils/Utils');

var UserOffersTable = React.createClass({


	getInitialState: function() {
		return {
			col: 'has',
			order: true
		} ;
	},
	
	propTypes: {
		ownOffers: ReactPropTypes.bool || false,
		offers: ReactPropTypes.array.isRequired,
		onSelectOffer: ReactPropTypes.func,
	},
	
	shouldComponentUpdate: function(nextProps, nextState){
		nextProps.offers.sort(Utils.genericComparable(nextState.col, nextState.order));
		return true;
	},
	
	
	render: function() {
		  
		var offers = this.props.offers;
		var offerItem = []

		
		for (var key in offers) {
			var action = <span className="link" onClick={this._clickOffer.bind(this, offers[key].id)}> $ </span>;
			if (this.props.ownOffers){
				action = <div>
						<span className="link red" onClick={this._clickDelete.bind(this, offers[key].id)}> X </span> | 
						<span className="link" onClick={this._clickEdit.bind(this, offers[key].id)}> E </span>
					</div>
			}
			
			offerItem.push(
				<tr key={key}>
					<td>{offers[key].amount} {offers[key].has}</td>
					<td>{parseFloat(offers[key].amount*offers[key].rate).toFixed(2)} {offers[key].wants}</td>
					<td>{offers[key].rate} ({offers[key].wants}/{offers[key].has})</td>
					<td>{action}</td>
				</tr>
			);
		}		
		
		return (
				<div className="tableDiv">
			      	<table>
						<thead>
						<tr>
							<td id="has" className="link" onClick={this._clickSort}>Has</td>
							<td id="wants" className="link" onClick={this._clickSort}>Wants</td>
							<td id="rate" className="link" onClick={this._clickSort}>Rate</td>
							<td></td>
						</tr>
						</thead>
						<tbody>{offerItem}</tbody>
						
					</table>
				</div>
				
		);		
		
		
	},

	_onChange: function() {
		this.props.offers =  OfferStore.getOffers() || [];
	},
	
	_clickSort: function (){
		this.setState({
			col: 'has',
			order: !this.state.order
		});
	},
	
	_clickDelete: function(id){
		OfferWebAPIUtils.deleteOffer(id);
	},
	
	_clickOffer: function(){
		alert('offer');
	},
	
	_clickEdit: function(id){
		if (this.props.onSelectOffer)
			this.props.onSelectOffer(id);
	},
	
});

module.exports = UserOffersTable;