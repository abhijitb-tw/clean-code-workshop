package com.movierental;

import java.util.ArrayList;

public class Customer {
  private String name;
  private final RentalCollection rentals = new RentalCollection(new ArrayList<>());

  public Customer(String name) {
    this.name = name;
  }

  public void addRental(Rental rental) {
    rentals.addRental(rental);
  }

  public String getName() {
    return name;
  }

  public RentalCollection getRentals() {
      return this.rentals;
  }

  public String statement() {
      Statement statement = new TextStatement(this.name, rentals);
      return statement.statement();
  }

  public String htmlStatement() {
      Statement statement = new HtmlStatement(this.name, this.rentals);
      return statement.statement();
  }
}

