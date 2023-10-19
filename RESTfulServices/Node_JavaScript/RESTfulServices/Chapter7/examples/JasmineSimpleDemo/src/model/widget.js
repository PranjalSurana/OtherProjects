/** Widget class is a subclass of Product */

const Product = require("./product");

class Widget extends Product {
    constructor(id, description, price, gears, sprockets) {
        super(id, description, price);
        if (gears <= 0) {
            throw Error(`invalid gears ${gears}`);
        }
        if (sprockets <= 0) {
            throw Error(`invalid sprockets ${sprockets}`);
        }
        this.gears = gears;
        this.sprockets = sprockets;
    }

    toString() {
        JSON.stringify(this);
    }
}

module.exports = Widget
