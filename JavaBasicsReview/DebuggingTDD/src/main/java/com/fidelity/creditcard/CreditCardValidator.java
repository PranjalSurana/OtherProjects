package com.fidelity.creditcard;

public class CreditCardValidator {
    // return checksum of the given credit card number by checking each digit in turn
    boolean isValid (long ccard) {
        // TODO: find and fix the bug in this method
        int numLength = (int)(Math.log10(ccard) + 1); // NOTE: this line is correct
        int sum = 0;
        for (int k = numLength - 1; k >= 0; k--) {
            long digit = ccard % 10;
            if (k % 2 != 0) {
                digit *= 2;
                if (digit >= 10) {
                    digit -= 9;
                }
            }
            sum += digit;
            ccard /= 10;
        }
        return (sum % 10 == 0);
    }
}
