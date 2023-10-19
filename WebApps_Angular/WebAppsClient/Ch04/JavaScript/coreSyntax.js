
var javaProgrammerHere = 10.32;
var $usually_write_perl = 'Hello';
var _systemsProgrammer = true;

let hour = 1;
var greeting = 'Good ' + (hour < 12) ? 'Morning' : 'Evening';

var numEntries = 100;
while (numEntries > 10) {
    // do something
    --numEntries;
}

for (let numEntries = 100; numEntries > 10; --numEntries) {
    // do something
}

if (numEntries > 100) {
    alert('Too many people in exhibit.');
} else if (numEntries == 0) {
    closeExhibit();
}

// String API

var name = 'O\'Keefe';

var lastChar = name.charAt(name.length - 1);

var aposPosition = name.indexOf('\'');  // -1 if not there

var num = parseInt('6');


// Arrays
// dynamically grows
var itemCosts = new Array();
itemCosts[0] = 51.2;
itemCosts[1] = 60.6;

// alternative, note: [], not {}
var itemCosts = [51.2, 60.6];

// length of an array
var numItems = itemCosts.length;

// resize array
itemCosts.length = 1;

// sparse array allowed
var itemCosts = [51.2, , , , 60.6];


function updateDetails(zipcode) {
    zipcode.name = zipcode.city + ', ' + zipcode.state;
}


var fullDetails = false;
function showDetails(zipcode) {
    var url = '../jaxrs/zipcode/json/' + zipcode;
    if (fullDetails) {
        var request = new XMLHttpRequest();
        request.open('GET', url, false);
        request.send();
    }
};


var fullDetails = false;
function showDetails(zipcode) {
    url = '../jaxrs/zipcode/json/' + zipcode;  // oops global
    if (fullDetails) {
        var request = new XMLHttpRequest();
        request.open('GET', url, false);
        request.send();
    }
};


var fullDetails = false;
function showDetails(zipcode) {
    let url = '../jaxrs/zipcode/json/' + zipcode;
    if (fullDetails) {
        let request = new XMLHttpRequest();
        request.open('GET', url, false);
        request.send();
    }
};


var fullDetails = new Object();
function updateDetails(zipcode) {
    fullDetails.zipcode = zipcode;
}

// Idioms
var numEntries = numStocks || pref.portfolioLength || 10;

if (numEntries == undefined) {
    numEntries = 10;
}

numEntries = 3;             // assignment
if (numEntries == 3);       // check equality
if (entry === this.entry);  // strict equality


//Objects
// creating an Object and setting its values
var item = new Object();
item.id = 23024;
item.cost = 60.6;

// alternative Perl-like syntax
var item = new Object();
item['id'] = 23024;
item['cost'] = 60.6;

// object-literal alternative syntax
var item = { id: 23024, cost: 60.6 };

// objects are hierarchical
// objects are hierarchical
var item = {
    id: 23024, cost: 60.6,
    location: { lat: 35.3, lon: -97.1 }
};

// alternate syntax
var item = new Object();
item.id = 23024;
item.cost = 60.6;
item.location = new Object();
item.location.lat = 35.3;
item.location.lon = -97.1;

// objects are like associative arrays
var item = {
    id: 23024, cost: 60.6,
    location: { lat: 35.3, lon: -97.1 }
};
var prop;
for (prop in item) {
    if (prop == 'cost') {
        // do something with item[prop]
    }
}

// properties can be added or deleted
item.salePrice = item.cost * 1.05;
delete item.location;

// defining classes
function Item(id_, cost_, price_) {
    var id = id_;
    var cost = cost_;
    var price = price_;
    this.getId = function () {
        return id;
    }
    this.getCost = function () {
        return cost;
    }
    this.getPrice = function () {
        return price;
    }
    this.getProfit = function () {
        return (price - cost);
    }
}

var item = new Item(23021, 30.2, 31.4);
var profit = item.getProfit();

// dialog boxes
alert('Please enter a valid state code');

var resp = confirm('Are you sure?');
if (resp) {
    // delete all the work
}

var resp = prompt('How many zipcodes?', '10');
if (resp != null && resp != '') {
    numZips = parseInt(resp);
}

// DOM
document.getElementById('songtitle').className = 'highlight';
var classes = document.getElementById('songtitle').classList
classes.add('highlight');
classes.remove('highlight');

document.getElementById('ad').setAttribute('src', urlToShow);

// Math
var lower = Math.floor(32.2); // 32
var rnd = Math.random(); // 0 to 1

// Date
var now = new Date();      // a Date object
var alsoNow = Date.now();  // a number (milliseconds since epoch)
var fixedTime = new Date(2019, 1, 31, 12, 1, 31, 300);
var timeString = fixedTime.toLocaleString('de-DE');

// Number
var maxInt = Number.MAX_SAFE_INTEGER;
var num = Number.parseFloat("1234.56");
var output = num.toFixed(1);    // "1234.6"
var exp = num.toExponential(3); // "1.235e+3"
var fmt = num.toLocaleString('fr-FR', num); // "1 234,56"

// Event Handlers
window.addEventListener('DOMContentLoaded', (event) => {
    console.log('DOM fully loaded and parsed');
});

window.onload = function () {
    document.getElementById('active').onmouseover = insertSong;
}

document.getElementById('active').addEventListener('mouseover', insertSong);

window.addEventListener('load', showPhoto);


// Security
document.domain = 'acme.com';
