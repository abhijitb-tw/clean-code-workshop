package com.movierental;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

public class CustomerTest {
    /**
     * Movie specific rental statement [partial]
     */
    static class RentalStatement {
        /**
         * Partial text statement
         */
        private String statement;
        /**
         * Rental billed amount
         */
        private double amount;
        /**
         * Reward points generated through rental
         */
        private int frp;

        RentalStatement(String statement, double amount, int frp) {
            this.statement = statement;
            this.amount = amount;
            this.frp = frp;
        }

        String getStatement() {
            return statement;
        }

        double getAmount() {
            return amount;
        }

        int getFrequentRenterPoints() {
            return frp;
        }
    }

    private String expectedStatement(List<Rental> rentals) {
        StringBuilder expectedStatementBuilder = new StringBuilder();
        expectedStatementBuilder.append(String.format("Rental Record for %s\n", customer.getName()));


        int frp = 0;
        double amount = 0;
        for (Rental rental : rentals) {
            RentalStatement rs;
            switch (rental.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    rs = regularRentalStatement(rental);
                    break;
                case Movie.CHILDRENS:
                    rs = childrenRentalStatement(rental);
                    break;
                case Movie.NEW_RELEASE:
                    rs = newReleaseRentalStatement(rental);
                    break;
                default:
                    throw new UnsupportedOperationException();

            }
            expectedStatementBuilder.append(rs.getStatement());
            amount += rs.getAmount();
            frp += rs.getFrequentRenterPoints();
        }

        expectedStatementBuilder
                .append("Amount owed is ").append(amount)
                .append('\n')
                .append(String.format("You earned %d frequent renter points", frp));

        return expectedStatementBuilder.toString();

    }

    private static RentalStatement rentalStatement(
            Rental rental,
            final int minRentPeriod,
            final double baseCharge,
            final double surcharge,
            ToIntFunction<Rental> frpCalc
    ) {
        double amount = baseCharge;
        if (rental.getDaysRented() > minRentPeriod)
            amount += (rental.getDaysRented() - minRentPeriod) * surcharge;

        String textStatement = String.format(
                "\t%s\t%s\n",
                rental.getMovie().getTitle(),
                amount
        );

        return new RentalStatement(textStatement, amount, frpCalc.applyAsInt(rental));
    }

    private static RentalStatement regularRentalStatement(Rental regular) {
        return rentalStatement(regular, 2, 2.0, 1.5, r -> 1);
    }

    private static RentalStatement childrenRentalStatement(Rental children) {
        return rentalStatement(children, 3, 1.5, 1.5, c -> 1);
    }

    private static RentalStatement newReleaseRentalStatement(Rental newRelease) {
        return rentalStatement(newRelease, 0, 0, 3, nr -> nr.getDaysRented() > 1 ? 2 : 1);
    }

    private static String expectedHtmlBody(List<Rental> rentals) {
        StringBuilder htmlBuilder = new StringBuilder();
        rentals.forEach(rental -> htmlBuilder.append(Customer.htmlLineBody(rental)) );
        return htmlBuilder.toString();
    }

