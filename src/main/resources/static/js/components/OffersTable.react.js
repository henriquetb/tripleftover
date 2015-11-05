var React = require('react');
var ReactPropTypes = React.PropTypes;
var Lightbox = require('./Lightbox.react');
var Utils = require('../utils/Utils');

var OffersTable = React.createClass({

	propTypes: {
		market: ReactPropTypes.object.isRequired,
		offers: ReactPropTypes.array.isRequired
	},
	
	render: function() {
		
		var offers = this.props.offers;
		var offerItem = []
		
		offers.sort(Utils.genericComparable('rate', true));
		
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
	
	
	_clickOffer: function(){
		alert('oee');
	},
});

module.exports = OffersTable;