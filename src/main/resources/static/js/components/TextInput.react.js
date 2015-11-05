
var React = require('react');
var ReactPropTypes = React.PropTypes;

var ENTER_KEY_CODE = 13;

var TextInput = React.createClass({

  propTypes: {
    className: ReactPropTypes.string,
    id: ReactPropTypes.string,
    placeholder: ReactPropTypes.string,
    onChange: ReactPropTypes.func,
    value: ReactPropTypes.string,
    autoFocus: ReactPropTypes.bool,
    disabled: ReactPropTypes.bool,
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
    return (
      <input
        className={this.props.className}
        id={this.props.id}
        placeholder={this.props.placeholder}
      	onBlur={this._save}
        onChange={this._onChange}
        onKeyDown={this._onKeyDown}
        value={this.state.value}
        autoFocus={this.props.autoFocus}
      	disabled={this.props.disabled}
      />
    );
  },
  
  /**
   * Invokes the callback passed in as onSave, allowing this component to be
   * used in different ways.
   */
  _save: function() {
	  if (this.props.onChange)
		  this.props.onChange(this.state.value, this);
    /*this.setState({
      value: ''
    });*/
  },

  /**
   * @param {object} event
   */
  _onChange: function(/*object*/ event) {
	  var value = event.target.value
	  this.setValue(value);
  },
  

  /**
   * @param  {object} event
   */
  _onKeyDown: function(event) {
    if (event.keyCode === ENTER_KEY_CODE) {
      this._save();
    }
  },
  
  getValue: function(){
	  return this.state.value
  },
  
  setValue: function(value){
	  this.setState({
		  value: value
	  });
  },
  

});

module.exports = TextInput;
