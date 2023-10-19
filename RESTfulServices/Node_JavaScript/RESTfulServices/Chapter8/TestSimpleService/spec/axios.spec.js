const axios = require('axios');

const contact1 = {
    id: 1,
    firstname: "Joe",
    lastname: "Smith",
    title: "Mr.",
    company: "Dev Inc.",
    jobtitle: "Developer",
    primarycontactnumber: "+359777123456",
    othercontactnumbers: [
        "+359777456789",
        "+359777112233"
    ],
    primaryemailaddress: "joe.smith@xyz.com",
    emailaddresses: [
        "j.smith@xyz.com"
    ],
    groups: [
        "Dev",
        "Family"
    ]
};

const contact2 = {
    id: 2,
    firstname: "John",
    lastname: "Douglas",
    title: "Mr.",
    company: "Fidelity",
    jobtitle: "Manager",
    primarycontactnumber: "+359777223344",
    othercontactnumbers: [],
    primaryemailaddress: "john.douglas@xyz.com",
    emailaddresses: [
        "j.douglas@xyz.com"
    ],
    groups: [
        "Dev"
    ]
};

const abbyContact = {
    id: 3,
    firstname: "Abigail",
    lastname: "Johnson",
    title: "Mrs.",
    company: "Fidelity",
    jobtitle: "CEO",
    primarycontactnumber: "+15551231234",
    othercontactnumbers: [],
    primaryemailaddress: "Abigail.Johnson@fidelity.com",
    emailaddresses: [
    "CEO@fidelity.com"
    ],
    groups: [
    "Executives"
    ]
};

const contact2Update = {
    id: 2,
    firstname: "Jack",
    lastname: "Douglas",
    title: "Mr.",
    company: "Fidelity",
    jobtitle: "Executive",
    primarycontactnumber: "+359777223344",
    othercontactnumbers: [],
    primaryemailaddress: "john.douglas@fidelity.com",
    emailaddresses: [
        "j.douglas@fidelity.com"
    ],
    groups: [
        "Dev"
    ]
};

const allContacts = [ contact1, contact2 ];

