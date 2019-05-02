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
    String result = "Rental Record for " + getName() + "\n";

    // This refactor, however, forces amount to be calculated twice!!

    for (Rental rental : rentals) {
      //show figures for this rental
      result += "\t" + rental.getMovie().getTitle() + "\t" +
          String.valueOf(rental.amount()) + "\n";
    }

    //add footer lines result
    result += "Amount owed is " + getTotalAmount() + "\n";
    result += "You earned " + getTotalFRP()
        + " frequent renter points";
    return result;
  }

  private int getTotalFRP() {
      return this.rentals.stream().mapToInt(Rental::frequentRenterPoints).sum();
  }


  private double getTotalAmount() {
      return this.rentals.stream().mapToDouble(Rental::amount).sum();
  }
}

