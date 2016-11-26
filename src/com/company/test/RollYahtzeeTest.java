package com.company.test;

import com.company.DiceSet;
import com.company.Stat;
import com.company.roll.RollYahtzee;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/5/2016.
 */
public class RollYahtzeeTest {
    @Test
    public void getScore() throws Exception {

    }

    @Test
    public void getProbability() throws Exception {
        RollYahtzee roll = new RollYahtzee();

        // The probability of a Yahtzee in 1 roll is 347,897/7558272 or  0.0772%
        assertEquals("RollYahtzee: unexpected probability.", Stat.toValue(1,1296),
                roll.getProbability(new DiceSet(), 1));

        // The probability of a Yahtzee in 2 rolls is 221/17496 or 1.2631%
        assertEquals("RollYahtzee: unexpected probability.", Stat.toValue(221,17496),
                roll.getProbability(new DiceSet(), 2));

        // The probability of a Yahtzee in 3 rolls is 347,897/7558272 or 4.6029%
        assertEquals("RollYahtzee: unexpected probability.", Stat.toValue(347897,7558272),
                roll.getProbability(new DiceSet(), 3));

        // The probability of a Yahtzee in 1 rolls after 2 of a kind 1/216 or 0.4630%
        assertEquals("RollYahtzee: unexpected probability.", Stat.toValue(1,216),
                roll.getProbability(new DiceSet(new int[]{1, 1, 2, 3, 4}), 1));

        // The probability of a Yahtzee in 1 rolls after 3 of a kind 1/36 or 2.7778%
        assertEquals("RollYahtzee: unexpected probability.", Stat.toValue(1,36),
                roll.getProbability(new DiceSet(new int[]{1, 1, 1, 2, 2}), 1));

        // The probability of a Yahtzee in 1 rolls after 4 of a kind 1/6 or 16.6667%
        assertEquals("RollYahtzee: unexpected probability.", Stat.toValue(1,6),
                roll.getProbability(new DiceSet(new int[]{1, 1, 1, 1, 2}), 1));
    }

}