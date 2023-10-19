/**
 * A Node.js RESTful API for CRUD operations on a widget database.
 * 
 * To start the service:
 *      npm start
 */

// Use Express to implement a REST API
// See https://expressjs.com/en/4x/api.html
const express = require('express');

const ContactDao = require('../dao/mock-contact-dao');

class ContactRestController {
    constructor() { 
        this.port = process.env.PORT || 3000;

        this.contactDao = new ContactDao();

        // Define a regular expression that matches strings consisting of digits only
        this.onlyDigitsRegExp = /^\d+$/;

        this.app = express();

        // Configure Express to populate a request body from JSON input
        this.app.use(express.json());

        // Start of router configuration 
        const router = express.Router();
        router.get('/contacts', this.getAllContacts.bind(this));
        router.get('/contacts/:id', this.getContact.bind(this));
        router.post('/contacts', this.addContact.bind(this));
        router.put('/contacts', this.updateContact.bind(this));
        router.delete('/contacts/:id', this.deleteContact.bind(this));
        
        router.options('/restart', this.restart.bind(this));
        router.options('/stop', this.stop.bind(this));		
        // End of router configuration

        this.app.use('/', router);
    }

    start() {
        this.app.listen(this.port, 
            () => console.log(`Service for contact CRUD operations listening on port ${this.port}`))
    }

    //============================== Contact methods ============================

    getAllContacts(req, res) {
        try {
            const contacts = this.contactDao.queryForAllContacts();
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
                const widget = this.contactDao.queryForContact(id);

                if (widget) {
                    res.json(widget);
                }
                else {
                    res.status(406).json({ error: `Contact ${id} not found` }); // 406 - Not Acceptable
                }
            }
            catch (err) {
                console.error(`error on GET widget ${id}: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `bad widget id ${id}`});
        }
    }

    addContact(req, res) {
        const w = req.body;
        if (w.id && w.firstname && w.lastname && w.title && w.company) {
            try {
                const rowCount = this.contactDao.createContact(w);

                res.json({ rowCount: rowCount });
            }
            catch (err) {
                console.error(`error on POST widget ${JSON.stringify(w)}\n: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `widget ${JSON.stringify(w)} is not fully populated`});
        }
    }

    updateContact(req, res) {
        const w = req.body;
        if (w.id) {
            try {
                const rowCount = this.contactDao.updateContact(w);

                res.json({ rowCount: rowCount });
            }
            catch (err) {
                console.error(`error on PUT widget ${JSON.stringify(w)}\n: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `widget ${JSON.stringify(w)} is not fully populated`});
        }
    }

    deleteContact(req, res) {
        // Read id from the URL
        const id = req.params.id;

        if (this.onlyDigitsRegExp.test(id)) {  // test for only digits
            try {
                const rowCount = this.contactDao.deleteContact(id);

                res.json({ rowCount: rowCount });
            }
            catch (err) {
                console.error(`error on DELETE widget ${id}: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `bad widget id ${id}`});
        }
    }

    stop(req, res) {
        try {
            this.contactDao.shutdown();
            if (res) {
                res.status(200);
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
        this.contactDao = new ContactDao();
        res.status(204).end();
    }
}

module.exports = ContactRestController;

// When a file is run directly from Node.js, require.main is set to its module. 
// So if require.main === module, this file is being executed as a standalone program.
// Otherwise it is being "required" by another module; e.g., a spec module
if (require.main === module) {
    const api = new ContactRestController();
    api.start();
}
