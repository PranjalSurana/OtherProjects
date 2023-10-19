var express = require("express");

var router = express.Router();

 

function generateID(client) {

  return (

    Math.abs(

      client.email.split("").reduce(function (a, b) {

        a = (a << 5) - a + b.charCodeAt(0);

        return a & a;

      }, 0)

    ) + 123456

  );

}

 

/* GET users listing. */

router.post("/register", function (req, res, next) {

  console.log(generateID(req.body));

  console.log(req.body);

  res.json({

    ...req.body,

    id: generateID(req.body),

  });

});

 

module.exports = router;
