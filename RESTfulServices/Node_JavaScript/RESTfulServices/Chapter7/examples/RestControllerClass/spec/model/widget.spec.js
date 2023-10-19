const Widget = require("../../src/model/widget");

describe('widget constructor', () => {
    it('succeeds when all args are valid', () => {
        const widget = new Widget(1, 'A widget', 9.99, 5, 3);
        expect(widget.id).toBe(1);
        expect(widget.description).toBe('A widget');
        expect(widget.price).toBe(9.99);
        expect(widget.gears).toBe(5);
        expect(widget.sprockets).toBe(3);
    });

    it('fails when an arg is invalid', () => {
        expect(() => new Widget(1, 'A widget', 9.99, 5, 0)).toThrowError(Error);
    });
});