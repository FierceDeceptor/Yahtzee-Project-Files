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


    @Override
    public double toDouble() {
        return value;
    }

    @Override
    public long toLong() {
        return (long) value;
    }

    @Override
    public int compareTo(Value other) {
        return (int) this.toRatio().subtract(other.toRatio()).toRatio().getNumerator();
    }


    @Override
    public Value multiply(Value other) {
        return new Dbl(this.value * other.toDouble());
    }

    @Override
    public Value divide(Value other) {
        return new Dbl(this.value / other.toDouble());
    }

    @Override
    public Value add(Value other) {
        return new Dbl(this.value + other.toDouble());
    }

    @Override
    public Value subtract(Value other) {
        return new Dbl(this.value - other.toDouble());
    }

    @Override
    public Lng toLng() {
        return new Lng((long) value);
    }

    @Override
    public Ratio toRatio() {
        return Stat.approximateToRatio(value);
    }

    @Override
    public Dbl toDbl() {
        return this;
    }

    @Override
    public boolean isRational() {
        return false;
    }

    public String toString() {
        return Double.toString(value);
    }
}
