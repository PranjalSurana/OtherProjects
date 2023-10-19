/**
 * Test DAO for CRUD operations on Products
 */

const fs = require('fs');

const contactFile = `data/contacts.json`;

class ProductDao {
    constructor() {
        // read mock data from JSON file
        const fileContents = fs.readFileSync(contactFile, 'utf-8');
        this.contacts = JSON.parse(fileContents);
    }

    queryForAllContacts() {
        return this.contacts;
    }

    queryForContact(id) {
        // Search for the contact with the given id
        const searchResult = this.contacts.filter(contact => contact.id == id);
        // Return the first contact found or null
        const contact = searchResult ? searchResult[0] : null;
        return contact;
    }

    createContact(contact) {
        this.contacts.push(contact);
        return 1;
    }

    updateContact(contact) {
        let updateCount = 0;
        const searchResult = this.contacts.filter(w => w.id == contact.id);
        if (searchResult.length > 0) {  // contact exists
            // Replace the contact with the updated contact
            this.contacts = this.contacts.map(w => w.id == contact.id ? contact : w);
            updateCount = 1;
        }
        return updateCount;
    }

    deleteContact(id) {
        const previousLength = this.contacts.length;
        // Attempt to remove the contact with the given id
        this.contacts = this.contacts.filter(contact => contact.id != id);
        // Return 1 if the contact was removed, 0 otherwise
        return this.contacts.length < previousLength ? 1 : 0;
    }

    closeConnection(connection) {
    }

    shutdown() {
    }
}

module.exports = ProductDao;


