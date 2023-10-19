/**
 * Integration specs for the Widget RESTful service.
 */

const axios = require('axios');
const dbUtils = require('../../src/dao/db-utils');

/* 
    Database initialization:
    delete from widgets;
    insert into widgets values (1, 'Low Impact Widget', 12.99, 2, 3);
    insert into widgets values (2, 'Medium Impact Widget', 42.99, 5, 5);
    insert into widgets values (3, 'High Impact Widget', 89.99, 10, 8);
    commit;
    select * from widgets order by id;
 */

// TODO Note the following widget definitions, which match the records in
//      the database. You can use these as expected values in your specs.
//      (no code changes required)
const widget1 = {id: 1, description: 'Low Impact Widget', price: 12.99, gears: 2, sprockets: 3};
const widget2 = {id: 2, description: 'Medium Impact Widget', price: 42.99, gears: 5, sprockets: 5};
const widget3 = {id: 3, description: 'High Impact Widget', price: 89.99, gears: 10, sprockets: 8};

const widgetsInDb = [widget1, widget2, widget3];

describe("RESTful controller integration tests for Widget CRUD operations:", () => {
    const baseUrl = 'http://localhost:3000/widgets';

    // TODO Note how the beforeEach() method re-initializes the database 
    //      before each spec
    //      (no code changes required)
    beforeEach(async () => {
        await initDb();
    });

    async function initDb() {
        const stmts = [
            "delete from widgets",
            "insert into widgets values (1, 'Low Impact Widget', 12.99, 2, 3)",
            "insert into widgets values (2, 'Medium Impact Widget', 42.99, 5, 5)",
            "insert into widgets values (3, 'High Impact Widget', 89.99, 10, 8)",
        ];
        await dbUtils.executeDml(stmts);
    }
   
    describe("retrieve all widgets", () => {
        // TODO Review the following spec, which sends a GET request 
        //      for all widgets to the Product API
        //      (no code changes required)
        it("succeeds", async () => {
            const rowCount = await dbUtils.countRowsInTable('widgets');

            const response = await axios.get(baseUrl);

            expect(response.status).toBe(200);
            expect(response.data).toBeTruthy();
            expect(response.data.length).toEqual(rowCount);
            expect(response.data).toEqual(widgetsInDb);
        });
    });

    describe("retrieve a widget", () => {
        // TODO Write a spec with a GET request that successfully retrieves 
        //      one widget by ID.




        // TODO Review the following spec, which verifies that an attempt to
        //      retrieve a widget using an invalid ID results in a status 400.
        //      (no code changes required)
        it("fails due to an invalid ID", async () => {
            try {
                await axios.get(`${baseUrl}/-1`);

                fail('GET with invalid ID should have failed');
            } 
            catch (err) {
                // Axios throws an error if the HTTP response status 
                // is not in the range 2xx
                expect(err.response.status).toEqual(400);

                // To make the spec more robust, we'll test the error message
                // with a regular expression (RE) match instead of string equality.
                // The RE below matches all of the following strings:
                //      bad widget ID 
                //      Widget has an invalid id
                //      widget's ID must be numeric
                expect(err.response.data.error).toMatch(/widget.*id/i);
            }
        });
    });

    describe("create a widget", () => {
        // TODO Review the following spec, which sends a POST request 
        //      to add a widget to the Product API
        //      (no code changes required)
        it("succeeds", async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            const newRowId = oldRowCount + 1;
            const newWidget = { id: newRowId, description: 'Very Groovy Widget', price: 99.99, gears: 9, sprockets: 99 };
        
            const response = await axios.post(baseUrl, newWidget);

            expect(response.status).toBe(200);
            expect(response.data.rowCount).toEqual(1);
            const newRowCount = await dbUtils.countRowsInTable('widgets');
            expect(newRowCount).toEqual(oldRowCount + 1);
            const queryResult = await dbUtils.countRowsInTableWhere('widgets',
                `   id = ${newRowId} 
                and description='Very Groovy Widget' 
                and price = 99.99
                and gears = 9 
                and sprockets = 99`);
            expect(queryResult).toEqual(1);
        });

        // TODO Write a spec that verifies that an attempt to add a widget
        //      using an incomplete request body results in a status 400.
    



    });

    describe("update a widget", () => {
        // TODO Write a spec with a PUT request that successfully 
        //      updates an existing widget in the database
        it("updates successfully", async () => {


        });

        // TODO Write a spec that verifies that an attempt to update a widget
        //      using an incomplete request body results in a status 400.




    });

    describe("delete a widget", () => {
        // TODO Write a spec for a successful DELETE request
        it("deletes successfully", async () => {


        });
    

        // TODO Write a spec that verifies that an attempt to delete a widget
        //      using an invalid ID results in a status 400.




    });

});
