
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
		  offerTables.push(<OffersTable key={key} offers={offers[key]} />);
	  }
	  
	  return (
			  <section id="offers">
			  {offerTables}
			  </section>
	  );
  },

});

module.exports = MainSection;
