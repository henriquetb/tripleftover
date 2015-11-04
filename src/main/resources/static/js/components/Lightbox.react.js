
var React = require('react');
var ReactPropTypes = React.PropTypes;
//var TLWebAPIUtils = require('../utils/TLWebAPIUtils');
//var OffersTable = require('./OffersTable.react');


var Lightbox = React.createClass({
	
  propTypes: {
    content: ReactPropTypes.element.isRequired,
    onClose: ReactPropTypes.func.isRequired
  },
  
  render: function() {

	  var content = this.props.content;
	  
	  return (
			  <section id="lightbox">
			  	<div className="overlay" onClick={this._closeLightbox}></div>
			  	<div className="lightboxContet"> {content} </div>
			  </section>
	  );
  },

  _closeLightbox: function(){
	  this.props.onClose();
  }
});

module.exports = Lightbox;
