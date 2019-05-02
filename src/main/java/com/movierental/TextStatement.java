package com.movierental;


import java.util.List;

public class TextStatement implements Statement {
    private final Customer customer;
    private final List<Rental> rentals;

    public TextStatement(Customer customer, List<Rental> rentals) {
        this.customer = customer;
        this.rentals = rentals;
    }

    @Override
    public String header() {
        return "Rental Record for " + customer.getName() + "\n";
    }

    @Override
    public String footer() {
        StringBuilder footerBuilder = new StringBuilder();
        footerBuilder.append("Amount owed is ")
                .append(customer.getTotalAmount()).append('\n')
                .append("You earned ").append(customer.getTotalFRP())
                .append(" frequent renter points");
        return footerBuilder.toString();
    }

    @Override
    public String body() {
        StringBuilder bodyBuilder = new StringBuilder();
        rentals.forEach(rental -> bodyBuilder.append(String.format("\t%s\t%s\n", rental.getMovie().getTitle(), rental.amount())));
        return bodyBuilder.toString();
    }

}
