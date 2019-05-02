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
      double thisAmount = amountFor(each);
      // add frequent renter points
      frequentRenterPoints++;
      // add bonus for a two day new release rental
      if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
          &&
          each.getDaysRented() > RentalCategory.NEW_RELEASE.getMinRentPeriod()) frequentRenterPoints++;

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

  private double amountFor(Rental rental) {
    RentalCategory category = getRentalCategory(rental);
    double amount = category.getBaseCharge();
    if(rental.getDaysRented() > category.getMinRentPeriod()) {
      amount += (rental.getDaysRented() - category.getMinRentPeriod()) * category.getSurcharge();
    }
    return amount;
  }

  private RentalCategory getRentalCategory(Rental rental) {
    switch (rental.getMovie().getPriceCode()) {
      case Movie.REGULAR:
        return RentalCategory.REGULAR;
      case Movie.CHILDRENS:
        return RentalCategory.CHILDREN;
      case Movie.NEW_RELEASE:
        return RentalCategory.NEW_RELEASE;
    }

    throw new UnsupportedOperationException(String.format("%d: Unsupported Price Code", rental.getMovie().getPriceCode()));
  }
}

