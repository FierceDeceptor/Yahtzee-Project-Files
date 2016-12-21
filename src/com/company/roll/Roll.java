package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public interface Roll {
    /**
     * This method is used to score a set of dice using only a particular roll type.
     *
     * @return The score of the dice passed to the method.
     */
    int getScore(DiceSet dice);

    /**
     * This method is used to calculate the probability of a certain roll based on DiceSet that is passed to the method.
     * 1. DiceSet is empty - The probability of the roll in the first try is calculated.
     * 2. DiceSet is partially full -  The probability of the roll is calculated only rolling the missing dice from the
     * set.
     * 3. DiceSet is full. -  The probability calculated is either 0% or 100% depending on if the dice fulfill the
     * specified move.
     *
     * @param dice The dice set that is being used to calculate the probability.
     * @return The probability that the particular move is to be rolled.  This number is 0.0 for 0% probability and 1.0
     * for 100% probability.
     */
    Ratio getAverageProbability(DiceSet dice, int rollsLeft);

    /**
     *
     * @param dice
     * @param rollsLeft
     * @return
     */
    double getAverageScore(DiceSet dice);
}