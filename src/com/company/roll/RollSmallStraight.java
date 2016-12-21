package com.company.roll;

import com.company.DiceSet;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class RollSmallStraight implements Roll {

    @Override
    public int getScore(DiceSet dice) {
        return 0;
    }

    @Override
    public Ratio getAverageProbability(DiceSet dice, int rollsLeft) {
        return Stat.toValue(1,1);
    }

    @Override
    public double getAverageScore(DiceSet dice) {
        return 0;
    }

    public String toString(){
        return "Small straight";
    }
}
