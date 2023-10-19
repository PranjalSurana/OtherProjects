/**
 * Specs for the Contact RESTful endpoint.
 */

const ContactRestController = require('../../src/service/contact-rest-controller');

console.error = arg => {}

const testContacts = [
    {
        "id": 1,
        "firstname": "Joe",
        "lastname": "Smith",
        "title": "Mr.",
        "company": "Dev Inc.",
        "jobtitle": "Developer",
        "primarycontactnumber": "+359777123456",
        "othercontactnumbers": [
            "+359777456789",
            "+359777112233"
        ],
        "primaryemailaddress": "joe.smith@xyz.com",
        "emailaddresses": [
            "j.smith@xyz.com"
        ],
        "groups": [
            "Dev",
            "Family"
        ]
    },
    {
        "id": 2,
        "firstname": "John",
        "lastname": "Douglas",
        "title": "Mr.",
        "company": "Fidelity",
        "jobtitle": "Manager",
        "primarycontactnumber": "+359777223344",
        "othercontactnumbers": [],
        "primaryemailaddress": "john.douglas@xyz.com",
        "emailaddresses": [
            "j.douglas@xyz.com"
        ],
        "groups": [
            "Dev"            
        ]
    }
];

describe('RESTful controller unit tests for Contact CRUD operations:', () => {
    let controller;
    let mockDao;
    let mockHttpResponse;

    beforeEach(() => {
        mockDao = jasmine.createSpyObj('mockDao', ['queryForAllContacts', 'queryForContact', 'createContact', 
                                        'updateContact', 'deleteContact', 'shutdown']);

        controller = new ContactRestController();
        controller.contactDao = mockDao;

        mockHttpResponse = jasmine.createSpyObj('mockHttpResponse', ['status', 'json']);

        // The mock status() method needs to return a reference to the 
        // mock response so it can be chained with other calls:
        //    res.status(500).json(...)
        mockHttpResponse.status.and.returnValue(mockHttpResponse);
    });

    describe('retrieve all contacts', () => {
        it('succeeds', () => {
            mockDao.queryForAllContacts.and.returnValue(testContacts);
            const req = { };

            controller.getAllContacts(req, mockHttpResponse);
        
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith(testContacts);
        });

        it('fails due to a DAO exception', () => {
            mockDao.queryForAllContacts.and.throwError('error');
            const req = { };

            controller.getAllContacts(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);

            // We could test whether send() was called, but we don't really
            // care about the body of the response as long as the status is 500.
            // A best practice is to keep "white box" testing (that is, tests that
            // depend on the implementation of a method) to a minimum.
        });
    });

    describe('retrieve one contact by ID', () => {
        it('succeeds', () => {
            mockDao.queryForContact.and.returnValue(testContacts[0]);
            const req = { params: { id: 1 } };

            controller.getContact(req, mockHttpResponse);
        
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith(testContacts[0]);
        });

        it('fails because contact is not found', () => {
            const req = { params: { id: 999 } };

            controller.getContact(req, mockHttpResponse);
        
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(406);
        });

        it('fails due to an invalid ID', () => {
            const req = { params: { id: -1 } };

            controller.getContact(req, mockHttpResponse);
        
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.queryForContact).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', () => {
            mockDao.queryForContact.and.throwError('error');
            const req = { params: { id: 1 } };

            controller.getContact(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });

    });

    describe('create a contact', () => {
        it('succeeds', () => {
            mockDao.createContact.and.returnValue(1);
            const req = { 
                body: { "id": 1, "firstname": "Joe", "lastname": "Smith", "title": "Mr.", "company": "Dev Inc.", 
                    "jobtitle": "Developer", "primarycontactnumber": "+359777123456", "othercontactnumbers": [
                        "+359777456789", "+359777112233" ], "primaryemailaddress": "joe.smith@xyz.com", 
                    "emailaddresses": [ "j.smith@xyz.com" ], "groups": [ "Dev", "Family" ] }
            };
        
            controller.addContact(req, mockHttpResponse);

            expect(mockDao.createContact).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('succeeds with returned row count 0', () => {
            mockDao.createContact.and.returnValue(0);
            const req = { 
                body: { "id": 1, "firstname": "Joe", "lastname": "Smith", "title": "Mr.", "company": "Dev Inc.", 
                    "jobtitle": "Developer", "primarycontactnumber": "+359777123456", "othercontactnumbers": [
                        "+359777456789", "+359777112233" ], "primaryemailaddress": "joe.smith@xyz.com", 
                    "emailaddresses": [ "j.smith@xyz.com" ], "groups": [ "Dev", "Family" ] }
            };
        
            controller.addContact(req, mockHttpResponse);

            expect(mockDao.createContact).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 0});
        });

        it('fails due to an empty request body', () => {
            const req = { body: { } };

            controller.addContact(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.createContact).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', () => {
            mockDao.createContact.and.throwError('error');
            const req = { 
                body: { "id": 1, "firstname": "Joe", "lastname": "Smith", "title": "Mr.", "company": "Dev Inc.", 
                    "jobtitle": "Developer", "primarycontactnumber": "+359777123456", "othercontactnumbers": [
                        "+359777456789", "+359777112233" ], "primaryemailaddress": "joe.smith@xyz.com", 
                    "emailaddresses": [ "j.smith@xyz.com" ], "groups": [ "Dev", "Family" ] }
            };

            controller.addContact(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('update a contact', () => {
        it('succeeds', () => {
            mockDao.updateContact.and.returnValue(1);
            const req = { 
                body: { "id": 1, "firstname": "Joe", "lastname": "Smith", "title": "Mr.", "company": "Dev Inc.", 
                    "jobtitle": "Developer", "primarycontactnumber": "+359777123456", "othercontactnumbers": [
                        "+359777456789", "+359777112233" ], "primaryemailaddress": "joe.smith@xyz.com", 
                    "emailaddresses": [ "j.smith@xyz.com" ], "groups": [ "Dev", "Family" ] }
            };
        
            controller.updateContact(req, mockHttpResponse);

            expect(mockDao.updateContact).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('fails due to an empty request body', () => {
            const req = { body: { } };

            controller.updateContact(req, mockHttpResponse);
                
            expect(mockDao.updateContact).not.toHaveBeenCalled();
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
        });

        it('fails due to a DAO exception', () => {
            mockDao.updateContact.and.throwError('error');
            const req = { 
                body: { id: 99, firstname: "Abby", lastname: "Johnson", 
                        primarycontactnumber: "+18765551212", primaryemailaddress: "abbyj@fmr.com"}
            };

            controller.updateContact(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('delete a contact', () => {
        it('succeeds', () => {
            mockDao.deleteContact.and.returnValue(1);
            const req = { params: { id: 1 } };

            controller.deleteContact(req, mockHttpResponse);
                
            expect(mockDao.deleteContact).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('fails due to an invalid id', () => {
            const req = { params: { id: -1 } };

            controller.deleteContact(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.deleteContact).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', () => {
            mockDao.deleteContact.and.throwError('error');
            const req = { params: { id: 1 } };

            controller.deleteContact(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('shutdown the API', () => {
        it('succeeds', () => {
            const req = { };

            controller.stop(req, mockHttpResponse);
 
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(204);
        });

        it('fails due to a DAO exception', () => {
            mockDao.shutdown.and.throwError('error');
            const req = { };

            controller.stop(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });
});
