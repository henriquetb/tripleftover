
var React = require('react');
var ReactPropTypes = React.PropTypes;
var TLWebAPIUtils = require('../utils/TLWebAPIUtils');
var OffersTable = require('./OffersTable.react');


var MainSection = React.createClass({
	
  propTypes: {
    markets: ReactPropTypes.array.isRequired
  },
  
  render: function() {
	  
	  var offerTables = [];

	  var markets = this.props.markets;

	  for ( var key in markets) {
		  if (markets[key].length == 0) continue;
		  var market;
		  for (var i in markets[key]){
			  market = {
					  "has": markets[key][i].has,
					  "wants": markets[key][i].wants
			  }  
			  break;
		  }
		  offerTables.push(<OffersTable key={key} offers={markets[key]} market={market} />);
	  }
	  
	  
	  return (
			  <section id="offers">
			  {offerTables}
			  </section>
	  );
  },

});

module.exports = MainSection;
