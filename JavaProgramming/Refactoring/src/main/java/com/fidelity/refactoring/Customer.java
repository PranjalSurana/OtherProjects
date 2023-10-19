package com.fidelity.refactoring;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	
    private String name;
    private List<Rental> rentals;

    public Customer (String name) {
        this.name = name;
        rentals = new ArrayList<>();
    }

    public String getName () {
        return name;
    }

    public void addRental (Rental rental) {
        rentals.add(rental);
    }

    public String statement () {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental record for " + getName() + "\n";

        for (Rental rental : rentals) {
            double amount = 0;
            if (rental.getMovie().getPriceCode() == Movie.REGULAR) {
                amount += 2;
                if (rental.getDaysRented() > 2) {
                    amount += (rental.getDaysRented() - 2) * 1.5;
                }
            }
            if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) {
                amount += rental.getDaysRented() * 3;
            }
            if (rental.getMovie().getPriceCode() == Movie.CHILDREN) {
                amount += 1.5;
                if (rental.getDaysRented() > 3) {
                    amount += (rental.getDaysRented() - 3) * 1.5;
                }
            }
            // add frequent renter points
            frequentRenterPoints++;
            // add bonus for a two day new release rental
            if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1) {
                frequentRenterPoints++;
            }
            // show figures for this rental
            result += "\t" + rental.getMovie().getTitle() + "\t" + amount + "\n";
            totalAmount += amount;
        }

        result += "Amount owed is " + totalAmount + "\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points";
        
        return result;
    }
}
