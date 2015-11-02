
var React = require('react');
var ReactPropTypes = React.PropTypes;


var SelectInput = React.createClass({

  propTypes: {
    className: ReactPropTypes.string,
    id: ReactPropTypes.string,
    defaultValue: ReactPropTypes.string,
    value: ReactPropTypes.string,
    options: ReactPropTypes.array
  },

  getInitialState: function() {
    return {
      value: this.props.value || ''
    };
  },

  /**
   * @return {object}
   */
  render: function() /*object*/ {
	  var opts = this.props.options;
	  
	  var selOptions = [];
	  
	  for ( var key in opts) {
		  var opt = opts[key];
		  selOptions.push(<option key={opt.value} value={opt.value}>{opt.text}</option>);
	  }
	  
	  return (
			  <select
		        className={this.props.className}
		        id={this.props.id}
		        onChange={this._onChange}
			  	defaultValue={this.props.defaultValue}>
			  	{selOptions}
			  </select>
	  );
  },

  /**
   * @param {object} event
   */
  _onChange: function(/*object*/ event) {
	  console.log('changed selection');
    this.setState({
      value: event.target.value
    });
  },

});

module.exports = SelectInput;
