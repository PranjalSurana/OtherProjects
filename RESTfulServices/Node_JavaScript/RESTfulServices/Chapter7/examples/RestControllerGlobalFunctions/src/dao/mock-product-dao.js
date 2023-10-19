/**
 * Test DAO for CRUD operations on Products
 */

const fs = require('fs');

const widgetFilePath = `${__dirname}/widgets.json`;

// read mock data from JSON file
const fileContents = fs.readFileSync(widgetFilePath, 'utf-8');

let widgets = JSON.parse(fileContents);

class ProductDao {
    queryForAllWidgets() {
        return widgets;
    }

    queryForWidget(id) {
        // Search for the widget with the given id
        const searchResult = widgets.filter(w => w.id == id);
        // Return the first widget found or null
        const widget = searchResult ? searchResult[0] : null;
        return widget;
    }

    createWidget(widget) {
        widgets.push(widget);
        return 1;
    }

    updateWidget(widget) {
        let updateCount = 0;
        const searchResult = widgets.filter(w => w.id == widget.id);
        if (searchResult.length > 0) {  // widget exists
            // Replace the widget with the updated widget
            widgets = widgets.map(w => w.id == widget.id ? widget : w);
            updateCount = 1;
        }
        return updateCount;
    }

    deleteWidget(id) {
        const previousLength = widgets.length;
        // Attempt to remove the widget with the given id 
        widgets = widgets.filter(w => w.id != id);
        // Return 1 if the widget was removed, 0 otherwise
        return widgets.length < previousLength ? 1 : 0;
    }
    
    closeConnection(connection) {
    }

    shutdown() {
    }
}

module.exports = ProductDao;


