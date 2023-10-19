package com.fidelity.refactoring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CustomerTest {
	
    private static final Movie THE_HULK = new Movie("The Hulk", Movie.CHILDREN);
    private static final Movie IRON_MAN = new Movie("Iron Man 4", Movie.NEW_RELEASE);
    private static final Movie SPIDER_MAN = new Movie("Spiderman", Movie.REGULAR);

    private Customer customer;

    @BeforeEach
    public void setUp () {
        customer = new Customer("fred");
    }

    @Test
    public void testChildrenRental () {
        customer.addRental(new Rental(THE_HULK, 2));
        assertEquals(customer.statement(),
                     "Rental record for fred\n\tThe Hulk\t1.5\nAmount owed is 1.5\nYou earned 1 frequent renter points");
    }

    @Test
    public void testDiscountChildrensRentals () {
        customer.addRental(new Rental(THE_HULK, 4));
        assertEquals(customer.statement(),
                     "Rental record for fred\n\tThe Hulk\t3.0\nAmount owed is 3.0\nYou earned 1 frequent renter points");
    }

    @Test
    public void testNewReleaseRental () {
        customer.addRental(new Rental(IRON_MAN, 1));
        assertEquals(customer.statement(),
                     "Rental record for fred\n\tIron Man 4\t3.0\nAmount owed is 3.0\nYou earned 1 frequent renter points");
    }

    @Test
    public void testNotDiscountNewReleaseRentalsButBonusFrequentRenterPoints () {
        customer.addRental(new Rental(IRON_MAN, 4));
        assertEquals(customer.statement(),
                     "Rental record for fred\n\tIron Man 4\t12.0\nAmount owed is 12.0\nYou earned 2 frequent renter points");
    }

    @Test
    public void testRegularRental () {
        customer.addRental(new Rental(SPIDER_MAN, 2));
        assertEquals(customer.statement(),
                     "Rental record for fred\n\tSpiderman\t2.0\nAmount owed is 2.0\nYou earned 1 frequent renter points");
    }

    @Test
    public void testDiscountRegularRental () {
        customer.addRental(new Rental(SPIDER_MAN, 4));
        assertEquals(customer.statement(),
                     "Rental record for fred\n\tSpiderman\t5.0\nAmount owed is 5.0\nYou earned 1 frequent renter points");
    }

    @Test
    public void testSumVariousRentals () {
        customer.addRental(new Rental(THE_HULK, 2));
        customer.addRental(new Rental(SPIDER_MAN, 1));
        customer.addRental(new Rental(IRON_MAN, 3));
        assertEquals(customer.statement(),
                     "Rental record for fred\n\tThe Hulk\t1.5\n\tSpiderman\t2.0\n\tIron Man 4\t9.0\nAmount owed is 12.5\nYou earned 4 frequent renter points");
    }
}
