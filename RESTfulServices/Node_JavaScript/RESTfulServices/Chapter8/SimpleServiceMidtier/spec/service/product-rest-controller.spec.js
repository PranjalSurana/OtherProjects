/**
 * Specs for the Widget RESTful endpoint.
 */

const ProductRestController = require('../../src/service/product-rest-controller');

console.error = arg => {}

const testWidgets = [
    { id: 1, description: 'Low Impact Widget', price: 12.99, gears: 2, sprockets: 3 },
    { id: 2, description: 'Medium Impact Widget', price: 42.99, gears: 5, sprockets: 5 },
    { id: 3, description: 'High Impact Widget', price: 89.99, gears: 10, sprockets: 8 }
];

describe('RESTful controller unit tests for Widget CRUD operations:', () => {
    let controller;
    let mockDao;
    let mockHttpResponse;

    beforeEach(() => {
        mockDao = jasmine.createSpyObj('mockDao', ['queryForAllWidgets', 'queryForWidget', 'createWidget', 
                                        'updateWidget', 'deleteWidget', 'shutdown']);

        controller = new ProductRestController();
        controller.productDao = mockDao;

        mockHttpResponse = jasmine.createSpyObj('mockHttpResponse', ['status', 'json']);

        // The mock status() method needs to return a reference to the 
        // mock response so it can be chained with other calls:
        //    res.status(500).json(...)
        mockHttpResponse.status.and.returnValue(mockHttpResponse);
    });

    describe('retrieve all widgets', () => {
        it('succeeds', () => {
            mockDao.queryForAllWidgets.and.returnValue(testWidgets);
            const req = { };

            controller.getAllWidgets(req, mockHttpResponse);
        
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith(testWidgets);
        });

        it('fails due to a DAO exception', () => {
            mockDao.queryForAllWidgets.and.throwError('error');
            const req = { };

            controller.getAllWidgets(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);

            // We could test whether send() was called, but we don't really
            // care about the body of the response as long as the status is 500.
            // A best practice is to keep "white box" testing (that is, tests that
            // depend on the implementation of a method) to a minimum.
        });
    });

    describe('retrieve one widget by ID', () => {
        it('succeeds', () => {
            mockDao.queryForWidget.and.returnValue(testWidgets[0]);
            const req = { params: { id: 1 } };

            controller.getWidget(req, mockHttpResponse);
        
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith(testWidgets[0]);
        });

        it('fails because widget is not found', () => {
            const req = { params: { id: 999 } };

            controller.getWidget(req, mockHttpResponse);
        
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(406);
        });

        it('fails due to an invalid ID', () => {
            const req = { params: { id: -1 } };

            controller.getWidget(req, mockHttpResponse);
        
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.queryForWidget).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', () => {
            mockDao.queryForWidget.and.throwError('error');
            const req = { params: { id: 1 } };

            controller.getWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });

    });

    describe('create a widget', () => {
        it('succeeds', () => {
            mockDao.createWidget.and.returnValue(1);
            const req = { 
                body: { id: 99, description: 'Widget', price: 999, gears: 9, sprockets: 99 }
            };
        
            controller.addWidget(req, mockHttpResponse);

            expect(mockDao.createWidget).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('succeeds with returned row count 0', () => {
            mockDao.createWidget.and.returnValue(0);
            const req = { 
                body: { id: 99, description: 'Widget', price: 999, gears: 9, sprockets: 99 }
            };
        
            controller.addWidget(req, mockHttpResponse);

            expect(mockDao.createWidget).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 0});
        });

        it('fails due to an empty request body', () => {
            const req = { body: { } };

            controller.addWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.createWidget).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', () => {
            mockDao.createWidget.and.throwError('error');
            const req = { 
                body: { id: 99, description: 'Widget', price: 999, gears: 9, sprockets: 99 }
            };

            controller.addWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('update a widget', () => {
        it('succeeds', () => {
            mockDao.updateWidget.and.returnValue(1);
            const req = { 
                body: { id: 99, description: 'Very Groovy Widget', price: 999, gears: 9, sprockets: 99 }
            };
        
            controller.updateWidget(req, mockHttpResponse);

            expect(mockDao.updateWidget).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('fails due to an empty request body', () => {
            const req = { body: { } };

            controller.updateWidget(req, mockHttpResponse);
                
            expect(mockDao.updateWidget).not.toHaveBeenCalled();
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
        });

        it('fails due to a DAO exception', () => {
            mockDao.updateWidget.and.throwError('error');
            const req = { 
                body: { id: 99, description: 'Very Groovy Widget', price: 999, gears: 9, sprockets: 99 }
            };

            controller.updateWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('delete a widget', () => {
        it('succeeds', () => {
            mockDao.deleteWidget.and.returnValue(1);
            const req = { params: { id: 1 } };

            controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockDao.deleteWidget).toHaveBeenCalled();
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({rowCount: 1});
        });

        it('fails due to an invalid id', () => {
            const req = { params: { id: -1 } };

            controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            expect(mockDao.deleteWidget).not.toHaveBeenCalled();
        });

        it('fails due to a DAO exception', () => {
            mockDao.deleteWidget.and.throwError('error');
            const req = { params: { id: 1 } };

            controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('shutdown the API', () => {
        it('succeeds', () => {
            const req = { };

            controller.stop(req, mockHttpResponse);
 
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(200);
        });

        it('fails due to a DAO exception', () => {
            mockDao.shutdown.and.throwError('error');
            const req = { };

            controller.stop(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });
});
