package com.movierental;

import java.util.ArrayList;
import java.util.List;

public class Customer {
  private String name;
  private List<Rental> rentals = new ArrayList<>();

  public Customer(String name) {
    this.name = name;
  }

  public void addRental(Rental arg) {
    rentals.add(arg);
  }

  public String getName() {
    return name;
  }

  public String statement() {
      Statement statement = new TextStatement(this, rentals);
      return statement.statement();
  }

  public int getTotalFRP() {
      return this.rentals.stream().mapToInt(Rental::frequentRenterPoints).sum();
  }


  public double getTotalAmount() {
      return this.rentals.stream().mapToDouble(Rental::amount).sum();
  }

  public String htmlStatement() {
      Statement statement = new HtmlStatement(this, this.rentals);
      return statement.statement();
  }
}

