const express = require('express');

// Set up routing for contact operations
function router(controller) {
    const router = express.Router();
    router.get('/contacts', controller.getAllContacts.bind(controller));
    router.get('/contacts/:id', controller.getContact.bind(controller));
    router.post('/contacts', controller.addContact.bind(controller));
    router.put('/contacts', controller.updateContact.bind(controller));
    router.delete('/contacts/:id', controller.deleteContact.bind(controller));
    return router;
}

module.exports = router;
