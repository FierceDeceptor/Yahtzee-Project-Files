package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class Roll1s implements Roll {

    /**
     * The score is calculated by determining how many ones are in the roll and multiplying by 1.
     * @param dice The dice in the roll.
     * @return The score of the dice if using the 1s.
     */
    @Override
    public int getScore(DiceSet dice) {
        return dice.getDie(1) * 1;
    }

    /**
     * The average probability is calculated by taking the average of all the probabilities for each possible scoring
     * roll.
     * @param dice The dice that have already been rolled and can not be changed.
     * @param rollsLeft Indicates the number of rolls left for probability calculations.
     * @return The probability of rolling a 1 for the remaining dice.
     */
    @Override
    public Ratio getAverageProbability(DiceSet dice, int rollsLeft) {
        // Determine the number of scoring dice in the roll.
        int numDice = dice.getDie(1);

        // Determine the remaining number of possible rolls.
        int n = 5 - numDice;

        // Calculate the total probability of all the possible rolls.
        Ratio totalProbability = Stat.toValue(0,1);
        for(int i = numDice + 1;i <= 5;i++){
            totalProbability = (Ratio)totalProbability.add(Stat.probabilityMatchingDice(i - numDice, rollsLeft));
        }

        // Return the average probability dividing the total probability by the number of possible rolls.
        return (Ratio)totalProbability.divide(Stat.toValue(n));
    }

    /**
     *
     * @param dice
     * @return
     */
    @Override
    public double getAverageScore(DiceSet dice) {

        // Determine the number of scoring dice in the roll.
        int numDice = dice.getDie(1);

        // Determine the remaining number of scoring rolls.
        int n = 5 - numDice;

        // Calculate the total score of all the possible rolls.
        int totalScore = 1 * n * (numDice + 1 + 5) / 2;

        // Return the average score by returning the total score by the number of possible scoring rolls.
        return Stat.round((double)totalScore / n, 1);
    }

    public String toString(){
        return "Aces";
    }
}
