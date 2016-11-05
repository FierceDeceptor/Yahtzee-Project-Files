package com.company.test;

import com.company.DiceSet;
import com.company.Stat;
import com.company.roll.Roll2s;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/5/2016.
 */
public class Roll2sTest {
    @Test
    public void getScore() throws Exception {
        Roll2s roll = new Roll2s();

        // Score for roll containing one die with value of 2.
        assertEquals("Roll2s: unexpected score.", 2, roll.getScore(new DiceSet(new int[]{1,2,3,4,5})));

        // Score for roll containing two die with value of 2.
        assertEquals("Roll2s: unexpected score.", 4, roll.getScore(new DiceSet(new int[]{1,2,3,2,1})));

        // Score for roll containing zero die with value of 2.
        assertEquals("Roll2s: unexpected score.", 0, roll.getScore(new DiceSet(new int[]{1,1,3,4,5})));

    }

    @Test
    public void getProbability() throws Exception {
        Roll2s roll = new Roll2s();

        // Probability for 0 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(0,1),
                roll.getProbability(new DiceSet(new int[]{1,1,3,4,5}), 2));

        // Probability for 1 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,6),
                roll.getProbability(new DiceSet(new int[]{1,1,3,4}), 2));

        // Probability for 2 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(11,36),
                roll.getProbability(new DiceSet(new int[]{1,1,3}), 2));

        // Probability for 3 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(91,216),
                roll.getProbability(new DiceSet(new int[]{1,1}), 2));

        // Probability for 4 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(671,1296),
                roll.getProbability(new DiceSet(new int[]{1}), 2));

        // Probability for 5 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(4651,7776),
                roll.getProbability(new DiceSet(new int[]{}), 3));

        // Probability if the dice set already contains a 1.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,1),
                roll.getProbability(new DiceSet(new int[]{1,2,3,4,5}), 2));

    }

}