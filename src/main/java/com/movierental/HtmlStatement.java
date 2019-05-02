package com.movierental;

import java.util.List;

public class HtmlStatement implements Statement {
    private final Customer customer;
    private final List<Rental> rentals;

    public HtmlStatement(Customer customer, List<Rental> rentals) {
        this.customer = customer;
        this.rentals = rentals;
    }

    @Override
    public String header() {
        return String.format("<H1>Rental Record for <B>%s</B></H1>", this.customer.getName());
    }

    @Override
    public String footer() {
        StringBuilder footerBuilder = new StringBuilder();
        footerBuilder.append(String.format("Amount owed is <B>%s</B>.<BR/>", this.customer.getTotalAmount()));
        footerBuilder.append(String.format("You earned <B>%s</B> frequent renter points.<BR/>", this.customer.getTotalFRP()));
        return footerBuilder.toString();
    }

    @Override
    public String body() {
        StringBuilder bodyBuilder = new StringBuilder();
        rentals.forEach(rental -> bodyBuilder.append(String.format("<B>%s</B>: %s<BR/>", rental.getMovie().getTitle(), rental.amount())) );
        return bodyBuilder.toString();
    }
}
