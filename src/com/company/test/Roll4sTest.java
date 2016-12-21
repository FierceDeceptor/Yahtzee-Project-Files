package com.company.test;

import com.company.DiceSet;
import com.company.Stat;
import com.company.roll.Roll4s;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/5/2016.
 */
public class Roll4sTest {
    @Test
    public void getScore() throws Exception {
        Roll4s roll = new Roll4s();

        // Score for roll containing one die with value of 1.
        assertEquals("Roll4s: unexpected score.", 4, roll.getScore(new DiceSet(new int[]{1,2,3,4,5})));

        // Score for roll containing two die with value of 1.
        assertEquals("Roll4s: unexpected score.", 8, roll.getScore(new DiceSet(new int[]{1,2,3,4,4})));

        // Score for roll containing zero die with value of 1.
        assertEquals("Roll4s: unexpected score.", 0, roll.getScore(new DiceSet(new int[]{2,2,3,3,5})));
    }

    @Test
    public void getProbability() throws Exception {
        Roll4s roll = new Roll4s();

        // Probability for 0 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(0,1),
                roll.getAverageProbability(new DiceSet(new int[]{1,2,3,3,5}), 2));

        // Probability for 1 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,6),
                roll.getAverageProbability(new DiceSet(new int[]{2,2,3,3}), 2));

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

        // Probability if the dice set already contains a 4.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,1),
                roll.getAverageProbability(new DiceSet(new int[]{1,2,3,4,5}), 2));

    }

}