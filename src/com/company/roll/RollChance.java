package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class RollChance implements RollDice {
    @Override
    public int getScore(DiceSet dice) {
        // Calculate the sum of all the dice.
        int sum = 0;
        for(int i = 1; i <= 6;i++){
            sum += dice.getDie(i) * i;
        }
        return sum;
    }

    @Override
    public Ratio getProbability(DiceSet dice, int rollsLeft) {
        // There is a 100% probability of rolling a chance.
        return Stat.toValue(1,1);
    }
}
