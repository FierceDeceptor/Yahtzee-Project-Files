package com.company.test;

import com.company.DiceSet;
import com.company.Stat;
import com.company.roll.Roll5s;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/5/2016.
 */
public class Roll5sTest {
    @Test
    public void getScore() throws Exception {
        Roll5s roll = new Roll5s();

        // Score for roll containing one die with value of 5.
        assertEquals("Roll5s: unexpected score.", 5, roll.getScore(new DiceSet(new int[]{1,2,3,4,5})));

        // Score for roll containing two die with value of 5.
        assertEquals("Roll5s: unexpected score.", 10, roll.getScore(new DiceSet(new int[]{1,2,3,5,5})));

        // Score for roll containing zero die with value of 5.
        assertEquals("Roll5s: unexpected score.", 0, roll.getScore(new DiceSet(new int[]{2,2,3,4,4})));

    }

    @Test
    public void getProbability() throws Exception {
        Roll5s roll = new Roll5s();

        // Probability for 0 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(0,1),
                roll.getAverageProbability(new DiceSet(new int[]{1,2,3,4,4}), 2));

        // Probability for 1 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,6),
                roll.getAverageProbability(new DiceSet(new int[]{1,2,3,4}), 2));

        // Probability for 2 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(11,36),
                roll.getAverageProbability(new DiceSet(new int[]{1,2,3}), 2));

        // Probability for 3 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(91,216),
                roll.getAverageProbability(new DiceSet(new int[]{1,2}), 2));

        // Probability for 4 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(671,1296),
                roll.getAverageProbability(new DiceSet(new int[]{1}), 2));

        // Probability for 5 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(4651,7776),
                roll.getAverageProbability(new DiceSet(new int[]{}), 3));

        // Probability if the dice set already contains a 5.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,1),
                roll.getAverageProbability(new DiceSet(new int[]{1,2,3,4,5}), 2));

    }

}