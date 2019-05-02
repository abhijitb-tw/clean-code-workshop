package com.movierental;

/**
 * {@link Statement} interface for generating statements in appropriate formats
 */
public interface Statement {
    String header();
    String footer();
    String body();

    default String statement() {
        return header() + body() + footer();
    }
}
