/*
 * Specs for the business manager.
 */

const BusinessService = require('../../src/business-service/business-service');
const dbUtils = require('../../src/dao/db-utils');
const Widget = require('../../src/model/widget');

/* 
    Database initialization:
    delete from widgets;
    insert into widgets values (1, 'Low Impact Widget', 12.99, 2, 3);
    insert into widgets values (2, 'Medium Impact Widget', 42.99, 5, 5);
    insert into widgets values (3, 'High Impact Widget', 89.99, 10, 8);
    commit;
    select * from widgets order by id;
 */

const widget1 = new Widget(1, 'Low Impact Widget', 12.99, 2, 3);
const widget2 = new Widget(2, 'Medium Impact Widget', 42.99, 5, 5);
const widget3 = new Widget(3, 'High Impact Widget', 89.99, 10, 8);

const widgetsInDb = [widget1, widget2, widget3];

describe('Business service integration tests for Widget CRUD operations:', () => {
    let businessService;

    beforeEach(async () => {
        await initDb();
        businessService = new BusinessService();
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
    
    describe('retrieve all widgets', () => {
        it('succeeds', async () => {

            const allWidgets = await businessService.queryForAllWidgets();

            const rowCount = await dbUtils.countRowsInTable('widgets');
            expect(allWidgets.length).toEqual(rowCount);
            expect(allWidgets).toEqual(widgetsInDb);            
        });
    });

    describe('retrieve a widget', () => {
        it('succeeds', async () => {

            const widget = await businessService.queryForWidget(1);

            expect(widget).toEqual(widget1);            
        });
    });

    describe('create a widget', () => {
        it('succeeds', async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            const newRowId = oldRowCount + 1;
            const w = {
                id: newRowId,
                description: 'Very Groovy Widget',
                price: 999,
                gears: 9,
                sprockets: 99
            };

            const insertedCount = await businessService.createWidget(w);

            expect(insertedCount).toEqual(1);            
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
    });

    describe('update a widget', () => {
        it('succeeds', async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            const w = {
                id: 1,
                description: 'Very Groovy Widget',
                price: 999,
                gears: 9,
                sprockets: 99
            };

            const updatedCount = await businessService.updateWidget(w);

            expect(updatedCount).toEqual(1);            
            const newRowCount = await dbUtils.countRowsInTable('widgets');
            expect(newRowCount).toEqual(oldRowCount);
            const queryResult = await dbUtils.countRowsInTableWhere('widgets',
                `   id = 1 
                and description='Very Groovy Widget' 
                and price = 999
                and gears = 9 
                and sprockets = 99`);
            expect(queryResult).toEqual(1);
        });
    });

    describe('delete a widget', () => {
        it('succeeds', async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            let queryResult = await dbUtils.countRowsInTableWhere('widgets', 'id = 1');
            expect(queryResult).toEqual(1);

            const updatedCount = await businessService.deleteWidget(1);

            expect(updatedCount).toEqual(1);            
            const newRowCount = await dbUtils.countRowsInTable('widgets');
            expect(newRowCount).toEqual(oldRowCount - 1);
            queryResult = await dbUtils.countRowsInTableWhere('widgets', 'id = 1');
            expect(queryResult).toEqual(0);
        });
    });
});
