package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

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

    public static Value toValue(String str) {
        if (str.contains(".")) {
            return toValue(new Scanner(str).nextDouble());
        }
        return toValue(new Scanner(str).nextLong());
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

    /**
     * This method generates all the combinations of a set with a certain group size.
     * @param set The set that the groups are derived from.
     * @param size The group size of all the combinations.
     * @return A list of all possible groups from the set with a certain group size.
     */
    public static ArrayList<int[]> generateCombination(int[] set, int size){
        ArrayList combinations = new ArrayList();
        generateCombination(set, size, 0, new int[size], combinations);
        return combinations;
    }

    /**
     *
     * @param set
     * @param size
     * @param start
     * @param combination
     * @param list
     */
    private static void generateCombination(int[] set, int size, int start, int[] combination, ArrayList<int[]> list){
        if(size == 0){
            list.add(combination.clone());
        }
        else{
            for(int i = start;i <= set.length - size;i++){
                combination[combination.length - size] = set[i];
                generateCombination(set, size - 1, i + 1, combination, list);
            }
        }
    }

    /**
     * THis method generates all the permutations of a set with a certain group size.
     * @param set The set that the groups are derived from.
     * @param size The group size of all the permutations.
     * @return A list of all possible groups from the set with a certain group size.
     */
    public static ArrayList<int[]> generatePermutation(Set<Integer> set, int size){
        return null;
    }

    /**
     * This method rounds a value to a specific number of decimal places and returns the result.
     * @param value The value that is being rounded.
     * @param decimalPlace The number of decimal places that the number is being rounded to.
     * @return The value that has been rounded.
     */
    public static double round(double value, int decimalPlace){
        return (int)(value * Math.pow(10, decimalPlace) + 0.5) / Math.pow(10, decimalPlace);
    }

    static public Ratio probabilityMatchingDice(int matchingDice, int numberOfRolls){
        // The transition matrix builder.
        Matrix.Builder tBuilder = new Matrix.Builder(6, 6);

        // Set the first row of the transition matrix.
        tBuilder.set(Stat.toValue(3125,7776), 0, 0);
        tBuilder.set(Stat.toValue(3125,7776), 0, 1);
        tBuilder.set(Stat.toValue(1250,7776), 0, 2);
        tBuilder.set(Stat.toValue(250,7776), 0, 3);
        tBuilder.set(Stat.toValue(25,7776), 0, 4);
        tBuilder.set(Stat.toValue(1,7776), 0, 5);

        // Set the second row of the transition matrix.
        tBuilder.set(Stat.toValue(625,1296), 1, 1);
        tBuilder.set(Stat.toValue(500,1296), 1, 2);
        tBuilder.set(Stat.toValue(150,1296), 1, 3);
        tBuilder.set(Stat.toValue(20,1296), 1, 4);
        tBuilder.set(Stat.toValue(1,1296), 1, 5);

        // Set the third row of the transition matrix.
        tBuilder.set(Stat.toValue(125,216), 2, 2);
        tBuilder.set(Stat.toValue(75,216), 2, 3);
        tBuilder.set(Stat.toValue(15,216), 2, 4);
        tBuilder.set(Stat.toValue(1,216), 2, 5);


        // Set the fourth row of the transition matrix.
        tBuilder.set(Stat.toValue(25,36), 3, 3);
        tBuilder.set(Stat.toValue(10,36), 3, 4);
        tBuilder.set(Stat.toValue(1,36), 3, 5);

        // Set the fifth row of the transition matrix.
        tBuilder.set(Stat.toValue(5,6), 4, 4);
        tBuilder.set(Stat.toValue(1,6), 4, 5);

        // Let the fifth row of the transition matrix.
        tBuilder.set(Stat.toValue(1,1), 5, 5);

        // The probability matrix builder.
        Matrix.Builder pBuilder = new Matrix.Builder(6, 1);

        // The last row is a 1.
        pBuilder.set(Stat.toValue(1,1), 5, 0);

        Matrix transition = tBuilder.Build();
        Matrix probability = pBuilder.Build();

        // Multiply the transition matrix by the probability matrix rollsLeft times.
        for(int i = 0; i < numberOfRolls;i++ ){
            probability = transition.multiply(probability);
        }

        // The probability for the different number of dice is stored in the probability matrix.
        return (Ratio)probability.get(5 - matchingDice, 0);
    }
}
