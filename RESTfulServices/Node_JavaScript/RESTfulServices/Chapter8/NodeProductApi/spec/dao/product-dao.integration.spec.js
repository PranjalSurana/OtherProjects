/*
 * Specs for the Oracle Widget DAO.
 */

const ProductDao = require('../../src/dao/product-dao');
const TransactionManager = require('../../src/dao/transaction-manager');
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

describe('Product DAO integration tests for Widget CRUD operations:', () => {
    let widgetDao;
    let transactionManager;

    beforeEach(async () => {
        await initDb();
        transactionManager = new TransactionManager();
        widgetDao = new ProductDao(transactionManager);
        await transactionManager.startTransaction();
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

            const allWidgets = await widgetDao.queryForAllWidgets();

            await transactionManager.rollbackTransaction();
            const rowCount = await dbUtils.countRowsInTable('widgets');
            expect(allWidgets.length).toEqual(rowCount);
            expect(allWidgets).toEqual(widgetsInDb);            
        });
    });

    describe('retrieve a widget', () => {
        it('succeeds', async () => {

            const widget = await widgetDao.queryForWidget(1);

            await transactionManager.rollbackTransaction();
            expect(widget).toEqual(widget1);            
        });
    });

    describe('create a widget', () => {
        it('succeeds', async () => {
            const oldRowCount = await dbUtils.countRowsInTable('widgets');
            newRowId = oldRowCount + 1;
            const w = {
                id: newRowId,
                description: 'Very Groovy Widget',
                price: 999,
                gears: 9,
                sprockets: 99
            };

            const insertedCount = await widgetDao.createWidget(w)

            await transactionManager.commitTransaction();
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

            const updatedCount = await widgetDao.updateWidget(w);

            await transactionManager.commitTransaction();
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

            const deletedCount = await widgetDao.deleteWidget(1);

            await transactionManager.commitTransaction();
            expect(deletedCount).toEqual(1);            
            const newRowCount = await dbUtils.countRowsInTable('widgets');
            expect(newRowCount).toEqual(oldRowCount - 1);
            queryResult = await dbUtils.countRowsInTableWhere('widgets', 'id = 1');
            expect(queryResult).toEqual(0);
        });
    });
});
