var React = require('react');
var ReactPropTypes = React.PropTypes;

var OffersTable = React.createClass({

	propTypes: {
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
					<td>{offers[key].amount*offers[key].rate} {offers[key].wants}</td>
					<td>{offers[key].rate} ({offers[key].wants}/{offers[key].has})</td>
				</tr>
			);
		}

		return (
				<div>
					<span className="marketTitle">Users that have USD and want AUD:</span>
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