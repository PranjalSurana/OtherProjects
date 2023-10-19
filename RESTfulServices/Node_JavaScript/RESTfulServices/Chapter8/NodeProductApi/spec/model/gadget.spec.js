const Gadget = require("../../src/model/gadget");

describe('gadget', () => {
    it('is instantiated', () => {
        const gadget = new Gadget(1, 'A gadget', 9.99, 8);

        expect(gadget.id).toBe(1);
        expect(gadget.description).toBe('A gadget');
        expect(gadget.price).toBe(9.99);
        expect(gadget.cylinders).toBe(8);
    });
});