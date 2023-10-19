/** Gadget class is a subclass of Product */

const Product = require("./product");

class Gadget extends Product {
    constructor(id, description, price, cylinders) {
        if (cylinders <= 0) {
            throw Error(`invalid cylinders ${cylinders}`);
        }
        super(id, description, price);
        this.cylinders = cylinders;
    }

    toString() {
        JSON.stringify(this);
    }
}

module.exports = Gadget
