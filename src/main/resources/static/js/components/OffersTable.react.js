var React = require('react');
var ReactPropTypes = React.PropTypes;
var Lightbox = require('./Lightbox.react');
var Utils = require('../utils/Utils');

var OffersTable = React.createClass({

	
	getInitialState: function() {
		return {
			col: 'amount',
			order: true
		} ;
	},
	
	propTypes: {
		market: ReactPropTypes.object.isRequired,
		offers: ReactPropTypes.array.isRequired
	},
	
	shouldComponentUpdate: function(nextProps, nextState){
		nextProps.offers.sort(Utils.genericComparable(nextState.col, nextState.order));
		return true;
	},
	
	render: function() {
		
		var offers = this.props.offers;
		var offerItem = [];
		
		for (var key in offers) {
			offerItem.push(
				<tr key={offers[key].id}>
					<td>{offers[key].user.name}</td>
					<td>{parseFloat(offers[key].amount*offers[key].rate).toFixed(2)} {offers[key].wants}</td>
					<td>{offers[key].amount} {offers[key].has}</td>
					<td>{offers[key].rate} ({offers[key].wants}/{offers[key].has})</td>
					<td><span className="link" onClick={this._clickOffer}> $ </span></td>
				</tr>
			);
		}
		
		
		return (
				<div className="tableDiv">
					<span className="marketTitle">Users that have {this.props.market.has} and want {this.props.market.wants}:</span>
			      	<table>
						<thead>
						<tr>
							<td>User</td>
							<td>Wants</td>
							<td id="amount" className="link" onClick={this._clickSort}>Has</td>
							<td id="rate" className="link" onClick={this._clickSort}>Rate</td>
							<td></td>
						</tr>
						</thead>
						<tbody>{offerItem}</tbody>
						
					</table>
				</div>
		);		
	},
	
	
	_clickOffer: function(){
		alert('make an offer. Not implemented!');
	},
	
	_clickSort: function (element){
		this.setState({
			col: element.target.id,
			order: !this.state.order
		});
	},
});

module.exports = OffersTable;