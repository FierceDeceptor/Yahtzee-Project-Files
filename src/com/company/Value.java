package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 */
public interface Value extends Comparable<Value> {

    Value multiply(Value other);

    Value divide(Value other);

    Value add(Value other);

    Value subtract(Value other);

    double toDouble();

    long toLong();

    Lng toLng();

    Ratio toRatio();

    Dbl toDbl();

    boolean isRational();
}
