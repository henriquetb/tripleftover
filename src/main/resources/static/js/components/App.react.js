
var Header = require('./Header.react');
var MainSection = require('./MainSection.react');
var React = require('react');
var OfferStore = require('../stores/OfferStore');
var OfferConstants = require('../constants/OfferConstants');
var OfferWebAPIUtils = require('../utils/OfferWebAPIUtils');

/**
 * Retrieve the current TODO data from the TodoStore
 */
function getAppState() {
  return {
    markets: OfferStore.getMarketsLists() || [],
  };
}


var App = React.createClass({

	
  getInitialState: function() {
    return getAppState() ;
  },

  componentWillMount: function() {
	  OfferStore.addChangeListener(OfferConstants.CHANGE_MARKETS_EVENT, this._onChange);
	  
	  OfferWebAPIUtils.getOffersPerMarket([{"has":"aud", "wants":"eur"}, {"has":"eur", "wants":"aud"}]);  
	  
	  this.setState(getAppState());
  },

  
  componentWillUnmount: function() {
	  OfferStore.removeChangeListener(OfferConstants.CHANGE_MARKETS_EVENT, this._onChange);
  },

  render: function() {
    return (
      <div>
        <Header />
        <MainSection markets={this.state.markets}/>
      </div>
    );
  },

  /**
   * Event handler for 'change' events coming from the OfferStore
   */
  _onChange: function() {
    this.setState(getAppState());
  }

});

module.exports = App;
