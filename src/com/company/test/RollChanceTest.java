package com.company.test;

import com.company.DiceSet;
import com.company.Stat;
import com.company.roll.RollChance;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/5/2016.
 */
public class RollChanceTest {
    @Test
    public void getScore() throws Exception {
        RollChance roll = new RollChance();

        // Score for all dice with value of 1.
        assertEquals("RollChance unexpected score.", 5,
                roll.getScore(new DiceSet(new int[]{1, 1, 1, 1, 1})));

        // Score for all dice with value of 6.
        assertEquals("RollChance: unexpected score.", 30,
                roll.getScore(new DiceSet(new int[]{6, 6, 6, 6, 6})));

        // Score for all dice with increasing values from 1 to 5.
        assertEquals("RollChance: unexpected score.", 15,
                roll.getScore(new DiceSet(new int[]{1, 2, 3, 4, 5})));
    }

    @Test
    public void getProbability() throws Exception {
        RollChance roll = new RollChance();

        // Probability for 0 dice remaining to roll.
        assertEquals("RollChance: unexpected probability.", Stat.toValue(1,1),
                roll.getAverageProbability(new DiceSet(), 3));
    }

}