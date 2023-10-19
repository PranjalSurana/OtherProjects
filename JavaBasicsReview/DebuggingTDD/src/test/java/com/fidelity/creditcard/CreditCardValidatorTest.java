package com.fidelity.creditcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CreditCardValidatorTest {
	// Note that although this is a credit card validator, short numbers work just as well in the algorithm
    @Test
    public void test1 () {
        CreditCardValidator v = new CreditCardValidator();
        assertEquals(v.isValid(1234567890L), false);
    }

    @Test
    public void test2 () {
        CreditCardValidator v = new CreditCardValidator();
        assertEquals(v.isValid(25432L), false);
    }

    @Test
    public void test3 () {
        CreditCardValidator v = new CreditCardValidator();
        assertEquals(v.isValid(63636L), true);
    }

    @Test
    public void test4 () {
        CreditCardValidator v = new CreditCardValidator();
        assertEquals(v.isValid(254327L), true);
    }

    @Test
    public void test5 () {
        CreditCardValidator v = new CreditCardValidator();
        assertEquals(v.isValid(754320L), true);
    }
    
}