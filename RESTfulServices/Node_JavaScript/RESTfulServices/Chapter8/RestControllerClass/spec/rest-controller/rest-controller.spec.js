/**
 * Specs for the Widget RESTful endpoint.
 */

const ProductRestController = require('../../src/rest-controller/rest-controller');

console.error = s => {}

const testWidgets = [
    { id: 1, description: 'Low Impact Widget', price: 12.99, gears: 2, sprockets: 3 },
    { id: 2, description: 'Medium Impact Widget', price: 42.99, gears: 5, sprockets: 5 },
    { id: 3, description: 'High Impact Widget', price: 89.99, gears: 10, sprockets: 8 }
];

describe('RESTful controller unit tests for Widget CRUD operations:', () => {
    let controller;
    let mockBusinessService;

    beforeEach(() => {
        mockBusinessService = jasmine.createSpyObj('mockBusinessService', 
                            ['queryForAllWidgets', 'queryForWidget', 'createWidget', 
                             'updateWidget', 'deleteWidget', 'shutdown']);

        controller = new ProductRestController(mockBusinessService);

        mockHttpResponse = jasmine.createSpyObj('mockHttpResponse', ['status', 'json']);

        // The mock status() method needs to return a reference to the mock response
        // so it can be chained with other calls: res.status(500).json(...)
        mockHttpResponse.status.and.returnValue(mockHttpResponse); 
    });

    describe('retrieve all widgets', () => {
        it('succeeds', async () => {
            // The Promise.resolve() static method creates a new resolved Promise for the provided value.
            mockBusinessService.queryForAllWidgets = () => Promise.resolve(testWidgets);
            const req = { };

            await controller.getAllWidgets(req, mockHttpResponse);
        
            expect(mockHttpResponse.json)
                    .toHaveBeenCalledOnceWith(testWidgets);
        });

        it('fails due to a business service exception', async () => {
            mockBusinessService.queryForAllWidgets.and.throwError('mock business service error');
            const req = { };

            await controller.getAllWidgets(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('retrieve one widget by ID', () => {
        it('succeeds', async () => {
            mockBusinessService.queryForWidget = () => Promise.resolve(testWidgets[0]);

            const req = { params: { id: 1 } };

            await controller.getWidget(req, mockHttpResponse);
        
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith(testWidgets[0]);
        });

        it('fails due to an invalid ID', async () => {
            const req = { params: { id: -1 } };

            await controller.getWidget(req, mockHttpResponse);
        
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
            // We could also check that the businessService.queryForWidget() method 
            // was not called, but that makes the spec dependent on the implementation 
            // of the controller. A best practice to make your specs less brittle is
            // to avoid white box testing whenever possible.
            // expect(mockBusinessService.queryForWidget).not.toHaveBeenCalled();
        });

        it('fails due to a business service exception', async () => {
            mockBusinessService.queryForWidget.and.throwError('mock business service error');
            const req = { params: { id: 1 } };

            await controller.getWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });

    });

    describe('create a widget', () => {
        it('succeeds', async () => {
            mockBusinessService.createWidget = () => Promise.resolve(1);
            const req = { 
                body: { id: 99, description: 'Very Groovy Widget', price: 999, gears: 9, sprockets: 99 }
            };
        
            await controller.addWidget(req, mockHttpResponse);

            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({ rowCount: 1 });
        });

        it('fails due to an empty request body', async () => {
            const req = { body: { } };

            await controller.addWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
        });

        it('fails due to a business service exception', async () => {
            mockBusinessService.createWidget.and.throwError('mock business service error');
            const req = { body: { id: 99, description: 'Very Groovy Widget', price: 999, gears: 9, sprockets: 99 }
            };

            await controller.addWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('update a widget', () => {
        it('succeeds', async () => {
            mockBusinessService.updateWidget = () => Promise.resolve(1);
            const req = { body: { id: 1, description: 'Extremely Awesome Widget', price: 999, gears: 9, sprockets: 99 }
            };

            await controller.updateWidget(req, mockHttpResponse);

            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({ rowCount: 1 });
        });

        it('fails due to an empty request body', async () => {
            const req = { body: { } };

            await controller.updateWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
        });

        it('fails due to a business service exception', async () => {
            mockBusinessService.updateWidget.and.throwError('mock business service error');
            const req = { body: { id: 99, description: 'Very Groovy Widget', price: 999, gears: 9, sprockets: 99 }
            };

            await controller.updateWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('delete a widget', () => {
        it('succeeds', async () => {
            mockBusinessService.deleteWidget = () => Promise.resolve(1);
            const req = { params: { id: 1 } };

            await controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.json).toHaveBeenCalledOnceWith({ rowCount: 1 });
        });

        it('fails due to an invalid id', async () => {
            const req = { params: { id: -1 } };

            await controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(400);
        });

        it('fails due to a business service exception', async () => {
            mockBusinessService.deleteWidget.and.throwError('mock business service error');
            const req = { params: { id: 1 } };

            await controller.deleteWidget(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });

    describe('shutdown the API', () => {

        it('succeeds', async () => {
            mockBusinessService.shutdown = () => Promise.resolve();
            const req = { };

            await controller.stop(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(200);
        });

        it('fails due to a business service exception', async () => {
            mockBusinessService.shutdown.and.throwError('mock business service error');
            const req = { };

            await controller.stop(req, mockHttpResponse);
                
            expect(mockHttpResponse.status).toHaveBeenCalledOnceWith(500);
        });
    });
});
