package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class Roll1s implements RollDice {
    /**
     * The score is calculated by determining how many ones are in the roll and multiplying by 1.
     * @param dice The dice in the roll.
     * @return The score resulting from the roll of 1s.
     */
    @Override
    public int getScore(DiceSet dice) {
        return dice.getDie(1) * 1;
    }

    /**
     * The probability of rolling a one is calculated by determining what the probability of rolling a 1 with the
     * remaining dice is.
     * The function for calculating this probability is:
     *                  f(n)= 1 - (1 - 1/6)^n.
     * where n is the number of dice left to roll.
     * @param dice The dice that have already been rolled and can not be changed.
     * @return The probability of rolling a 1 for the remaining dice.
     */
    @Override
    public Ratio getProbability(DiceSet dice) {

        // The probability of rolling a 1 is 100% if the one is already in the previous dice.
        if(dice.getDie(1) > 0){
            return Stat.toValue(1,1);
        }

        // Calculate the number of dice remaining to roll.
        int n = 5 - dice.numberOfDice();

        // Calculate the probability to roll a one on the next roll.
        return (Ratio)(Stat.toValue(1,1).subtract(Stat.toValue(5,6).power(n)));
    }
}
