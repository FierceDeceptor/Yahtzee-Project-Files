package com.company;

import java.util.DoubleSummaryStatistics;

/**
 * Created by danielpredmore on 10/27/16.
 *
 * This Class provides an object to store and calculate fractions
 */
public class Ratio implements Value {
    private long numerator;
    private long denominator;
    private int sign;

    /**
     * This is the main constructor for Ratios
     *
     * @param numerator The value in the numerator
     * @param denominator The value in the denominator
     */
    protected Ratio(long numerator, long denominator) {
        sign = Long.signum(numerator)*Long.signum(denominator);
        this.numerator = Math.abs(numerator);
        this.denominator =  Math.abs(denominator);
        reduce();
    }



    /**
     * getter for the numerator
     *
     * @return numerator
     */
    public long getNumerator() {return numerator * sign;}

    /**
     * getter for the denominator
     *
     * @return denominator
     */
    public long getDenominator() {return denominator;}

    /**
     * Reduces the ratio to lowest terms
     */
    private void reduce() {
        long gcd = Stat.gcd(numerator, denominator);
        numerator = numerator / gcd;
        denominator = denominator / gcd;
    }

    @Override
    public double toDouble() {
        return (double) numerator / (double) denominator * sign;
    }

    @Override
    public long toLong() {
        return (long) toDouble();
    }

    @Override
    public Value multiply(Value other) {
        if (other instanceof Ratio) {
            return multiply((Ratio) other);
        } else if(other instanceof Lng) {
            return multiply(((Lng) other).getValue());
        }

        return new Dbl(this.toDouble() * other.toDouble());
    }

    @Override
    public Value divide(Value other) {
           if (other instanceof Ratio) {
               return divide((Ratio) other);
           } else if (other instanceof Lng) {
               return divide(((Lng) other).getValue());
           }

        return new Dbl(this.toDouble() / other.toDouble());
    }

    @Override
    public Value add(Value other) {
        if (other instanceof Ratio) {
            return add((Ratio) other);
        } else if (other instanceof Lng) {
            return add(((Lng) other).getValue());
        }

        return new Dbl(this.toDouble() + other.toDouble());
    }

    @Override
    public Value subtract(Value other) {
        if (other instanceof Ratio) {
            return subtract((Ratio) other);
        } else if (other instanceof Lng) {
            return subtract(((Lng) other).getValue());
        }

        return new Dbl(this.toDouble() - other.toDouble());
    }

    @Override
    public Lng toLng() {
        return new Lng((long) toDouble());
    }

    @Override
    public Ratio toRatio() {
        return this;
    }

    @Override
    public Dbl toDbl() {
        return new Dbl(toDouble());
    }

    @Override
    public boolean isRational() {
        return true;
    }

    @Override
    public int compareTo(Value other) {
        return (int) this.subtract(other.toRatio()).numerator;
    }


    /**
     * multiply two Ratios
     *
     * @param other the other Ratio being multiplied
     * @return the multiply of the two Ratios
     */
    private Ratio multiply(Ratio other) {
        long numerator = this.numerator * other.numerator * this.sign * other.sign;
        long denominator = this.denominator * other.denominator;
        return new Ratio(numerator, denominator);
    }

    /**
     * divide two Ratios
     *
     * @param other the Ratio in the denominator
     * @return divide of this ratio over the other ratio
     */
    private Ratio divide(Ratio other) {
        long numerator = this.numerator * other.denominator * this.sign * other.sign;
        long denominator = this.denominator * other.numerator;
        return new Ratio(numerator, denominator);
    }

    /**
     * finds the sum of this ratio and the other ratio
     *
     * @param other the ratio to be added
     * @return the sum of the two ratios
     */
    private Ratio add(Ratio other) {
        long denominator = Stat.lcm(this.denominator, other.denominator);
        long numerator = this.numerator * (denominator / this.denominator) * this.sign
                + other.numerator * (denominator / other.denominator) * other.sign;
        return new Ratio(numerator, denominator);
    }

    /**
     * finds the difference of two ratios
     *
     * @param other the ratio to be subtracted
     * @return the subtract of the two ratios
     */
    private Ratio subtract(Ratio other) {
        long denominator = Stat.lcm(this.denominator, other.denominator);
        long numerator = this.numerator * (denominator / this.denominator) * this.sign
                - other.numerator * (denominator / other.denominator) * other.sign;
        return new Ratio(numerator, denominator);
    }

    /**
     * multiplies a ratio by an long value
     *
     * @param number the value to be multiplied
     * @return the new ratio scaled by the value
     */
    private Ratio multiply(long number) {
        return new Ratio(number * numerator * this.sign, denominator);
    }

    /**
     * divides a ratio by a long value
     *
     * @param number the value to be divided
     * @return the new ratio scaled by the inverse of the value
     */
    private Ratio divide(long number) {
        return new Ratio(numerator * this.sign, number * denominator);
    }

    /**
     * adds a long and a ratio
     *
     * @param number the long being added
     * @return the add of the long and ratio
     */
    private Ratio add(long number) {
        return new Ratio(numerator * this.sign + number * denominator, denominator);
    }

    /**
     * subtracts a long from a ratio
     *
     * @param number the number to be subtracted
     * @return the ratio of the subtract of the ratio and number
     */
    private Ratio subtract(long number) {
        return new Ratio(numerator * this.sign - number * denominator, denominator);
    }

    /**
     * greater than comparison for Ratio
     *
     * @param other the Ratio being compared
     * @return boolean if greater than
     */
    public boolean isGreaterThan(Ratio other) {
        return this.toDouble() > other.toDouble();
    }

    /**
     * less than comparison for Ratio
     *
     * @param other the Ratio being compared
     * @return boolean if less than
     */
    public boolean isLessThan(Ratio other) {
        return this.toDouble() < other.toDouble();
    }

    /**
     * generates a copy of the ratio
     *
     * @return copied ratio
     */
    public Ratio copyOf() {
        return new Ratio(numerator * sign, denominator);
    }

    /**
     * equals operator for Ratio
     *
     * @param other the object to be compared
     * @return boolean if same value
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Ratio
                && ((Ratio) other).denominator == this.denominator
                && ((Ratio) other).numerator == this.numerator
                && ((Ratio) other).sign == this.sign;

    }

    /**
     * toString for ratio
     *
     * @return string
     */
    @Override
    public String toString() {
        if (denominator != 1) {
            return numerator * sign + "/" + denominator;
        }
        return numerator * sign + "";
    }
}
