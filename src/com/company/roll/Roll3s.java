package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class Roll3s implements RollDice {
    /**
     * The score is calculated by determining how many threes are in the roll and multiplying by 3.
     * @param dice The dice in the roll.
     * @return The score of the dice if using the 3s.
     */
    @Override
    public int getScore(DiceSet dice) {
        return dice.getDie(3) * 3;
    }

    /**
     * The probability of rolling a one is calculated by determining what the probability of rolling a 3 with the
     * remaining dice is.
     * The function for calculating this probability is:
     *                  f(n)= 1 - (1 - 1/6)^n.
     * where n is the number of dice left to roll.
     * @param dice The dice that have already been rolled and can not be changed.
     * @return The probability of rolling a 3 for the remaining dice.
     */
    @Override
    public Ratio getProbability(DiceSet dice, int rollsLeft) {
        // The probability of rolling a 1 is 100% if the one is already in the dice roll.
        if(dice.getDie(3) > 0){
            return Stat.toValue(1,1);
        }

        // Calculate the number of dice remaining to roll.
        int n = 5 - dice.numberOfDice();

        // Calculate the probability to roll a one on the next roll.
        return (Ratio)(Stat.toValue(1,1).subtract(Stat.toValue(5,6).power(Stat.toValue(n))));
    }
}