    private String expectedHtmlStatement(List<Rental> rentals) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append(customer.htmlHeader());
        htmlBuilder.append(expectedHtmlBody(rentals));
        htmlBuilder.append(customer.htmlFooter());
        return htmlBuilder.toString();
    }

    private Customer customer;

    @Before
    public void setup() {
        customer = new Customer("Abhijit");
    }

    @Test
    public void test() {
    }

    @Test
    public void testHtmlHeaderFormat() {
        Assert.assertEquals(String.format("<H1>Rental Record for %s</H1>", customer.getName()), customer.htmlHeader());
    }

    @Test
    public void testHtmlFooterFormat() {
        String expectedFooterFormat = String.format("Amount owed is <B>%s</B>.<BR/>You earned <B>%s</B> frequent renter points.<BR/>", 0.0, 0);
        Assert.assertEquals(expectedFooterFormat, customer.htmlFooter());
    }

    @Test
    public void testStatement_Regular() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Forest Gump", Movie.REGULAR), 1));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testStatement_Regular_MoreThan2Days() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("X-Men", Movie.REGULAR), 5));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testStatement_Regulars() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Forest Gump", Movie.REGULAR), 1));
        rentals.add(new Rental(new Movie("X-Men", Movie.REGULAR), 5));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testHtmlStatement_Regulars() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Forest Gump", Movie.REGULAR), 1));
        rentals.add(new Rental(new Movie("X-Men", Movie.REGULAR), 5));

        rentals.forEach(customer::addRental);
        Assert.assertEquals(expectedHtmlStatement(rentals), customer.htmlStatement());
    }

    @Test
    public void testStatement_Children() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Boss Baby", Movie.CHILDRENS), 2));
        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testStatement_Children_MoreThan3Days() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Boss Baby", Movie.CHILDRENS), 12));
        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testStatement_ChildrenMovies() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Zootopia", Movie.CHILDRENS), 1));
        rentals.add(new Rental(new Movie("The Incredibles", Movie.CHILDRENS), 5));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testHtmlStatement_ChildrenMovies() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Zootopia", Movie.CHILDRENS), 1));
        rentals.add(new Rental(new Movie("The Incredibles", Movie.CHILDRENS), 5));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedHtmlStatement(rentals), customer.htmlStatement());
    }


    @Test
    public void testStatement_NewRelease() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Avengers: Endgame", Movie.NEW_RELEASE), 1));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testHtmlStatement_NewRelease() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Avengers: Endgame", Movie.NEW_RELEASE), 1));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedHtmlStatement(rentals), customer.htmlStatement());
    }

    @Test
    public void testStatement_NewRelease_MoreThan1Days() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Avengers: Endgame", Movie.NEW_RELEASE), 4));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testStatement_NewReleases() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Avengers: Endgame", Movie.NEW_RELEASE), 1));
        rentals.add(new Rental(new Movie("The Tashkent Files", Movie.NEW_RELEASE), 4));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testHtmlStatement_NewReleases() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Avengers: Endgame", Movie.NEW_RELEASE), 1));
        rentals.add(new Rental(new Movie("The Tashkent Files", Movie.NEW_RELEASE), 4));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedHtmlStatement(rentals), customer.htmlStatement());
    }

    @Test
    public void testStatement_Movies() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Forest Gump", Movie.REGULAR), 1));
        rentals.add(new Rental(new Movie("X-Men", Movie.REGULAR), 5));
        rentals.add(new Rental(new Movie("Zootopia", Movie.CHILDRENS), 1));
        rentals.add(new Rental(new Movie("The Incredibles", Movie.CHILDRENS), 5));
        rentals.add(new Rental(new Movie("Avengers: Endgame", Movie.NEW_RELEASE), 1));
        rentals.add(new Rental(new Movie("The Tashkent Files", Movie.NEW_RELEASE), 4));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedStatement(rentals), customer.statement());
    }

    @Test
    public void testHtmlStatement_Movies() {
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(new Movie("Forest Gump", Movie.REGULAR), 1));
        rentals.add(new Rental(new Movie("X-Men", Movie.REGULAR), 5));
        rentals.add(new Rental(new Movie("Zootopia", Movie.CHILDRENS), 1));
        rentals.add(new Rental(new Movie("The Incredibles", Movie.CHILDRENS), 5));
        rentals.add(new Rental(new Movie("Avengers: Endgame", Movie.NEW_RELEASE), 1));
        rentals.add(new Rental(new Movie("The Tashkent Files", Movie.NEW_RELEASE), 4));

        rentals.forEach(customer::addRental);

        Assert.assertEquals(expectedHtmlStatement(rentals), customer.htmlStatement());
    }

}