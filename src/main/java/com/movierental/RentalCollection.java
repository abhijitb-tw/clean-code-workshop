package com.movierental;

import java.util.List;
import java.util.function.Consumer;

/**
 * Encapsulates all rentals and also performs calculations on the underlying rental collection
 */
public class RentalCollection {
    private final List<Rental> rentals;

    public RentalCollection(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addRental(final Rental rental) {
        this.rentals.add(rental);
    }

    public int getTotalFRP() {
        return this.rentals.stream().mapToInt(Rental::frequentRenterPoints).sum();
    }


    public double getTotalAmount() {
        return this.rentals.stream().mapToDouble(Rental::amount).sum();
    }

    public void forEach(Consumer<Rental> consumer) {
        this.rentals.forEach(consumer::accept);
    }
}
