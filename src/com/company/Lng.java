package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 *
 * This object is a value that stores a long
 */
public class Lng implements Value {
    private long value;

    /**
     * Constructor for Lng objects
     *
     * @param value the value of the Lng
     */
    protected Lng(long value) {
        this.value = value;
    }

    /**
     * Converts a value into a long
     *
     * @return the long value
     */
    @Override
    public long toLong() {return value;}

    /**
     * Converts a value into a double
     *
     * @return the double value
     */
    @Override
    public double toDouble() {
        return (double) value;
    }

    /**
     * Multiplies the values of two value objects
     *
     * @param other the other value
     * @return the product of the two values
     */
    @Override
    public Value multiply(Value other) {
        if (other instanceof Lng) {
            return new Lng(this.value * ((Lng) other).value);
        }

        return other.multiply(this);
    }

    /**
     * Divides two value objects
     *
     * @param other the divisor
     * @return the quotient of the two values
     */
    @Override
    public Value divide(Value other) {
        if (other instanceof Lng) {
            return new Ratio(this.value, ((Lng) other).value);
        }

        return new Ratio(value, 1).divide(other);
    }

    /**
     * Adds two value objects
     *
     * @param other the other value
     * @return the sum of the two values
     */
    @Override
    public Value add(Value other) {
        if (other instanceof Lng) {
            return new Lng(this.value + ((Lng) other).value);
        }

        return other.add(this);
    }

    /**
     * Subtracts the values of two objects
     *
     * @param other the other value object
     * @return the difference of the values
     */
    @Override
    public Value subtract(Value other) {
        if (other instanceof Lng) {
            return new Lng(this.value - ((Lng) other).value);
        }

        return this.add(other.multiply(new Lng(-1)));
    }

    @Override
    public Value power(Value other) {
        if (other instanceof Lng) {
            if (other.toLong() >= 0) {
                return new Dbl(Math.pow(this.toDouble(), other.toDouble())).toLng();
            } else {
                return new Ratio(1, (long) Math.pow(this.toDouble(), Math.abs(other.toDouble())));
            }
        }

        return new Dbl(Math.pow(this.toDouble(), other.toDouble()));
    }

    /**
     * Converts a value into a Lng object
     *
     * @return the converted Lng
     */
    @Override
    public Lng toLng() {
        return this;
    }

    /**
     * Converts a value into a Ratio object
     *
     * @return the converted Ratio
     */
    @Override
    public Ratio toRatio() {
        return new Ratio(value, 1);
    }

    /**
     * Converts a value to a Dbl object
     *
     * @return the converted Dbl
     */
    @Override
    public Dbl toDbl() {
        return new Dbl(toDouble());
    }

    /**
     * Finds if a object is rational
     *
     * @return true if rational
     */
    @Override
    public boolean isRational() {
        return true;
    }

    @Override
    public int compareTo(Value other) {
        return Double.compare(this.toDouble(),other.toDouble());
    }

    public String toString() {
        return Long.toString(value);
    }

}
