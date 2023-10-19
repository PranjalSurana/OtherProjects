$(document).ready(function () {
    $("#demo").click(function () {
        demo();
    });
    $("#mapdemo").click(function () {
        mapdemo();
    });
    $("#filterdemo").click(function () {
        filterdemo();
    });
    $("#reducedemo").click(function () {
        reducedemo();
    });
});

/**
 * the foreach() function
 */

var products = [{
    name: 'Golf clubs',
    price: 175
}, {
    name: 'Basketball',
    price: 25
}, {
    name: 'Tennis racket',
    price: 95
}, {
    name: 'Baseball glove',
    price: 65
}, {
    name: 'Catnip mouse',
    price: 5.5
}];

var demo = function () {
    var html = "Listing the products with a for loop<br/><ul>";
    for (var i = 0; i < products.length; i++) {
        html += "<li>" + products[i].name + "</li>";
    }
    html += "</ul>";
    $("#productsForLoop").html(html);

    html = "Listing the products with a foreach loop<br/><ul>";
    // TODO: Use a forEach to do the same as the for loop above

    $("#productsForEachLoop").html(html);
};

// the map() function
var mapdemo = function () {
    var numbers = [1, 4, 9, 16, 121];
    var html = "The original numbers<br/><ul>";
    var roothtml = "The square roots<br/><ul>";

    // TODO: Assign the square roots of the numbers to roots
    var roots = [];
    for (var i = 0; i < numbers.length; i++) {
        html += "<li>" + numbers[i] + "</li>";
        roothtml += "<li>" + roots[i] + "</li>";
    }
    html += "</ul>";
    $("#originalNumbers").html(html);
    roothtml += "</ul>";
    $("#sqrtNumbers").html(roothtml);
};

// roots is now [1, 2, 3]
// numbers is still [1, 4, 9]

// map and filter
var filterdemo = function () {
    var numbers = [1, 2, 3, 4, 42, 121];
    var html = "The original numbers<br/><ul>";
    var doublehtml = "Double the odd numbers<br/><ul>";

    for (var i = 0; i < numbers.length; i++) {
        html += "<li>" + numbers[i] + "</li>";
    }
    html += "</ul>";
    $("#originalFilterNumbers").html(html);

    var newNumbers = numbers
        // TODO: restrict the list to just odd numbers

        .map(function (number) {
            return number * 2;
        });

    for (var i = 0; i < newNumbers.length; i++) {
        doublehtml += "<li>" + newNumbers[i] + "</li>";
    }
    doublehtml += "</ul>";
    $("#doubleOddNumbers").html(doublehtml);

};

// reduce
var reducedemo = function () {
    var numbers = [1, 2, 3, 4, 5, 6, 7];
    var html = "The original numbers<br/><ul>";
    for (var i = 0; i < numbers.length; i++) {
        html += "<li>" + numbers[i] + "</li>";
    }
    html += "</ul>";
    $("#originalReduceNumbers").html(html);

    // TODO: Use reduce to add up the numbers
    var totalNumber = 0;
    $("#sumReduceNumbers").html("The sum of all the numbers = " + totalNumber);

};
