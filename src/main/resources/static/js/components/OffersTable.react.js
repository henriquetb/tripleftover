var React = require('react');
var ReactPropTypes = React.PropTypes;

var OffersTable = React.createClass({

	propTypes: {
		market: ReactPropTypes.object.isRequired,
		offers: ReactPropTypes.array.isRequired
	},
	
	render: function() {
		  
		var offers = this.props.offers;
		var offerItem = []
		
		for (var key in offers) {
			offerItem.push(
				<tr key={offers[key].id}>
					<td>{offers[key].user.name}</td>
					<td>{offers[key].amount} {offers[key].has}</td>
					<td>{parseFloat(offers[key].amount*offers[key].rate).toFixed(2)} {offers[key].wants}</td>
					<td>{offers[key].rate} ({offers[key].wants}/{offers[key].has})</td>
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
							<td>Has</td>
							<td>Wants</td>
							<td>Rate</td>
						</tr>
						</thead>
						<tbody>{offerItem}</tbody>
						
					</table>
				</div>
		);
		
	},
});

module.exports = OffersTable;