describe('Testing Axios functions', () => {
    const baseUrl = 'http://localhost:3000/contacts';

    // TODO Note the URL for resetting the mock database to its initial state
    //      (no code change required)
    const restartUrl = 'http://localhost:3000/restart';

    // TODO Add a call to beforeEach() to reset the DAO before every spec
    beforeEach(async () => {
        await axios.options(restartUrl);
    });

    // TODO Send a GET request for all contacts.
    //      Note that the it() callback is 'async'
    //      (no code change required)
    it('GET all contacts', async () => {
        try {  // If you use await, you need to wrap the code in a try/catch block

            // TODO Call the Axios get() function to send a GET request to the baseUrl and
            //      save the response in the const 'resp'.
            // HINT Remember that axios.get() returns a Promise, so you need to 'await'
            //      the call to get()
            // HINT See slide 8-18
            const resp = await axios.get(baseUrl);
            const data = resp.data;
            console.log(data);
            // add your code here

            // Verify the response data. The response object looks like this:
            //  {
            //      "status": 200,
            //      "data": [ {"id": 1, "firstname": "John", ...}, {"id": 2, "firstname": "Jack", ...} ]
            //  }
            expect(resp.status).toBe(200);
            expect(resp.data.length).toBe(2);
            expect(resp.data).toEqual(allContacts);
        }
        catch(err) {
            fail(`${err}: Check whether SimpleService is running at ${baseUrl}`);
        }
    });

//    // TODO Write a spec that gets one contact by ID.
//    // HINT The response object looks like this:
//    //  {
//    //      "status": 200,
//    //      "data": {"id": 2, "firstname": "Jack", ...}
//    //  }
    it('GET one contact by ID', async () => {
            // TODO send a GET request with Axios
            // HINT The URL will be similar to the one used in the previous spec, but
            //      you will need to add the ID to the end of the URL.
            // HINT If you use await, you need to wrap the code in a try/catch block
        try {
            const resp = await axios.get(baseUrl + '/2');
            console.log(resp.data)
            expect(resp.status).toBe(200);
            expect(resp.data).toEqual(contact2);
        }

        catch(err) {
            fail(`${err}: Check whether SimpleService is running at ${baseUrl}`);
        }
            // TODO Use Jasmine matchers to verify the response data:
            //      Verify the status is 200
            //      Verify the data is the contact that you requested


    });
//
//    // TODO Add a spec for adding a new contact. You can use the 'abbyContact'
//    //      object defined above for the new contact data.
//    // HINT This will require two steps:
//    //      1. Send a POST request (HINT: see slide 8-17) and verify the response has
//    //         the correct values for status and data
//    //      2. To verify that the "database" was updated, send a GET request for
//    //         all contacts, then verify that the contact array length is now 3,
//    //         and that the last array item is the contact you added
//    it('POST a new contact', async () => {
//
//
//
//
//    });
//
//    // TODO Add a spec for updating an existing contact. You can use the 'contact2Update'
//    //      object defined above for the new contact data. Remember to verify that
//    //      the "database" was updated.
//    it('PUT an existing contact', async () => {
//
//
//
//
//    });
//
//    // TODO Add a spec for deleting a contact. Remember to verify that
//    //      the "database" was updated.
//    it('DELETE one contact', async () => {
//
//
//
//
//    });
//
//    /*
//     * BONUS TODO: examine the following specs. They have replaced async and await
//     * with explicit calls to Promise.then() and catch(). Note that the code
//     * is considerably more complex than the equivalent code with async and await.
//     *
//     * The it() callback's doneWithAsyncWork parameter is a function that Jasmine
//     * passes when it calls the callback. If a spec calls asynchronous functions but
//     * doesn't use async/await, it must call the doneWithAsyncWork function when
//     * the spec is complete, otherwise the spec will time out.
//     */
//
//    it('GET all contacts', doneWithAsyncWork => {
//        // send a GET request with Axios
//        axios.get(baseUrl)
//             .then(resp => {
//                expect(resp.status).toBe(200);
//                expect(resp.data.length).toBe(2);
//                expect(resp.data).toEqual(allContacts);
//                doneWithAsyncWork();
//             })
//             .catch(err => {
//                // The 'catch' method is executed only when the request fails to complete.
//                fail(`${err}: Check whether SimpleService is running at ${baseUrl}`);
//                doneWithAsyncWork();
//             });
//    });
//
//    it('GET one contact by ID', doneWithAsyncWork => {
//        // send a GET request with Axios
//        axios.get(`${baseUrl}/2`)
//             .then(resp => {
//                expect(resp.status).toBe(200);
//                expect(resp.data).toEqual(allContacts[1]);
//                doneWithAsyncWork();
//             })
//             .catch(err => {
//                fail(`Caught error getting data: ${err}`);
//                doneWithAsyncWork();
//             });
//    });
//
//    it('POST a new contact', doneWithAsyncWork => {
//        axios.post(baseUrl, abbyContact)
//            .then(resp => {
//                expect(resp.status).toBe(200);
//                expect(resp.data).toEqual({rowCount: 1});
//
//                // verify the new contact was added
//                axios.get(baseUrl)
//                    .then(resp => {
//                        expect(resp.data.length).toBe(3);
//                        expect(resp.data.slice(0, 2)).toEqual(allContacts);
//                        expect(resp.data[2]).toEqual(abbyContact);
//                        doneWithAsyncWork();
//                    });
//            })
//            .catch(err => {
//                fail(`Caught error posting data: ${err}`);
//                doneWithAsyncWork();
//            });
//    });
//
//    it('PUT an existing contact', doneWithAsyncWork => {
//        axios.put(baseUrl, contact2Update)
//            .then(resp => {
//                expect(resp.status).toBe(200);
//                expect(resp.data).toEqual({rowCount: 1});
//
//                // verify the contact was updated
//                axios.get(baseUrl)
//                    .then(resp => {
//                        expect(resp.data.length).toBe(2);
//                        expect(resp.data[0]).toEqual(allContacts[0]);
//                        expect(resp.data[1]).toEqual(contact2Update);
//                        doneWithAsyncWork();
//                    });
//            })
//            .catch(err => {
//                fail(`Caught error putting data: ${err}`);
//                doneWithAsyncWork();
//            });
//    });
//
//    it('DELETE one contact', doneWithAsyncWork => {
//        // send a DELETE request with Axios
//        axios.delete(`${baseUrl}/2`)
//             .then(resp => {
//                expect(resp.status).toBe(200);
//
//                // verify the contact was deleted
//                axios.get(baseUrl)
//                    .then(resp => {
//                        expect(resp.data.length).toBe(1);
//                        expect(resp.data[0]).toEqual(allContacts[0]);
//                        doneWithAsyncWork();
//                    });
//             })
//             .catch(err => {
//                fail(`Caught error deleting data: ${err}`);
//                doneWithAsyncWork();
//             });
//    });

});