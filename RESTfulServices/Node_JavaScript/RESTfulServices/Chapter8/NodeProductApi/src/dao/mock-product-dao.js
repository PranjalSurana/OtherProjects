/**
 * Test DAO for CRUD operations on Products
 */

const fs = require('fs');

const widgetFile = `data/widgets.json`;

class ProductDao {
    constructor() {
        // read mock data from JSON file
        const fileContents = fs.readFileSync(widgetFile, 'utf-8');
        this.widgets = JSON.parse(fileContents);
    }

    queryForAllWidgets() {
        return this.widgets;
    }

    queryForWidget(id) {
        // Search for the widget with the given id
        const searchResult = this.widgets.filter(widget => widget.id == id);
        // Return the first widget found or null
        const widget = searchResult ? searchResult[0] : null;
        return widget;
    }

    createWidget(widget) {
        this.widgets.push(widget);
        return 1;
    }

    updateWidget(widget) {
        let updateCount = 0;
        const searchResult = this.widgets.filter(w => w.id == widget.id);
        if (searchResult.length > 0) {  // widget exists
            // Replace the widget with the updated widget
            this.widgets = this.widgets.map(w => w.id == widget.id ? widget : w);
            updateCount = 1;
        }
        return updateCount;
    }

    deleteWidget(id) {
        const previousLength = this.widgets.length;
        // Attempt to remove the widget with the given id 
        this.widgets = this.widgets.filter(widget => widget.id != id);
        // Return 1 if the widget was removed, 0 otherwise
        return this.widgets.length < previousLength ? 1 : 0;
    }
    
    closeConnection(connection) {
    }

    shutdown() {
    }
}

module.exports = ProductDao;


