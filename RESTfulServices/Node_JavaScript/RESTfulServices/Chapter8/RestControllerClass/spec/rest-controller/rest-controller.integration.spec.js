/**
 * Integration specs for the Widget RESTful service.
 */

const dbUtils = require('../../src/dao/db-utils');
const axios = require('axios');

/* 
    Database initialization:
    delete from widgets;
    insert into widgets values (1, 'Low Impact Widget', 12.99, 2, 3);
    insert into widgets values (2, 'Medium Impact Widget', 42.99, 5, 5);
    insert into widgets values (3, 'High Impact Widget', 89.99, 10, 8);
    commit;
    select * from widgets order by id;
 */

const widget1 = {id: 1, description: 'Low Impact Widget', price: 12.99, gears: 2, sprockets: 3};
const widget2 = {id: 2, description: 'Medium Impact Widget', price: 42.99, gears: 5, sprockets: 5};
const widget3 = {id: 3, description: 'High Impact Widget', price: 89.99, gears: 10, sprockets: 8};

const widgetsInDb = [widget1, widget2, widget3];

describe("RESTful controller integration tests for Widget CRUD operations:", () => {
    const baseUrl = 'http://localhost:3000/widgets';

    beforeEach(async () => {
        await initDb();   // re-initialize the database before each spec
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
        it("succeeds", async () => {
            const response = await axios.get(`${baseUrl}/1`);

            expect(response.status).toBe(200);
            expect(response.data).toEqual(widget1);
        });

        it("fails due to an invalid ID", async () => {
            try {
                await axios.get(`${baseUrl}/-1`);

                fail('GET with invalid ID should have failed');
            } 
            catch (err) {
                expect(err.response.status).toEqual(400);
                expect(err.response.data.error).toMatch(/[Ww]idget.*[Ii][Dd]/);
            }
        });
    });

    describe("create a widget", () => {
        it("succeeds", async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            const newRowId = oldRowCount + 1;
            const newWidget = {
                id: newRowId,
                description: 'Very Groovy Widget',
                price: 999,
                gears: 9,
                sprockets: 99
            };
        
            const response = await axios.post(baseUrl, newWidget);

            expect(response.status).toBe(200);
            expect(response.data.rowCount).toEqual(1);
            const newRowCount = await dbUtils.countRowsInTable('widgets');
            expect(newRowCount).toEqual(oldRowCount + 1);
            const queryResult = await dbUtils.countRowsInTableWhere('widgets',
                `   id = ${newRowId} 
                and description='Very Groovy Widget' 
                and price = 999
                and gears = 9 
                and sprockets = 99`);
            expect(queryResult).toEqual(1);
        });

        it("fails due to an invalid request body", async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            const newWidget = {
                id: 999,
                description: 'Very Groovy Widget',
            };
            try {
                const response = await axios.post(baseUrl, newWidget);

                fail('POST with incomplete widget should have failed');
            } 
            catch (err) {
                expect(err.response.status).toEqual(400);
                expect(err.response.data.error).toMatch(/[Nn]ot.*populated/);
                const newRowCount = await dbUtils.countRowsInTable('widgets');
                expect(newRowCount).toEqual(oldRowCount);
            }
        });
    });

    describe("update a widget", () => {
        it("succeeds", async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            const updatedWidget = {
                id: 1,
                description: 'Quite Awesome Widget',
                price: 888,
                gears: 8,
                sprockets: 88
            };

            const response = await axios.put(baseUrl, updatedWidget);

            expect(response.status).toBe(200);
            expect(response.data.rowCount).toEqual(1);            
            const newRowCount = await dbUtils.countRowsInTable('widgets');
            expect(newRowCount).toEqual(oldRowCount);
            const queryResult = await dbUtils.countRowsInTableWhere('widgets',
                `   id = 1 
                and description='Quite Awesome Widget' 
                and price = 888
                and gears = 8 
                and sprockets = 88`);
            expect(queryResult).toEqual(1);
        });

        it("fails due to an invalid request body", async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            const updatedWidget = {
                description: 'Quite Awesome Widget',
                price: 888,
                gears: 8,
                sprockets: 88
            };
            try {
                await axios.put(baseUrl, updatedWidget);

                fail('PUT with incomplete widget should have failed');
            } 
            catch (err) {
                expect(err.response.status).toEqual(400);
                expect(err.response.data.error).toMatch(/[Nn]ot.*populated/);
                const newRowCount = await dbUtils.countRowsInTable('widgets');
                expect(newRowCount).toEqual(oldRowCount);
            }
        });
    });

    describe("delete a widget", () => {
        it("succeeds", async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            let queryResult = await dbUtils.countRowsInTableWhere('widgets', `id = 1`);
            expect(queryResult).toEqual(1);

            const response = await axios.delete(`${baseUrl}/1`);
                
            expect(response.status).toBe(200);
            expect(response.data.rowCount).toEqual(1);            
            const newRowCount = await dbUtils.countRowsInTable('widgets');
            expect(newRowCount).toEqual(oldRowCount - 1);
            queryResult = await dbUtils.countRowsInTableWhere('widgets', `id = 1`);
            expect(queryResult).toEqual(0);
        });

        it("fails due to an invalid ID", async () => {
            try {
                await axios.delete(`${baseUrl}/-1`);

                fail('DELETE with invalid ID should have failed');
            } 
            catch (err) {
                expect(err.response.status).toEqual(400);
                expect(err.response.data.error).toMatch(/[Ww]idget.*[Ii][Dd]/);
            }
        });
    });

});
