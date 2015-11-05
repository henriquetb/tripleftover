var Utils = {
		getAvailableCurrencies: function(){
			return [{"text":"USD", "value":"usd"},
			        {"text":"AUD", "value":"aud"},
			        {"text":"EUR", "value":"eur"},
			        {"text":"BRL", "value":"brl"}];
		},
		

		/*
		 * grenericComparable(attribute, order):
		 *
		 * Generic (for Strings and Integers) comparable function that given an String 'attribute' 
		 * and a boolean order returns the value of the attribute of 'a' compared to 'b'
		 */
		genericComparable: function (attribute, order){
			if (order)
				return function(a,b){return a[attribute]<b[attribute]?-1:1};
			else
				return function(a,b){return a[attribute]<b[attribute]?1:-1};
		},



}

module.exports = Utils;