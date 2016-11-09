package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 *
 * This interface controls a system of numbers composed of
 * Dbl : double values
 * Ratio : fractional values
 * Lng : integer values
 *
 * Calculations between these objects have the following inheritance
 * Lng -> Ratio -> Dbl
 *
 * To create a value use the method Stat.value(input)
 */
public interface Value extends Comparable<Value> {

    /**
     * Multiplies the values of two value objects
     *
     * @param other the other value
     * @return the product of the two values
     */
    Value multiply(Value other);

    /**
     * Divides two value objects
     *
     * @param other the divisor
     * @return the quotient of the two values
     */
    Value divide(Value other);

    /**
     * Adds two value objects
     *
     * @param other the other value
     * @return the sum of the two values
     */
    Value add(Value other);

    /**
     * Subtracts the values of two objects
     *
     * @param other the other value object
     * @return the difference of the values
     */
    Value subtract(Value other);

    Value power(Value other);

    /**
     * Converts a value into a double
     *
     * @return the double value
     */
    double toDouble();

    /**
     * Converts a value into a long
     *
     * @return the long value
     */
    long toLong();

    /**
     * Converts a value into a Lng object
     *
     * @return the converted Lng
     */
    Lng toLng();

    /**
     * Converts a value into a Ratio object
     *
     * @return the converted Ratio
     */
    Ratio toRatio();

    /**
     * Converts a value to a Dbl object
     *
     * @return the converted Dbl
     */
    Dbl toDbl();

    /**
     * Finds if a object is rational
     *
     * @return true if rational
     */
    boolean isRational();
}
