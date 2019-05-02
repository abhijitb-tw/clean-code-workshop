package com.movierental;


public class TextStatement implements Statement {
    private final String name;
    private final RentalCollection rentals;

    public TextStatement(String name, RentalCollection rentals) {
        this.name = name;
        this.rentals = rentals;
    }

    @Override
    public String header() {
        return "Rental Record for " + this.name + "\n";
    }

    @Override
    public String footer() {
        StringBuilder footerBuilder = new StringBuilder();
        footerBuilder.append("Amount owed is ")
                .append(rentals.getTotalAmount()).append('\n')
                .append("You earned ").append(rentals.getTotalFRP())
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
