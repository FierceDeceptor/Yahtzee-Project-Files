package com.company.misc;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This class is used to calculate the values of the transition matrix for several of the different roll types.
 * To run these calculation run the main method of this class.
 */
public class Derivations {

    public static void main(String[] args) {

        int[] results;
        System.out.println("Transition matrix for aces, twos, etc... ");
        for(int i = 5;i >= 1;i--){
            results = aces(i);
            System.out.println(Arrays.toString(results) + " += " + IntStream.of(results).sum());
        }

        System.out.println("Transition matrix for Full House:");
        results = fullHouseRow1();
        System.out.println(Arrays.toString(results) + " += " + IntStream.of(results).sum());

        results = fullHouseRow2();
        System.out.println(Arrays.toString(results) + " += " + IntStream.of(results).sum());

        results = fullHouseRow3();
        System.out.println(Arrays.toString(results) + " += " + IntStream.of(results).sum());

        results = fullHouseRow4();
        System.out.println(Arrays.toString(results) + " += " + IntStream.of(results).sum());

        results = fullHouseRow5();
        System.out.println(Arrays.toString(results) + " += " + IntStream.of(results).sum());

        results = fullHouseRow6();
        System.out.println(Arrays.toString(results) + " += " + IntStream.of(results).sum());
    }

    /**
     * #1 X X X X X - not possible
     * #2 A B X X X - 5 singles
     * #3 A A B X X - 1 pair & 3 singles
     * #4 A A A X X - all A
     * #5 A A B B X - 2 pairs & 1 single
     * #6 A A A B X - 1 triple & 2 singles
     * #7 A A A B B - 1 triple & 1 pair
     */

    private static int[] fullHouseRow1(){
        // Create an array with enough space for numDice.
        int[] values = new int[5];

        // Create an array with enough space for the diceProbabilities.
        int[] probabilities = new int[7];

        fullHouseRow1(0, values, probabilities);

        return probabilities;
    }

    private static void fullHouseRow1(int index, int[] values, int[] probabilities){
        if(index < 5){
            for(int i = 1;i <= 6;i++){
                values[index] = i;
                fullHouseRow1(index + 1, values, probabilities);
            }
        }
        else{

            int[] dist = new int[6];
            for(int i = 0; i < 5;i++){
                dist[values[i] - 1] += 1;
            }

            int[] info = {sets(dist, 1), sets(dist, 2), sets(dist, 3), sets(dist, 4), sets(dist, 5)};

            // this transition is not possible
            // fullHouse1to1(info)

            if(fullHouse1to2(info)){
                probabilities[1] += 1;
            }

            if(fullHouse1to3(info)){
                probabilities[2] += 1;
            }

            if(fullHouse1to4(info)){
                probabilities[3] += 1;
            }

            if(fullHouse1to5(info)){
                probabilities[4] += 1;
            }

            if(fullHouse1to6(info)){
                probabilities[5] += 1;
            }

            if(fullHouse1to7(info)){
                probabilities[6] += 1;
            }
        }
    }

     private static boolean fullHouse1to2(int[] info){
         return info[0] == 5;
    }

     private static boolean fullHouse1to3(int[] info){
        return info[1] == 1 && info[0] == 3;
    }

     private static boolean fullHouse1to4(int[] info){
        return info[4] == 1;
    }

     private static boolean fullHouse1to5(int[] info){
        return info[1] == 2 && info[0] == 1;
    }

     private static boolean fullHouse1to6(int[] info){
        return (info[2] == 1 && info[0] == 2) || (info[3] == 1 && info[0] == 1);
    }

     private static boolean fullHouse1to7(int[] info){
        return info[2] == 1 && info[1] == 1;
    }

     private static int[] fullHouseRow2(){
        // Create an array with enough space for numDice.
        int[] values = new int[3];

        // Create an array with enough space for the diceProbabilities.
        int[] probabilities = new int[6];

        fullHouseRow2(0, values, probabilities);

        return probabilities;
    }

