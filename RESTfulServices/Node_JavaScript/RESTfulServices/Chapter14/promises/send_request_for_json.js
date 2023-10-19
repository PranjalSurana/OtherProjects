// TODO: require request-promise-native instead of request
const request = require('request');

const serviceUrl = 'http://localhost:9876/todos';
const todoId = 1;

const getOptions = {
    method: 'GET',
    url: `${serviceUrl}/${todoId}`,
    headers: { 
        'User-Agent': 'Request-Promise' 
    },
    json: true // Automatically parses the JSON string in the response
};

const putOptions = {
    method: 'PUT',
    url: `${serviceUrl}/${todoId}`,
    headers: { 
        'User-Agent': 'Request-Promise',
        "Content-type": 'application/json; charset=UTF-8'
    }
};

// sample JSON response to GET request:
// { "id": 1,
//   "title": "Wash car",
//   "description": "Go to Scrub 'n Bubbles", 
//   "completed": false }

// TODO: Review the code below. Note how it implements the following use case:
//       1. Send a GET request for a to-do item
//       2. Modify the value of the to-do item's "completed" property
//       3. Send a PUT request to update the to-do item on the server

// TODO: replace the following call to request() with a call
//       to the equivalent request-promise function
// HINT: see the examples at https://www.npmjs.com/package/request-promise 
request(getOptions, function (err, response, todoItem) { // send GET request
    // TODO: replace this callback function with a call to then() and catch()
    if (err) {
        console.error(`Request to ${serviceUrl} failed: ${err}`);
    }
    else {
        // TODO: use the following code as the callback function for then()
        console.log(`To-do item current value: ${JSON.stringify(todoItem)}`);

        todoItem.completed = !todoItem.completed;  // flip completed value
        putOptions.body = JSON.stringify(todoItem);

        // TODO: replace the following call to request() with a call
        //       to the equivalent request-promise function
        // HINT: you'll need an explicit return statement to return 
        //       the value of the new function call
        request(putOptions, function (err, response, todoItem) { // send PUT request
            // TODO: replace this callback function with a call to a second then()
            if (err) {
                console.error(`Request to ${serviceUrl} failed: ${err}`);
            }
            else {
                // TODO: use the following code as the callback function for the second then()
                console.log(`To-do item updated value: ${todoItem}`);
            }
        });
    }
});
