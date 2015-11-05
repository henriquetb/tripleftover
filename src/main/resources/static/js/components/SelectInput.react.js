
var React = require('react');
var ReactPropTypes = React.PropTypes;


var SelectInput = React.createClass({

  propTypes: {
    className: ReactPropTypes.string,
    id: ReactPropTypes.string,
    defaultValue: ReactPropTypes.string,
    value: ReactPropTypes.string,
    options: ReactPropTypes.array,
    onChange: ReactPropTypes.func,
  },

  
  getInitialState: function() {
    return {
      value: this.props.value || this.props.defaultValue || ''
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
      var value = event.target.value;
      this.setValue(value);
  		
  },
  
  getValue: function(){
	  return this.state.value
  }, 
  setValue: function(value){
	    this.setState({
	      value: value
	    }, function(){
	    	if (this.props.onChange)
	    		this.props.onChange(this.state.value, this);
	    }.bind(this) );
  },
});

module.exports = SelectInput;
