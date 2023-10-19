/** Product class */

class Product {
    constructor(id, description, price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    toString() {
        JSON.stringify(this);
    }
}

module.exports = Product
