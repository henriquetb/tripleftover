var React = require('react');
var OfferActions = require('../actions/OfferActions');
var TextInput = require('./TextInput.react');
var SelectInput = require('./SelectInput.react');
var Utils = require('../utils/Utils');
var OfferWebAPIUtils = require('../utils/OfferWebAPIUtils');


var Header = React.createClass({

	  getInitialState: function() {
	    return {
	      hasAmount: "",
	      has: "eur",
	      wantsAmount: "",
	      wants: "aud"
	    };
	  },
	  
	
	render: function() {
		var availableCurrencies = Utils.getAvailableCurrencies();
		
		this.hasAmount = <TextInput type="number" id="hasAmount" placeholder="Amount" onChange={this._onChangeAmount} autoFocus={true} />;
		this.hasCurrency = <SelectInput id="hasCurrency" options={availableCurrencies} defaultValue={this.state.has}  onChange={this._onChangeCurrency}/>;
		this.wantsAmount = <TextInput type="number" id="wantsAmount" placeholder="Amount" onChange={this._onChangeAmount} />;
		this.wantsCurrency = <SelectInput id="wantsCurrency" options={availableCurrencies} defaultValue={this.state.wants} onChange={this._onChangeCurrency} />;
		return (
				<header id="header">
					<div className="floatLeft">
						<h1>I Have: </h1>
						{this.hasAmount}
						{this.hasCurrency}
					</div>
					<div className="floatRight">
						<h1>I Need: </h1>
						{this.wantsAmount}
						{this.wantsCurrency}
					</div>
				</header>
		);
		
	},
	
		
	_onChangeAmount: function(value, elem){
		var id = elem.props.id;
		

		var updateTableValues = function(market){
			OfferWebAPIUtils.getOffersPerMarket([{'has': this.state.wants, 'wants': this.state.has}]);
		}.bind(this);

		var newValue = parseFloat(value);
		if (isNaN(newValue)) newValue = "";
		
		if (id === 'hasAmount' ){
			if (this.state.hasAmount == value) return;
			
			this.setState({
				hasAmount: newValue
			}, updateTableValues);
		}else if (id === 'wantsAmount' ){
			if (this.state.wantsAmount == value) return;
			this.setState({
				wantsAmount: newValue
			}, updateTableValues);
		}
	},
	
	
	_onChangeCurrency: function(value, elem){
		var id = elem.props.id;
		
		var updateTableValues = function(market){
			OfferWebAPIUtils.getOffersPerMarket([{'has': this.state.wants, 'wants': this.state.has}]);
		}.bind(this);
		
		if (id === 'hasCurrency' ){
			this.setState({
				has: value
			}, updateTableValues);
		}else if (id === 'wantsCurrency' ){
			this.setState({
				wants: value
			}, updateTableValues);
		}
	},
	
	updateTable: function(){
		
	}
});

module.exports = Header;
