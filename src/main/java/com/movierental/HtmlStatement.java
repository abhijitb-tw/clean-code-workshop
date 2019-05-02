package com.movierental;

public class HtmlStatement implements Statement {
    private final String name;
    private final RentalCollection rentals;

    public HtmlStatement(String name, RentalCollection rentals) {
        this.name = name;
        this.rentals = rentals;
    }

    @Override
    public String header() {
        return String.format("<H1>Rental Record for <B>%s</B></H1>", this.name);
    }

    @Override
    public String footer() {
        StringBuilder footerBuilder = new StringBuilder();
        footerBuilder.append(String.format("Amount owed is <B>%s</B>.<BR/>", this.rentals.getTotalAmount()));
        footerBuilder.append(String.format("You earned <B>%s</B> frequent renter points.<BR/>", this.rentals.getTotalFRP()));
        return footerBuilder.toString();
    }

    @Override
    public String body() {
        StringBuilder bodyBuilder = new StringBuilder();
        rentals.forEach(rental -> bodyBuilder.append(String.format("<B>%s</B>: %s<BR/>", rental.getMovie().getTitle(), rental.amount())) );
        return bodyBuilder.toString();
    }
}
