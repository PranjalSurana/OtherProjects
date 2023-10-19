/**
 * Currying function examples
 */

// TODO: view the web page in a browser
//       click on the button to see the results
// TODO: replace the definition of the dub function 
//       by using the curry function to define a function that awaits 1 argument

// assign the button click handlers
$(document).ready(function() {
	$("#curryingbutton").click(function() {
		curryFunctions();
	});
});

var curryFunctions = function() {
	var numbers = [ 1, 5, 10, 42 ];
	var html = "<ul>";
	var doublehtml = "<ul>";
	
    var dub = curryMultiply(2);
	var doublevals = numbers.map(dub);
	
	for (var i = 0; i < numbers.length; i++) {
		html += "<li>" + numbers[i] + "</li>";
		doublehtml += "<li>" + doublevals[i] + "</li>";
	}
	
	html += "</ul>";
	$("#originalNumbers").html(html);
	doublehtml += "</ul>";
	$("#doubleNumbers").html(doublehtml);
};

var multiply = function(x, y) {
	return x * y;
};

var curryMultiply = function(x) {
	return function(y) {
		return multiply(x, y);
	}
};

// the curry function takes one argument that is a fixed-arity function
// (that is, fx has a fixed number of arguments)
// it returns a curried version of the original function
// calling curry produces a new function which when called, either returns
// a partially applied version of the original o
// or the result of the full application
// ex: var f = curry(multiply) -- returns a function awaiting 2 arguments
// ex: var f = curry(multiply)(2) -- returns a function awaiting 1 argument
function curry(fx) {
	var arity = fx.length;
	
	return function f1() {
		var args = Array.prototype.slice.call(arguments, 0);
		if (args.length >= arity) {
			return fx.apply(null, args);
		}
		else {
			return function f2() {
				var args2 = Array.prototype.slice.call(arguments, 0);
				return f1.apply(null, args.concat(args2));
			}
		}
	}
}