     private static void fullHouseRow2(int index, int[] values, int[] probabilities){
        if(index < 3){
            for(int i = 1;i <= 6;i++){
                values[index] = i;
                fullHouseRow2(index + 1, values, probabilities);
            }
        }
        else{

            int[] dist = new int[6];
            for(int i = 0; i < 3;i++){
                dist[values[i] - 1] += 1;
            }

            if(fullHouse2to2(dist)){
                probabilities[0] += 1;
            }

            if(fullHouse2to3(dist)){
                probabilities[1] += 1;
            }

            // fullHouse2to4 is not possible

            if(fullHouse2to5(dist)){
                probabilities[3] += 1;
            }

            if(fullHouse2to6(dist)){
                probabilities[4] += 1;
            }

            if(fullHouse2to7(dist)){
                probabilities[5] += 1;
            }
        }
    }

     private static boolean fullHouse2to2(int[] dist){
        return dist[0] == 0 && dist[1] == 0;
    }

     private static boolean fullHouse2to3(int[] dist){
        return (dist[0] == 1 && dist[1] == 0) || (dist[0] == 0 && dist[1] == 1);
    }

    // fullHouse2to4 is not possible

     private static boolean fullHouse2to5(int[] dist){
        return dist[0] == 1 && dist[1] == 1;
    }

     private static boolean fullHouse2to6(int[] dist){
        return (dist[0] == 2 && dist[1] == 0) || (dist[0] == 0 && dist[1] == 2) || dist[0] == 3 || dist[1] == 3;
    }

     private static boolean fullHouse2to7(int[] dist){
        return (dist[0] == 1 && dist[1] == 2) || ((dist[0] == 2 && dist[1] == 1));
    }

     private static int[] fullHouseRow3(){
        // Create an array with enough space for numDice.
        int[] values = new int[2];

        // Create an array with enough space for the diceProbabilities.
        int[] probabilities = new int[5];

        fullHouseRow3(0, values, probabilities);

        return probabilities;
    }

     private static void fullHouseRow3(int index, int[] values, int[] probabilities){
        if(index < 2){
            for(int i = 1;i <= 6;i++){
                values[index] = i;
                fullHouseRow3(index + 1, values, probabilities);
            }
        }
        else{

            int[] dist = new int[6];
            for (int value : values) {
                dist[value - 1] += 1;
            }

            if(fullHouse3to3(dist)){
                probabilities[0] += 1;
            }

            // fullHouse3to4 is not possible

            if(fullHouse3to5(dist)){
                probabilities[2] += 1;
            }

            if(fullHouse3to6(dist)){
                probabilities[3] += 1;
            }

            if(fullHouse3to7(dist)){
                probabilities[4] += 1;
            }
        }
    }


     private static boolean fullHouse3to3(int[] dist){
        return dist[0] == 0 && dist[1] == 0;
    }

    // fullHouse3to4 is not possible

     private static boolean fullHouse3to5(int[] dist){
         return dist[0] == 0 && dist[1] == 1;
    }

     private static boolean fullHouse3to6(int[] dist){
        return (dist[0] == 1 && dist[1] == 0) || (dist[0] == 2 && dist[1] == 0);
    }

     private static boolean fullHouse3to7(int[] dist){
        return (dist[0] == 1 && dist[1] == 1) || (dist[0] == 0 && dist[1] == 2);
    }

     private static int[] fullHouseRow4(){
        // Create an array with enough space for numDice.
        int[] values = new int[2];

        // Create an array with enough space for the diceProbabilities.
        int[] probabilities = new int[4];

        fullHouseRow4(0, values, probabilities);

        return probabilities;
    }

     private static void fullHouseRow4(int index, int[] values, int[] probabilities){
        if(index < 2){
            for(int i = 1;i <= 6;i++){
                values[index] = i;
                fullHouseRow4(index + 1, values, probabilities);
            }
        }
        else{

            int[] dist = new int[6];
            for (int value : values) {
                dist[value - 1] += 1;
            }

            if(fullHouse4to4(dist)){
                probabilities[0] += 1;
            }

            // fullHouse4to5 is not possible

            if(fullHouse4to6(dist)){
                probabilities[2] += 1;
            }

            if(fullHouse4to7(dist)){
                probabilities[3] += 1;
            }
        }
    }

     private static boolean fullHouse4to4(int[] dist){
        return dist[1] == 0;
    }

    // fullHouse4to5 is not possible

     private static boolean fullHouse4to6(int[] dist){
        return (dist[0] == 0 && dist[1] == 1) || (dist[0] == 1 && dist[1] == 1);
    }

     private static boolean fullHouse4to7(int[] dist){
        return dist[0] == 0 && dist[1] == 2;
    }

