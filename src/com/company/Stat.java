package com.company;

/**
 * Created by danielpredmore on 10/27/16.
 */
public class Stat {

    public static Dbl toValue(double value) {
        return new Dbl(value);
    }

    public static Lng toValue(long value) {
        return new Lng(value);
    }

    public static Ratio toValue(long a, long b) {
        return new Ratio(a,b);
    }


    /**
     * This function returns the least common multiple of two longs
     *
     * Please note that both a and b must be positive
     *
     * @param a the first value
     * @param b the second value
     * @return the LCM of a and b
     */
    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    /**
     * This function finds the greatest common divisor of two longs
     *
     * @param a the first value
     * @param b the second value
     * @return the GCD of a and b
     */
    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Finds the factorial of the given number
     *
     * @param number input value
     * @return the factorial of the input
     */
    public static long factorial(long number) {
        long value = 1;
        for (int i = 1; i <= number; i++) {
            value *= i;
        }
        return value;
    }

    public static Value factorialQuotient(long a, long b) {
        long value;
        Value ratio = null;

        if (a < b) {
            value = 1;
            for (long i = a + 1; i <= b; i++) {
                value *= i;
            }

            ratio = new Ratio(1, value);
        } else if(a > b) {
            value = 1;
            for (long i = b + 1; i <= a; i++) {
                System.out.println(i);
                value *= i;
            }

            ratio = toValue(value);
        } else {
            ratio = toValue(1);
        }

        return ratio;
    }

    /**
     * This calculates the combinations of n and r
     *
     * @param n total number of objects
     * @param r number of desired objects
     * @return the combinations of n and r
     */
    public static Value ncr(long n, long r) {
        return npr(n,r).divide(toValue(factorial(r)));
    }

    /**
     * This calculates the permutations of n and r
     *
     * @param n total number of objects
     * @param r number of desired objects
     * @return the permutations of n and r
     */
    public static Value npr(long n, long r) {
        return new Ratio(factorial(n), factorial(n-r));
    }

    /**
     * calculates the multiply of an array of ratios
     *
     * @param values the array of ratios
     * @return the multiply of the ratios
     */
    public static Value product(Value[] values) {
        Value theValue = toValue(1);
        for (Value value: values) {
            theValue = theValue.multiply(value);
        }
        return theValue;
    }

    /**
     * This is the simplest method of finding a ratio from a double.
     * There is no loss of information however the out put is not nice.
     *
     * @param value the decimal to be converted
     * @return the converted ratio
     */
    public static Ratio convertToRatio(double value) {
        int power = 0;
        while (value - (long) value != 0) {
            value *= 10;
            power++;
        }
        return new Ratio((long) value,(long) Math.pow(10, power));
    }

    /**
     * Approximates a decimal to a ratio with a given tolerance.
     * The output looks nice however data is lost
     *
     * @param value the decimal to be approximated
     * @param tolerance the tolerance of the output
     * @return an approximated ratio
     */
    public static Ratio approximateToRatio(double value, double tolerance) {
        int sign = (int) Math.signum(value);
        value = Math.abs(value);
        double h1 = 1;
        double h2 = 0;
        double k1 = 0;
        double k2 = 1;
        double b = value;

        do {
            double a =  Math.floor(b);
            double aux = h1;
            h1 = a * h1 + h2;
            h2 = aux;
            aux = k1;
            k1 = a * k1 + k2;
            k2 = aux;
            b = 1/(b-a);
        } while (Math.abs(value-h1/k1) > value*tolerance);

        System.out.println(sign);
        return new Ratio((long) h1 * sign, (long) k1);
    }

    /**
     * approximates a ratio with a default tolerance of 1.0E-6
     *
     * @param value the value to be approximated
     * @return the approximated ratio
     */
    public static Ratio approximateToRatio(double value) {
        return approximateToRatio(value, 1.0E-6);
    }

}
