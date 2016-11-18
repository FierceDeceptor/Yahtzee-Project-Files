package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 *
 * This object holds double values
 *
 */
public class Dbl implements Value {
    private double value;

    /**
     * Constructor for the dbl class
     *
     * @param value the value of the double
     */
    protected Dbl(double value) {
        this.value = value;
    }


    /**
     * Converts a value into a double
     *
     * @return the double value
     */
    @Override
    public double toDouble() {
        return value;
    }

    /**
     * Converts a value into a long
     *
     * @return the long value
     */
    @Override
    public long toLong() {
        return (long) value;
    }

    @Override
    public int compareTo(Value other) {
        return Double.compare(this.toDouble(),other.toDouble());
    }


    /**
     * Multiplies the values of two value objects
     *
     * @param other the other value
     * @return the product of the two values
     */
    @Override
    public Value multiply(Value other) {
        return new Dbl(this.value * other.toDouble());
    }

    /**
     * Divides two value objects
     *
     * @param other the divisor
     * @return the quotient of the two values
     */
    @Override
    public Value divide(Value other) {
        return new Dbl(this.value / other.toDouble());
    }

    /**
     * Adds two value objects
     *
     * @param other the other value
     * @return the sum of the two values
     */
    @Override
    public Value add(Value other) {
        return new Dbl(this.value + other.toDouble());
    }

    /**
     * Subtracts the values of two objects
     *
     * @param other the other value object
     * @return the difference of the values
     */
    @Override
    public Value subtract(Value other) {
        return new Dbl(this.value - other.toDouble());
    }

    @Override
    public Value power(Value other) {
        return new Dbl(Math.pow(this.toDouble(), other.toDouble()));
    }

    /**
     * Converts a value into a Lng object
     *
     * @return the converted Lng
     */
    @Override
    public Lng toLng() {
        return new Lng((long) value);
    }

    /**
     * Converts a value into a Ratio object
     *
     * @return the converted Ratio
     */
    @Override
    public Ratio toRatio() {
        return Stat.approximateToRatio(value);
    }

    /**
     * Converts a value to a Dbl object
     *
     * @return the converted Dbl
     */
    @Override
    public Dbl toDbl() {
        return this;
    }

    /**
     * Finds if a object is rational
     *
     * @return true if rational
     */
    @Override
    public boolean isRational() {
        return false;
    }

    public String toString() {
        return Double.toString(value);
    }
}
