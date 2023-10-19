/** Widget class is a subclass of Product */

const Product = require("./product");

class Widget extends Product {
    constructor(id, description, price, gears, sprockets) {
        super(id, description, price);
        if (gears <= 0 || sprockets <= 0) {
            throw Error(`invalid argument ${gears} or ${sprockets}`);
        }
        this.gears = gears;
        this.sprockets = sprockets;
    }

    toString() {
        return JSON.stringify(this);
    }
}

module.exports = Widget
