package com.company.test;

import com.company.DiceSet;
import com.company.Stat;
import com.company.roll.Roll1s;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/4/2016.
 */
public class Roll1sTest {
    @org.junit.Test
    public void getScore() throws Exception {
        Roll1s roll = new Roll1s();

        // Score for roll containing one die with value of 1.
        assertEquals("Roll1s: unexpected score.", 1, roll.getScore(new DiceSet(new int[]{1,2,3,4,5})));

        // Score for roll containing two die with value of 1.
        assertEquals("Roll1s: unexpected score.", 2, roll.getScore(new DiceSet(new int[]{1,2,3,2,1})));

        // Score for roll containing zero die with value of 1.
        assertEquals("Roll1s: unexpected score.", 0, roll.getScore(new DiceSet(new int[]{2,2,3,4,5})));
    }

    @org.junit.Test
    public void getProbability() throws Exception {
        Roll1s roll = new Roll1s();

        // Probability for 0 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(0,1),
                roll.getAverageProbability(new DiceSet(new int[]{2,2,3,4,5}), 2));

        // Probability for 1 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,6),
                roll.getAverageProbability(new DiceSet(new int[]{2,2,3,4}), 2));

        // Probability for 2 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(11,36),
                roll.getAverageProbability(new DiceSet(new int[]{2,2,3}), 2));

        // Probability for 3 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(91,216),
                roll.getAverageProbability(new DiceSet(new int[]{2,2}), 2));

        // Probability for 4 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(671,1296),
                roll.getAverageProbability(new DiceSet(new int[]{2}), 2));

        // Probability for 5 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(4651,7776),
                roll.getAverageProbability(new DiceSet(new int[]{}), 3));

        // Probability if the dice set already contains a 1.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,1),
                roll.getAverageProbability(new DiceSet(new int[]{1,2,3,4,5}), 2));

    }

}