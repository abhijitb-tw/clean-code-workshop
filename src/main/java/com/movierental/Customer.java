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

  public String htmlStatement() {
      StringBuilder htmlStmtBuilder = new StringBuilder();

      htmlStmtBuilder.append(htmlHeader());

      // body
      rentals.forEach(rental -> htmlStmtBuilder.append(htmlLineBody(rental)) );

      htmlStmtBuilder.append(htmlFooter());

      return htmlStmtBuilder.toString();
  }

  String htmlHeader() {
      return String.format("<H1>Rental Record for %s</H1>", getName());
  }

  static String htmlLineBody(Rental rental) {
      return String.format("<B>%s</B>: %s<BR/>", rental.getMovie().getTitle(), rental.amount());
  }

  String htmlFooter() {
      StringBuilder footerBuilder = new StringBuilder();
      footerBuilder.append(String.format("Amount owed is <B>%s</B>.<BR/>", getTotalAmount()));
      footerBuilder.append(String.format("You earned <B>%s</B> frequent renter points.<BR/>", getTotalFRP()));
      return footerBuilder.toString();
  }
}

