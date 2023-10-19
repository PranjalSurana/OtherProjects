/**
 * MathUtils defines several utility math methods.
 */
class MathUtils {
    sum(number1, number2) {
        return number1 + number2;
    }

    subtract(number1, number2) {
        return number1 - number2;
    }

    multiply(number1, number2) {
        return number1 * number2;
    }

    divide(number1, number2) {
        return number1 / number2;
    }

    average(number1, number2) {
        return (number1 + number2) / 2;
    }

    factorial(number) {
        if (number < 0) {
            throw new Error("There is no factorial for negative numbers");
        }
        else if (number == 1 || number == 0) {
            return 1;
        }
        else {
            return number * this.factorial(number - 1);
        }
    }

}

module.exports = MathUtils;
