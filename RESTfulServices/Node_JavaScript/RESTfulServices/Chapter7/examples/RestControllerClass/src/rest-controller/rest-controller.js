/**
 * A Node.js RESTful API for CRUD operations on a widget database.
 * This is a Node.js version of the Spring Boot widget REST service.
 * 
 * To start the service:
 *      npm start
 */

// Use Express to implement a REST API
// See https://expressjs.com/en/4x/api.html
const express = require('express');

const ProductDao = require('../dao/mock-product-dao');

// To enable CORS, uncomment the following statement
// const cors = require('cors');

class ProductRestController {
    constructor() { 
        this.port = process.env.PORT || 3000;

        this.productDao = new ProductDao();

        this.app = express();
        // Configure Express to populate a request body from JSON input
        this.app.use(express.json());

        // To enable CORS, uncomment the following statement
        // this.app.use(cors());

        // Set up routing for widget operations
        const router = express.Router();
        router.get('/widgets', this.getAllWidgets.bind(this));
        router.get('/widgets/:id', this.getWidget.bind(this));
        router.post('/widgets', this.addWidget.bind(this));
        router.put('/widgets', this.updateWidget.bind(this));
        router.delete('/widgets/:id', this.deleteWidget.bind(this));

        this.app.use('/', router);

        this.onlyDigitsRegExp = /^\d+$/;
    }

    start() {
        this.app.listen(this.port, 
            () => console.log(`listening on port ${this.port}`))
    }

    //============================== Widget methods ============================

    getAllWidgets(req, res) {
        try {
            const widgets = this.productDao.queryForAllWidgets();
            res.json(widgets);
        }
        catch (err) {
            console.error(`error on GET widgets: ${err}`);
            res.status(500).json({error: err});
        }
    }

    getWidget(req, res) {
        // Read id from the URL
        // If no id was passed, the request would have been handled by router.get('/')
        const id = req.params.id;
        if (this.onlyDigitsRegExp.test(id)) {  // test for only digits
            try {
                const widget = this.productDao.queryForWidget(id);

                if (widget) {
                    res.json(widget);
                }
                else {
                    res.status(404).json({ error: `Widget ${id} not found` }); // 406 - Not Acceptable
                }
            }
            catch (err) {
                console.error(`error on GET widget ${id}: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `bad widget id ${id}`});
        }
    }

    addWidget(req, res) {
        const w = req.body;
        if (w.id && w.description && w.price && w.gears && w.sprockets) {
            try {
                const rowCount = this.productDao.createWidget(w);

                res.json({ rowCount: rowCount });
            }
            catch (err) {
                console.error(`error on POST widget ${JSON.stringify(w)}\n: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `widget ${JSON.stringify(w)} is not fully populated`});
        }
    }

    updateWidget(req, res) {
        const w = req.body;
        if (w.id) {
            try {
                const rowCount = this.productDao.updateWidget(w);

                res.json({ rowCount: rowCount });
            }
            catch (err) {
                console.error(`error on PUT widget ${JSON.stringify(w)}\n: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `widget ${JSON.stringify(w)} is not fully populated`});
        }
    }

    deleteWidget(req, res) {
        // Read id from the URL
        const id = req.params.id;

        if (this.onlyDigitsRegExp.test(id)) {  // test for only digits
            try {
                const rowCount = this.productDao.deleteWidget(id);

                res.json({ rowCount: rowCount });
            }
            catch (err) {
                console.error(`error on DELETE widget ${id}: ${err}`);
                res.status(500).json({error: err});
            }
        }
        else {
            res.status(400).json({error: `bad widget id ${id}`});
        }
    }

    stop(req, res) {
        try {
            this.productDao.shutdown();
            if (res) {
                res.status(200);
            }
        }
        catch (err) {
            console.error(`error shutting down: ${err}`);
            res.status(500).json({error: err});
        }
    }
}

module.exports = ProductRestController;

// When a file is run directly from Node.js, require.main is set to its module. 
// So if require.main === module, this file is being executed as a standalone program.
// Otherwise it is being "required" by another module; e.g., a spec module
if (require.main === module) {
    const api = new ProductRestController();
    api.start();
}
