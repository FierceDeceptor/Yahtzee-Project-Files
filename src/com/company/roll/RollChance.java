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
        return 0;
    }

    @Override
    public Ratio getProbability(DiceSet dice, int rollsLeft) {
        return Stat.toValue(1,1);
    }
}
