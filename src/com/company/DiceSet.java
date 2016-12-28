package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class DiceSet {
    private Map<Integer, Integer> diceMap;

    public DiceSet(){

        diceMap = new TreeMap<>();

        // Add the dice 1 through 6 to the diceMap with a count of 0.
        for(int i = 1;i <= 6;i++) {
            diceMap.put(i, 0);
        }
    }

    /**
     * This method is used to initialize the DiceSet with an array of integers which contain the face value of a die.
     * The first index of the array stores the first dice and the second index stores the second die and so on.
     * @param dice
     * @exception IllegalArgumentException If dice value outside range of 1 to 6 throw exception.  If there are not six
     * dice in the roll than throw exception.
     */
    public DiceSet(int[] dice)
    {
        diceMap = new TreeMap<>();

        // Check to make sure that there are not more than five dice in the set.
        if(dice.length > 5){
            throw new IllegalArgumentException("There cannot be more than 5 die in a set.");
        }

        // Put die values 1 through 6 into the diceMap.
        for(int i = 1;i <= 6;i++){
            diceMap.put(i, 0);
        }
        // Check to make sure the dice are valid otherwise throw an exception.
        for(int i = 0;i < dice.length;i++){
            if(dice[i] < 1 || dice[i] > 6) {
                throw new IllegalArgumentException("Die value must be between 1 and 6.");
            }
        }

        // Add the count of each die value to the dice map.
        for(int i = 0; i < dice.length;i++){
            diceMap.put(dice[i], diceMap.get(dice[i])+1);
        }
    }

    /**
     * This method is used to query the number of die with a certain face value in the DiceSet.
     * @param value The face value of the die that is being requested.
     * @return The number of die with the specified face value in the DiceSet.
     * @exception IllegalArgumentException This exception is thrown when the requested face value is not in the range of
     * 1 through 6.
     */
    public int getDie(int value){
        if(value < 1 || value > 6){
            throw new IllegalArgumentException("Die value must be between 1 and 6.");
        }

        // Return the number of dice with a particular face value.
        return diceMap.get(value);
    }

    /**
     * This method is used so set the number of die with a certain face value in the DiceSet.
     * @param value The face value of the dice that is being set.
     * @param number The number of dice that have a certain face value.
     * @exception IllegalArgumentException This exception is thrown whit requested face value is not in the range of
     * 1 through 6
     */
    public void setDie(int value, int number){
        if(value < 1 || value > 6){
            throw new IllegalArgumentException("Die value must be between 1 and 6.");
        }

        // Set the number of dice with a particular face value.
        diceMap.put(value, number);
    }

    /**
     * This method gets the number of sets of dice with a specific size.
     * For example passing size = 2 would return the number of pairs in the dice roll.
     * and passing size = 3 would return the number of triples and so on.
     * @param size The size of the set.
     * @return The number of sets with a specific size.
     */
    public int sets(int size){
        int count = 0;
        for(int value : diceMap.values()){
            if(value == size){
                count++;
            }
        }

        return count;
    }

    /**
     * This method is used to query the number of dice that are in the set.
     * @return The number of dice in the set.
     */
    public int size(){
        int sum = 0;
        for(int number : diceMap.values()){
            sum += number;
        }
        return sum;
    }

    /**
     * This method is used to add die values to the current set of dice from a list of integers.
     * @param list The list of die values that are being added to the dice set.
     */
    public void fromList(int[] list){

    }

    /**
     * This method is used to add one die value to the current set of dice.
     * @param value The die value that are being added to the dice set.
     */
    public void addDie(int value){
        // Add one to the number of dice with the die the desired value.
        diceMap.put(value, diceMap.get(value) + 1);
    }

    /**
     * This method is used to clear all the dice values.  All the dice counts are set zero.
     */
    public void clear(){
        for(int key : diceMap.keySet()){
            diceMap.put(key, 0);
        }
    }

    public String toString(){
        // Make an array list from the dice set.
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 1;i <= 6;i++){
            // Get the number of die with the specified values.
            int number = diceMap.get(i);

            // Add the dice to the array list.
            for(int j = 0;j < number; j++){
                list.add(i);
            }
        }

        Iterator itr = list.iterator();
        String str = "";

        if(itr.hasNext()){
            str = itr.next().toString();
        }

        while(itr.hasNext()){
            str = str + " " + itr.next().toString();
        }

        return str;
    }
}
