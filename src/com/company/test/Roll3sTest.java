package com.company.test;

import com.company.DiceSet;
import com.company.Stat;
import com.company.roll.Roll3s;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/5/2016.
 */
public class Roll3sTest {
    @Test
    public void getScore() throws Exception {
        Roll3s roll = new Roll3s();

        // Score for roll containing one die with value of 3.
        assertEquals("Roll3s: unexpected score.", 3, roll.getScore(new DiceSet(new int[]{1,2,3,4,5})));

        // Score for roll containing two die with value of 3.
        assertEquals("Roll3s: unexpected score.", 6, roll.getScore(new DiceSet(new int[]{1,3,3,2,1})));

        // Score for roll containing zero die with value of 3.
        assertEquals("Roll3s: unexpected score.", 0, roll.getScore(new DiceSet(new int[]{1,2,2,4,5})));
    }

    @Test
    public void getProbability() throws Exception {
        Roll3s roll = new Roll3s();

        // Probability for 0 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(0,1),
                roll.getProbability(new DiceSet(new int[]{1,2,2,4,5}), 2));

        // Probability for 1 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,6),
                roll.getProbability(new DiceSet(new int[]{1,2,2,4}), 2));

        // Probability for 2 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(11,36),
                roll.getProbability(new DiceSet(new int[]{1,2,2}), 2));

        // Probability for 3 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(91,216),
                roll.getProbability(new DiceSet(new int[]{1,2}), 2));

        // Probability for 4 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(671,1296),
                roll.getProbability(new DiceSet(new int[]{1}), 2));

        // Probability for 5 dice remaining to roll.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(4651,7776),
                roll.getProbability(new DiceSet(new int[]{}), 3));

        // Probability if the dice set already contains a 3.
        assertEquals("Roll1s: unexpected probability.", Stat.toValue(1,1),
                roll.getProbability(new DiceSet(new int[]{1,2,3,4,5}), 2));

    }

}