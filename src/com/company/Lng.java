package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 */
public class Lng implements Value {
    private long value;

    protected Lng(long value) {
        this.value = value;
    }

    public long getValue() {return value;}

    public long toLong() {return value;}

    public double toDouble() {
        return (double) value;
    }

    public Value multiply(Value other) {
        if (other instanceof Lng) {
            return new Lng(this.value * ((Lng) other).value);
        }

        return other.multiply(this);
    }

    public Value divide(Value other) {
        if (other instanceof Lng) {
            return new Ratio(this.value, ((Lng) other).value);
        }

        return new Ratio(value, 1).divide(other);
    }

    public Value add(Value other) {
        if (other instanceof Lng) {
            return new Lng(this.value + ((Lng) other).value);
        }

        return other.add(this);
    }

    public Value subtract(Value other) {
        if (other instanceof Lng) {
            return new Lng(this.value - ((Lng) other).value);
        }

        return this.add(other.multiply(new Lng(-1)));
    }

    public Lng toLng() {
        return this;
    }

    public Ratio toRatio() {
        return new Ratio(value, 1);
    }

    public Dbl toDbl() {
        return new Dbl(toDouble());
    }

    public boolean isRational() {
        return true;
    }

    @Override
    public int compareTo(Value other) {
        return (int) this.toRatio().subtract(other.toRatio()).toRatio().getNumerator();
    }

    public String toString() {
        return Long.toString(value);
    }

}
