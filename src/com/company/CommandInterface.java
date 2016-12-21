package com.company;

import com.company.roll.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jonathan Hamberg on 12/18/2016.
 */
public class CommandInterface {
    Scanner input;
    PrintStream output;
    ArrayList<Roll> pastRolls;
    DiceSet currentDiceSet;
    int currentRoll;
    ArrayList<Roll> availableRolls;
    ArrayList<Roll> usedRolls;

    /**
     *
     * @param input
     * @param output
     */
    CommandInterface(Scanner input, PrintStream output){
        this.input = input;
        this.output = output;
        pastRolls = new ArrayList<>();
        currentDiceSet = new DiceSet();
        currentRoll = 1;

        availableRolls = new ArrayList<>();
        availableRolls.add(new Roll1s());
        availableRolls.add(new Roll2s());
        availableRolls.add(new Roll3s());
        availableRolls.add(new Roll4s());
        availableRolls.add(new Roll5s());
        availableRolls.add(new Roll6s());
        availableRolls.add(new Roll3Kind());
        availableRolls.add(new Roll4Kind());
        availableRolls.add(new RollChance());
        availableRolls.add(new RollSmallStraight());
        availableRolls.add(new RollLargeStraight());
        availableRolls.add(new RollFullHouse());
        availableRolls.add(new RollYahtzee());
    }

    /**
     *
     */
    void playGame(){
        String inputStr;
        for(;;){
            // Print the menu to the user.
            output.println("1. Enter Dice\n" +
                    "2. Show Dice\n" +
                    "3. Show Probabilities\n" +
                    "4. Show Score\n" +
                    "5. Next Roll\n" +
                    "6. End Game.");

            // Get the input from the user.
            inputStr = input.nextLine();

            if(inputStr.compareTo("1") == 0){
                enterDice();
            } else if(inputStr.compareTo("2") == 0) {
                showDice();
            } else if(inputStr.compareTo("3") == 0){
                showProbability();
            } else if(inputStr.compareTo("4") == 0){
                showScore();
            } else if(inputStr.compareTo("5") == 0){
                nextRoll();
            } else if(inputStr.compareTo("6") == 0){
                // End the game by breaking the forever loop.
                break;
            } else{
                output.println("Unrecognized choice.  Please choose item listed below.");
            }
        }
    }

    /**
     *
     */
    void enterDice(){
        // Prompt the user for the dice.
        output.print("List of die: ");

        // The contents of the current dice set is cleared before more dice are entered.
        currentDiceSet.clear();

        // Read the input from the user.
        String inputStr = input.nextLine();

        String []items = inputStr.split(" ");

        // Validate the input to make sure there are not more than 5 items in the list.
        if(items.length > 5){
            output.println("Error: There must not be more than 5 dice in a roll.");
        }

        for(String str : items){
            try{
                int value = Integer.valueOf(str);

                // Validate die to be in correct range of values.
                if(value < 1 || value > 6){
                    output.println("Error: Die value must be between 1 and 6.");
                }

                // Add the dice to the current dice set.
                currentDiceSet.addDie(value);
            }
            catch(NumberFormatException e){ // Validate the input to make sure it is in a number format.
                output.println("Error: Die value must be formatted an a number.");
            }
        }
    }

    /**
     *
     */
    void showDice(){
        output.println(currentDiceSet.toString());
    }

    /**
     *
     */
    void showProbability(){
        for(Roll roll : availableRolls){
            output.println(roll.toString() + " : " +
                    roll.getAverageScore(currentDiceSet) + " : " +
                    Stat.round(100 * roll.getAverageProbability(currentDiceSet, 3).toDouble(), 2) + "%");
        }
    }

    /**
     *
     */
    void showScore(){

    }

    /**
     *
     */
    void nextRoll(){

    }

}
