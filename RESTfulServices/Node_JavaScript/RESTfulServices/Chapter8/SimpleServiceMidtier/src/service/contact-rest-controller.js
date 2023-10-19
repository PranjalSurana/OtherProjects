/**
 * A Node.js RESTful API for CRUD operations on a contact database.
 * 
 * To start the service:
 *      npm start
 */

// Use Express to implement a REST API
// See https://expressjs.com/en/4x/api.html
const express = require('express');

// TODO require the Axios module


// TODO delete the "require" of the DAO module
const ContactDao = require('../dao/mock-contact-dao');

class ContactsRestController {
    constructor() { 
        this.port = process.env.PORT || 3000;
        this.backendApiUrl = "http://localhost:8080/backend-api/contacts";

        // TODO Delete the creation of the DAO
        this.contactDao = new ContactDao();

        // Define a regular expression that matches strings consisting of digits only
        this.onlyDigitsRegExp = /^\d+$/;

        this.app = express();

        // Configure Express to populate a request body from JSON input
        this.app.use(express.json());

        // Set up routing for contact operations
        const router = express.Router();
        router.get('/contacts', this.getAllContacts.bind(this));
        router.get('/contacts/:id', this.getContact.bind(this));
        router.post('/contacts', this.addContact.bind(this));
        router.put('/contacts', this.updateContact.bind(this));
        router.delete('/contacts/:id', this.deleteContact.bind(this));
        
        router.options('/restart', this.restart.bind(this));
        router.options('/stop', this.stop.bind(this));

        this.app.use('/', router);
    }

    start() {
        this.app.listen(this.port, 
            () => console.log(`Service for contact CRUD operations listening on port ${this.port}`))
    }

    //============================== Contact methods ============================

    getAllContacts(req, res) {
        try {
            // TODO Replace the call to the DAO method with an HTTP request
            //      to the back-end API. Your code may look like this:
            //          const backEndResp = ...
            //          const contacts = backEndResp.data;
            // HINT Axios functions all return Promises, so you'll need to 'await'
            //      each Axios function call.
            // HINT Remember to add 'async' to the enclosing method definition.
            // HINT See slide 8-18
            const contacts = this.contactDao.queryForAllContacts();

            // Don't change the following line
            res.json(contacts);
        }
        catch (err) {
            console.error(`error on GET contacts: ${err}`);
            res.status(500).json({error: err});
        }
    }

    getContact(req, res) {
        // Read id from the URL
        const id = req.params.id;
        if (this.onlyDigitsRegExp.test(id)) {  // test for only digits
            try {
                // TODO Replace the call to the DAO method with an HTTP request
                //      to the back-end API.
                // HINT The Axios call will be exactly the same as in getAllContacts(),
                //      except that you'll need to add the contact ID to the URL.
                // HINT Did you remember to add 'async' to the enclosing method definition?
                const contact = this.contactDao.queryForContact(id);

                // TODO Add a call to res.json() and pass the contact as the argument


                // If the back-end API doesn't find a contact with the given ID,
                // it returns a status of 406, which causes Axios to throw an
                // error. So instead of checking for an empty contact here, you
                // need to add a test for a 406 status in the 'catch' block. 
                //
                // TODO Delete the following if/else block:
                if (contact) {
                    res.json(contact);
                }
                else {
                    res.status(406).json({ error: `Contact ${id} not found` }); // 406 - Not Acceptable
                }
            }
            catch (err) {
                // TODO Delete the following two lines
                console.error(`error on GET contact ${id}: ${err}`);
                res.status(500).json({error: err});

                // TODO If err.response.status from the backend service is 406, 
                //      return a 406 status
                // HINT See slide 8-18


                // TODO Else return status 500


            }
        }
        else {
            res.status(400).json({error: `bad contact id ${id}`});
        }
    }

    addContact(req, res) {
        const contact = req.body;
        if (contact.firstname && contact.lastname && contact.primarycontactnumber 
                && contact.primaryemailaddress) {
            try {
                // TODO Replace the call to the DAO method with an HTTP request
                //      to the back-end API.
				// HINT The JSON response from the backend API looks like this:
				//      { "rowCount": 1 }
				// HINT See slide 8-17
                const rowsAffected = this.contactDao.createContact(contact);

                res.json({ rowCount: rowsAffected });
            }
            catch (err) {
                console.error(`error on POST contact ${JSON.stringify(contact)}\n: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `contact ${JSON.stringify(contact)} is not fully populated`});
        }
    }

    updateContact(req, res) {
        const contact = req.body;
        if (contact.id && contact.firstname && contact.lastname && contact.primarycontactnumber 
                && contact.primaryemailaddress) {
            try {
                // TODO Replace the call to the DAO method with an HTTP request
                //      to the back-end API.
                const rowsAffected = this.contactDao.updateContact(contact);

                res.json({ rowCount: rowsAffected });
            }
            catch (err) {
                console.error(`error on PUT contact ${JSON.stringify(contact)}\n: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `contact ${JSON.stringify(contact)} is not fully populated`});
        }
    }

    deleteContact(req, res) { // Read id from the URL
        const id = req.params.id;

        if (this.onlyDigitsRegExp.test(id)) {  // test for only digits
            try {
                // TODO Replace the call to the DAO method with an HTTP request
                //      to the back-end API.
                const rowsAffected = this.contactDao.deleteContact(id);

                res.json({ rowCount: rowsAffected });
            }
            catch (err) {
                console.error(`error on DELETE contact ${id}: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `bad contact id ${id}`});
        }
    }

    stop(req, res) {
        try {
            // TODO Replace the call to the DAO method with the following HTTP request
            //      to the back-end API:
            //          await axios.options(this.backendApiUrl + '/shutdown');
            this.contactDao.shutdown();

            if (res) {
                res.sendStatus(204);
            }
        }
        catch (err) {
            console.error(`error shutting down: ${err}`);
            res.status(500).json({error: err});
        }
    }

    /* 
     * Our test cases will use restart to restore the mock database
     * to its initial state before each spec.
     */
    restart(req, res) {
        // TODO Replace the call to the DAO method with the following HTTP request
        //      to the back-end API:
        //          await axios.options(this.backendApiUrl + '/restart');
        this.contactDao = new ContactDao();

        res.sendStatus(204);
    }
}

module.exports = ContactsRestController;

// When a file is run directly from Node.js, require.main is set to its module. 
// So if require.main === module, this file is being executed as a standalone program.
// Otherwise it is being "required" by another module; e.g., a spec module
if (require.main === module) {
    const api = new ContactsRestController();
    api.start();
}
