/**
 * Specs for the Widget RESTful endpoint.
 */

const ProductRestController = require('../../src/controllers/product-rest-controller');

console.error = arg => {}

const testWidgets = [
    { id: 1, description: 'Low Impact Widget', price: 12.99, gears: 2, sprockets: 3 },
    { id: 2, description: 'Medium Impact Widget', price: 42.99, gears: 5, sprockets: 5 },
    { id: 3, description: 'High Impact Widget', price: 89.99, gears: 10, sprockets: 8 }
];

describe('RESTful controller unit tests for Product CRUD operations:', () => {
    let controller;
    let mockDao;
    let mockHttpResponse;
    let mockTransactionManager;

    beforeEach(() => {
        mockDao = jasmine.createSpyObj('mockDao',
                            ['queryForAllWidgets', 'queryForWidget', 'createWidget', 
                             'updateWidget', 'deleteWidget', 'shutdown']);
        mockTransactionManager = jasmine.createSpyObj('mockTransactionManager',
                            ['startTransaction', 'commitTransaction', 'rollbackTransaction']);

        controller = new ProductRestController();
        controller.productDao = mockDao;
        controller.transactionManager = mockTransactionManager;

        mockHttpResponse = jasmine.createSpyObj('mockHttpResponse', ['status', 'json']);
        // The mock status() method needs to return a reference to the 
        // mock response so it can be chained with other calls:
        //    res.status(500).json(...)
        mockHttpResponse.status.and.returnValue(mockHttpResponse);
    });

    describe('retrieve all widgets', () => {
        it('succeeds', async () => {
            mockDao.queryForAllWidgets.and.returnValue(testWidgets);
            const req = { };

            await controller.getAllWidgets(req, mockHttpResponse);
        
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith(testWidgets);
        });

        it('fails due to a DAO exception', async () => {
            mockDao.queryForAllWidgets.and.throwError('error');
            const req = { };

            await controller.getAllWidgets(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);

            // We also could test whether the response's send() was called, but we 
            // don't really care about the body of the response as long as the status 
            // is 500. And the body of an error response might change in the future
            // so we'll skip testing send() and avoid making our spec brittle.
        });
    });

    describe('retrieve one widget by ID', () => {
        it('succeeds', async () => {
            mockDao.queryForWidget.and.returnValue(testWidgets[0]);
            const req = { params: { id: 1 } };

            await controller.getWidget(req, mockHttpResponse);
        
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith(testWidgets[0]);
        });

        it('fails due to an invalid ID', async () => {
            const req = { params: { id: -1 } };

            await controller.getWidget(req, mockHttpResponse);
        
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.queryForWidget).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', async () => {
            mockDao.queryForWidget.and.throwError('error');
            const req = { params: { id: 1 } };

            await controller.getWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });

    });

    describe('create a widget', () => {
        it('succeeds', async () => {
            mockDao.createWidget.and.returnValue(1);
            const req = { 
                body: { id: 99, description: 'Widget', price: 999, gears: 9, sprockets: 99 }
            };
        
            await controller.addWidget(req, mockHttpResponse);

            expect(mockDao.createWidget).toHaveBeenCalled();
            expect(mockTransactionManager.commitTransaction).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('fails due to an empty request body', async () => {
            const req = { body: { } };

            await controller.addWidget(req, mockHttpResponse);
                
            expect(mockDao.createWidget).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', async () => {
            mockDao.createWidget.and.throwError('error');
            const req = { 
                body: { id: 99, description: 'Widget', price: 999, gears: 9, sprockets: 99 }
            };

            await controller.addWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('update a widget', () => {
        it('succeeds', async () => {
            mockDao.updateWidget.and.returnValue(1);
            const req = { 
                body: { id: 99, description: 'Very Groovy Widget', price: 999, gears: 9, sprockets: 99 }
            };
        
            await controller.updateWidget(req, mockHttpResponse);

            expect(mockDao.updateWidget).toHaveBeenCalled();
            expect(mockTransactionManager.commitTransaction).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('fails due to an empty request body', async () => {
            const req = { body: { } };

            await controller.updateWidget(req, mockHttpResponse);
                
            expect(mockDao.updateWidget).not.toHaveBeenCalled();
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
        });

        it('fails due to a DAO exception', async () => {
            mockDao.updateWidget.and.throwError('error');
            const req = { 
                body: { id: 99, description: 'Very Groovy Widget', price: 999, gears: 9, sprockets: 99 }
            };

            await controller.updateWidget(req, mockHttpResponse);
                
            expect(mockTransactionManager.rollbackTransaction).toHaveBeenCalled();
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('delete a widget', () => {
        it('succeeds', async () => {
            mockDao.deleteWidget.and.returnValue(1);
            const req = { params: { id: 1 } };

            await controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockDao.deleteWidget).toHaveBeenCalled();
            expect(mockTransactionManager.commitTransaction).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('fails due to an invalid id', async () => {
            const req = { params: { id: -1 } };

            await controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.deleteWidget).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', async () => {
            mockDao.deleteWidget.and.throwError('error');
            const req = { params: { id: 1 } };

            await controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockTransactionManager.rollbackTransaction).toHaveBeenCalled();
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('shutdown the API', () => {
        it('succeeds', async () => {
            const req = { };

            await controller.stop(req, mockHttpResponse);
 
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(200);
        });

        it('fails due to a DAO exception', async () => {
            mockDao.shutdown.and.throwError('error');
            const req = { };

            await controller.stop(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });
});
