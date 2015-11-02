
var Header = require('./Header.react');
var MainSection = require('./MainSection.react');
var React = require('react');
var OfferStore = require('../stores/OfferStore');
var TLWebAPIUtils = require('../utils/TLWebAPIUtils');

/**
 * Retrieve the current TODO data from the TodoStore
 */
function getAppState() {
  return {
    allOffers: OfferStore.getAll() || [],
  };
}


var App = React.createClass({

  getInitialState: function() {
    return getAppState() ;
  },

  componentWillMount: function() {
	  OfferStore.addChangeListener(this._onChange);
	  //TLWebAPIUtils.getAllOffers();
	  
	  //TLWebAPIUtils.getOffersHasCurrency(["usd", "aud"]);
	  TLWebAPIUtils.getOffersPerMarket([{"has":"aud", "wants":"eur"}]);
	  
	  this.setState(getAppState());
  },

  componentWillUnmount: function() {
	  OfferStore.removeChangeListener(this._onChange);
  },

  render: function() {
    return (
      <div>
        <Header />
        <MainSection allOffers={this.state.allOffers}/>
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
