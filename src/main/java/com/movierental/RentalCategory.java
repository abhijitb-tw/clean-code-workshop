package com.movierental;

/**
 * {@link RentalCategory} performs rental calculation including amount and frequent renter points
 */
enum RentalCategory {
    REGULAR(Movie.REGULAR, 2, 2, 1.5),
    CHILDREN(Movie.CHILDRENS, 3, 1.5, 1.5),
    NEW_RELEASE(Movie.NEW_RELEASE, 1, 3, 3);

    private final int movieCategory;
    private final int minRentPeriod;
    private final double baseCharge;
    private final double surcharge;

    RentalCategory(int movieCategory, int minRentPeriod, double baseCharge, double surcharge) {
        this.movieCategory = movieCategory;
        this.minRentPeriod = minRentPeriod;
        this.baseCharge = baseCharge;
        this.surcharge = surcharge;
    }

    public int getMovieCategory() {
        return movieCategory;
    }

    public int getMinRentPeriod() {
        return minRentPeriod;
    }

    public double getBaseCharge() {
        return baseCharge;
    }

    public double getSurcharge() {
        return surcharge;
    }
}
