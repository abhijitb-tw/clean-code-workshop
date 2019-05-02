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
    double totalAmount = 0;
    int frequentRenterPoints = 0;
    String result = "Rental Record for " + getName() + "\n";
    for (Rental each : rentals) {
      double thisAmount = 0;
      //determine amounts for each line
      switch (each.getMovie().getPriceCode()) {
        case Movie.REGULAR:
          thisAmount += RentalCalc.REGULAR.getBaseCharge();
          if (each.getDaysRented() > RentalCalc.REGULAR.getMinRentPeriod())
            thisAmount += (each.getDaysRented() - RentalCalc.REGULAR.getMinRentPeriod()) * RentalCalc.REGULAR.getSurcharge();
          break;
        case Movie.NEW_RELEASE:
          thisAmount += each.getDaysRented() * RentalCalc.NEW_RELEASE.getSurcharge();
          break;
        case Movie.CHILDRENS:
          thisAmount += RentalCalc.CHILDREN.getBaseCharge();
          if (each.getDaysRented() > RentalCalc.CHILDREN.getMinRentPeriod())
            thisAmount += (each.getDaysRented() - RentalCalc.CHILDREN.getMinRentPeriod()) * RentalCalc.CHILDREN.getSurcharge();
          break;
      }
      // add frequent renter points
      frequentRenterPoints++;
      // add bonus for a two day new release rental
      if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
          &&
          each.getDaysRented() > RentalCalc.NEW_RELEASE.getMinRentPeriod()) frequentRenterPoints++;

      //show figures for this rental
      result += "\t" + each.getMovie().getTitle() + "\t" +
          String.valueOf(thisAmount) + "\n";
      totalAmount += thisAmount;
    }

    //add footer lines result
    result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
    result += "You earned " + String.valueOf(frequentRenterPoints)
        + " frequent renter points";
    return result;
  }
}

