var React = require('react');
var ReactPropTypes = React.PropTypes;
var OfferWebAPIUtils = require('../utils/OfferWebAPIUtils');
var OfferStore = require('../stores/OfferStore');

var UserOffersTable = React.createClass({

	propTypes: {
		ownOffers: ReactPropTypes.bool || false,
		offers: ReactPropTypes.array.isRequired,
		onSelectOffer: ReactPropTypes.func,
	},
	
	componentDidMount: function() {
		OfferStore.addChangeListener("changeOffers", this._onChange);
	},


	componentWillUnmount: function() {
		OfferStore.removeChangeListener("changeOffers", this._onChange);
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
					<td>{parseFloat(offers[key].amount*offers[key].rate).toFixed(2)} {offers[key].wants}</td>
					<td>{offers[key].amount} {offers[key].has}</td>
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
							<td>Wants</td>
							<td>Has</td>
							<td>Rate</td>
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