

// This is our test suite
describe('Samples', function () {
    let calc;

    // This will be called before running each spec
    beforeEach(function () {
        // calc = new MathUtils();
    });

    // This will be called after running each spec
    // Setting calc to null is not really necessary, but shown as an example
    afterEach(function () {
        calc = null;
    });

    describe('A suite', function () {
        it('contains spec with an expectation', function () {
            expect(true).toBe(true);
        });
    });

    // describes may be nested to group related tests together
    describe('nested describe', function () {
        it('The toBeLessThan matcher', function () {
            let pi = 3.1415926;
            let e = 2.78;

            expect(e).toBeLessThan(pi);
            expect(pi).not.toBeLessThan(e);
            expect(pi).toBeGreaterThan(e);
        });

        it('toThrow matcher is for testing exceptions', function () {
            let foo = function () { return 1 + 2; };
            let bar = function () { return a + 1; };
            let baz = function () { throw 'what'; };

            expect(foo).not.toThrow();
            expect(bar).toThrow();
            expect(baz).toThrow('what');
        });


    });

    describe('A spec using beforeEach and afterEach', function () {
        let theAnswer = 42;
        let foo = 0;

        beforeEach(function () {
            foo = theAnswer;
        });

        afterEach(function () {
            foo = 0;
        });

        it('does foo equal theAnswer', function () {
            expect(foo).toEqual(theAnswer);
        });
    });

    xdescribe('Not ready for prime time', function () {
        let foo;

        beforeEach(function () {
            foo = 0;
            foo += 1;
        });

        it('not completed yet', function () {
            expect(foo).toEqual(1);
        });
    });

    describe('Pending specs', function () {
        xit('Skip this one', function () {
            expect(true).toBe(false);
        });

        it('a spec w/o a body is skipped');

        it('calling pending', function () {
            expect(true).toBe(false);
            pending('this is why it is pending');
        });
    });

});