package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class RollChance implements Roll {

    /**
     *
     * @param dice
     * @return
     */
    @Override
    public int getScore(DiceSet dice) {
        // Calculate the sum of all the dice.
        int sum = 0;
        for(int i = 1; i <= 6;i++){
            sum += dice.getDie(i) * i;
        }
        return sum;
    }

    /**
     *
     * @param dice The dice set that is being used to calculate the probability.
     * @param rollsLeft
     * @return
     */
    @Override
    public Ratio getAverageProbability(DiceSet dice, int rollsLeft) {
        // There is a 100% probability of rolling a chance.
        return Stat.toValue(1,1);
    }

    /**
     *
     * @param dice
     * @return
     */
    @Override
    public double getAverageScore(DiceSet dice) {
        return 0;
    }

    /**
     *
     * @return
     */
    public String toString(){
        return "Chance";
    }
}