     private static int[] fullHouseRow5(){
        // Create an array with enough space for numDice.
        int[] values = new int[1];

        // Create an array with enough space for the diceProbabilities.
        int[] probabilities = new int[3];

        fullHouseRow5(0, values, probabilities);

        return probabilities;
    }

     private static void fullHouseRow5(int index, int[] values, int[] probabilities){
        if(index < 1){
            for(int i = 1;i <= 6;i++){
                values[index] = i;
                fullHouseRow5(index + 1, values, probabilities);
            }
        }
        else{

            int[] dist = new int[6];
            for (int value : values) {
                dist[value - 1] += 1;
            }

            if(fullHouse5to5(dist)){
                probabilities[0] += 1;
            }

            // fullHouse5to6 is not possible

            if(fullHouse5to7(dist)){
                probabilities[2] += 1;
            }
        }
    }

     private static boolean fullHouse5to5(int[] dist){
        return dist[0] == 0 && dist[1] == 0;
    }

    // fullHouse5to6 is not possible

     private static boolean fullHouse5to7(int[] dist){
        return (dist[0] == 1 && dist[1] == 0) || (dist[0] == 0 && dist[1] == 1);
    }

     private static int[] fullHouseRow6(){
        // Create an array with enough space for numDice.
        int[] values = new int[1];

        // Create an array with enough space for the diceProbabilities.
        int[] probabilities = new int[2];

        fullHouseRow6(0, values, probabilities);

        return probabilities;
    }

     private static void fullHouseRow6(int index, int[] values, int[] probabilities){
        if(index < 1){
            for(int i = 1;i <= 6;i++){
                values[index] = i;
                fullHouseRow6(index + 1, values, probabilities);
            }
        }
        else{

            int[] dist = new int[6];
            for (int value : values) {
                dist[value - 1] += 1;
            }

            if(fullHouse6to6(dist)){
                probabilities[0] += 1;
            }

            if(fullHouse6to7(dist)){
                probabilities[1] += 1;
            }
        }
    }

     private static boolean fullHouse6to6(int[] dist){
        return (dist[0] == 0 && dist[1] == 0) || (dist[0] == 1 && dist[1] == 0);
    }

     private static boolean fullHouse6to7(int[] dist){
        return dist[0] == 0 && dist[1] == 1;
    }

    /**
     * This method returns the number of sets with a specific size.
     * @param dist This is an array of integers which contains the number of each dice value.
     * @param setSize This is the desired set size that is being returned.
     * @return Returns the number of sets with a specific size.  ie setSize = 2 are pairs and setSize = 3 are triples
     */
     private static int sets(int[] dist, int setSize) {
        int setCount = 0;
        for (int i = 0; i < 6; i++) {
            if (dist[i] == setSize) {
                setCount += 1;
            }
        }

        return setCount;
    }

    /**
     * This method is used for calculating probabilities of the following rolls:
     *      Aces, Twos, Threes, Fours, Fives, and Sixes
     * @param numDice The number of dice used to calculate the transition matrix.
     * @return Returns an array containing the probabilities for the state changes in the transition matrix.
     */
     private static int[] aces(int numDice){
        // Create an array with enough space for numDice.
        int[] diceValues = new int[numDice];

        // Create an array with enough space for the diceProbabilities.
        int[] diceProbabilities = new int[numDice + 1];

        aces(numDice, 0, diceValues, diceProbabilities);

        return diceProbabilities;
    }

    /**
     *
     * @param numDice The number of dice determine the number of states transitions in the transition matrix.
     * @param index The current index of the dice values which is used for the recursive backtracking.
     * @param diceValues The array containing the values of the dice.
     * @param diceProbabilities The probabilities for the state changes in the transition matrix.
     */
     private static void aces(int numDice, int index, int[] diceValues, int[] diceProbabilities){
        if(index < numDice){
            for(int i = 1;i <= 6;i++){
                diceValues[index] = i;
                aces(numDice, index + 1, diceValues, diceProbabilities);
            }
        }
        else{
            // Count the number of occurrences of the value 1.
            int oneCount = 0;
            for(int i = 0;i < numDice;i++){
                if(diceValues[i] == 1){
                    oneCount += 1;
                }
            }

            // Update the diceProbabilities as necessary.
            diceProbabilities[oneCount] += 1;
        }
    }
}
