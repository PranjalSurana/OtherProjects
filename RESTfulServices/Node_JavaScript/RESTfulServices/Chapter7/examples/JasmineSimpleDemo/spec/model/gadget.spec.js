const Gadget = require("../../src/model/gadget");

describe('gadget constructor', () => {
    it('succeeds when all args are valid', () => {
        const gadget = new Gadget(1, 'A gadget', 9.99, 8);

        expect(gadget.id).toBe(1);
        expect(gadget.description).toBe('A gadget');
        expect(gadget.price).toBe(9.99);
        expect(gadget.cylinders).toBe(8);
    });
	

    it('fails when cylinders is invalid', () => {
        expect(() => new Gadget(1, 'A gadget', 9.99, 0)).toThrowError(Error);
    });
	
});