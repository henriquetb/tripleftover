
var React = require('react');
var ReactPropTypes = React.PropTypes;
var TLWebAPIUtils = require('../utils/TLWebAPIUtils');
var OffersTable = require('./OffersTable.react');


var MainSection = React.createClass({
	
  propTypes: {
    allOffers: ReactPropTypes.array.isRequired
  },
  
  render: function() {
	  
	  var offerTables = [];

	  var offers = this.props.allOffers;

	  for ( var key in offers) {
		  var market = {
				  "has": offers[key][0].has,
				  "wants": offers[key][0].wants
		  }
		  offerTables.push(<OffersTable key={key} offers={offers[key]} market={market} />);
	  }
	  
	  
	  return (
			  <section id="offers">
			  {offerTables}
			  </section>
	  );
  },

});

module.exports = MainSection;
