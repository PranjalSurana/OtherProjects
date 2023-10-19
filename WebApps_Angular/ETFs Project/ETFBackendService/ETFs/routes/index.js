const express = require('express');
const router = express.Router();
const etfs = require('../modules/etfs');
const url = require('url');

/* GET home page. */
router.get('/', function (req, res, next) {
  res.render('index', { title: 'the ETF Service' });
});

// looks for filter query paramter (e.g.:?filter=price)
router.get('/etfs', (request, response, next) => {
  let result = null;
  let get_params = url.parse(request.url, true).query;
  console.log('got into etfs');

  if (Object.keys(get_params).length == 0) {
    // return all
    console.log('no params');
    response.setHeader('content-type', 'application/json');
    // sort by ticker symbol
    response.end(JSON.stringify(sortAllEtfsByTickerSymbol()));
  } 
  else {
    let key = Object.keys(get_params)[0]; // get first parameter only
    console.log("First key is: " + key);
    if (key != 'filter') {
      response.status(404).send('Illegal parameter!');
    }
    else {
      let err = null;
      let value = request.query[key];
      console.log('param: ' + value);
      try {
        if (!value) {
          result = sortAllEtfsByTickerSymbol();
        } 
        else {
          result = etfs.filter_by_arg(value);
        }
      } catch (err) {
        console.log("Caught exception.");
        //createError(500);
        response.status(500).send('Something broke!');
      }
      if (result && !err) {
        response.setHeader('content-type', 'application/json');
        response.status(200).end(JSON.stringify(result));
      }
      else if (!err) {
        response.status(404);
        response.render('error',{ 
          message: 'Not Found: 404',
          detail: 'Illegal filter value!'});
      }
    }
  }
});

function sortAllEtfsByTickerSymbol() {
  return etfs.listAll()
    .sort((a, b) => a.Ticker < b.Ticker ? -1 : 1);
}

module.exports = router;
