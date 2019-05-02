package com.movierental;

public class Rental {

  private int daysRented;
  private Movie movie;

  public Rental(Movie movie, int daysRented){
    this.movie = movie;
    this.daysRented = daysRented;
  }

  public int getDaysRented() {
    return daysRented;
  }

  public Movie getMovie() {
    return movie;
  }

  private RentalCategory getRentalCategory() {
    switch (this.getMovie().getPriceCode()) {
      case Movie.REGULAR:
        return RentalCategory.REGULAR;
      case Movie.CHILDRENS:
        return RentalCategory.CHILDREN;
      case Movie.NEW_RELEASE:
        return RentalCategory.NEW_RELEASE;
    }

    throw new UnsupportedOperationException(String.format("%d: Unsupported Price Code", this.movie.getPriceCode()));
  }

  public double amount() {
    RentalCategory category = getRentalCategory();
    double amount = category.getBaseCharge();
    if(this.getDaysRented() > category.getMinRentPeriod()) {
      amount += (this.getDaysRented() - category.getMinRentPeriod()) * category.getSurcharge();
    }
    return amount;
  }

  public int frequentRenterPoints() {
    int frp = 1;
    if((getRentalCategory() == RentalCategory.NEW_RELEASE) && getDaysRented() > RentalCategory.NEW_RELEASE.getMinRentPeriod())
      ++frp;
    return frp;
  }


}