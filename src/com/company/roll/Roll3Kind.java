package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class Roll3Kind implements Roll {

    /**
     *
     * @param dice
     * @return
     */
    @Override
    public int getScore(DiceSet dice) {
        return 0;
    }

    /**
     *
     * @param dice The dice set that is being used to calculate the probability.
     * @param rollsLeft
     * @return
     */
    @Override
    public Ratio getAverageProbability(DiceSet dice, int rollsLeft) {
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
        return "3 of a kind";
    }
}
