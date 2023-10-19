const express = require('express');
const router = express.Router();
const fs = require('fs');

const contactList = initContacts();

function initContacts() {
    const jsonData = fs.readFileSync('./data/contact.json');
    return JSON.parse(jsonData).result;
}

function queryByArg(arg, value) {
    console.log('queryByArg: ' + arg + '=' + value);

    for (let contact of contactList) {
        if (contact[arg] == value) {
            return contact;
        }
    }
    return null;
};

function getContactNames() {
    console.log('getContactNames');

    const results = new Array();
    for (let contact of contactList) {
        results.push(contact.firstName);
    }
    return results;
};

router.get('/', function (request, response, next) {
    const firstName = request.query.firstName;
    console.log('contacts params: ' + firstName);

    if (firstName == undefined) {
        console.log('list all names');
        response.setHeader('content-type', 'application/json');
        response.end(JSON.stringify(getContactNames()));
    } else {
        const contact = queryByArg('firstName', firstName);
        if (contact == null) {
            response.sendStatus(404);
        } else {
            response.setHeader('content-type', 'application/json');
            response.end(JSON.stringify(contact));
        }
    }
});

module.exports = router;
