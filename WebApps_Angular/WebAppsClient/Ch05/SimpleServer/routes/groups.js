const express = require('express');
const router = express.Router();
const fs = require('fs');

const contactList = initContacts();
const groups = initGroups(contactList);

function initContacts() {
    const jsonData = fs.readFileSync('./data/contact.json');
    return JSON.parse(jsonData).result;
}

function initGroups(contacts) {
    let groupSet = new Set();
    for (let contact of contacts) {
        for (let group of contact.groups) {
            groupSet.add(group);
        }
    }
    return groupSet;
}

function queryByGroup(group) {
    console.log('queryByGroup: ' + group);

    const results = new Array();
    for (let contact of contactList) {
        if (contact.groups.indexOf(group) !== -1 ) {
            results.push(contact);
        }
    }
    return results;
};

router.get('/', function (request, response, next) {
    console.log('groups list request');

    if (groups.size === 0) {
        response.sendStatus(404);
    } else {
        response.setHeader('content-type', 'application/json');
        const results = new Array();
        for (let group of groups.values()) {
            results.push(group);
        }
        response.end(JSON.stringify(results));
    }
});

router.get('/:group', function (request, response, next) {
    const group = request.params.group;
    console.log('group=' + group);

    const results = queryByGroup(group);
    if (results.size === 0) {
        response.sendStatus(404);
    } else {
        response.setHeader('content-type', 'application/json');
        response.end(JSON.stringify(results));
    }
});

module.exports = router;
