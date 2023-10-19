/**
 * A Node.js RESTful API for CRUD operations on a widget database.
 * This is a Node.js version of the Spring Boot widget REST service.
 * 
 * To start the service:
 *      npm start
 */

const ProductDao = require('../dao/mock-product-dao');   // load the stub DAO
const productDao = new ProductDao();

const express = require('express');

const app = express();

// Configure Express to populate a request body from JSON input
app.use(express.json());

// Set up routing for widget operations
router = express.Router();

router.get('/widgets', getAllWidgets);  // get all widgets
router.get('/widgets/:id', getWidget);  // get the widget with the given id
router.post('/widgets', addWidget);     // add a new widget
router.put('/widgets', updateWidget);   // update an existing widget
router.delete('/widgets/:id', deleteWidget);    // delete the widget with the given id

app.use('/', router);

app.listen(3000, 
    () => console.log( 'Listening on port 3000'))

//============================== Widget functions ============================

function getAllWidgets(req, res) {
    try {
        const widgets = productDao.queryForAllWidgets();
        res.json(widgets);
    }
    catch (err) {
        console.error(`error GET widgets: ${err}`);
        res.status(500).json({ error: err });
    }
}

function getWidget(req, res) {
    const id = req.params.id;

    try {
        const widget = productDao.queryForWidget(id);
        if (widget) {
            res.json(widget);
        } else {
            res.status(406).json({ error: `Widget ${id} not found` });
        }
    } catch (err) {
        console.error(`error GET widget ${id}: ${err}`);
        res.status(500).json({error: err});
    }
}

function addWidget(req, res) {
    const w = req.body;

    if (w && w.id && w.description && w.price && w.gears && w.sprockets) {
        try {
            const insertCount = productDao.createWidget(w);
            res.json({ rowCount: insertCount });
        }
        catch (err) {
            res.status(500).json({ error: 'error on POST request' });
        }
    }
    else {
        res.status(400).json({error: 'widget is not fully populated: ' +
                                     JSON.stringify(w) });
    }
}

function updateWidget(req, res) {
    const widget = req.body;
    if (widget) {
        try {
            const rowCount = productDao.updateWidget(widget);

            res.json({ rowCount: rowCount });
        }
        catch (err) {
            console.error(`error PUT widget ${JSON.stringify(widget)}\n: ${err}`);
            res.status(500).json({error: err});
        }
    }
    else {
        res.status(400).json({error: `widget ${JSON.stringify(w)} is not fully populated`});
    }
}

function deleteWidget(req, res) {
    // Read id from the URL
    const id = req.params.id;

    if (id) {
        try {
            const rowCount = productDao.deleteWidget(id);

            return res.json({ rowCount: rowCount });
        }
        catch (err) {
            console.error(`error DELETE widget ${id}: ${err}`);
            res.status(500).json({error: err});
        }
    }
    else {
        res.status(400).json({error: `bad widget id ${id}`});
    }
}

function shutdownApi(req, res) {
    try {
        productDao.shutdown();
        res.status(204).end();
    }
    catch (err) {
        console.error(`error shutting down: ${err}`);
        res.status(500).json({error: err});
    }
}

module.exports = {
    getAllWidgets,
    getWidget,
    addWidget,
    updateWidget,
    deleteWidget,
    shutdownApi
};
