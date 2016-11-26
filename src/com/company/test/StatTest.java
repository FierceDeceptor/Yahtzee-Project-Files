package com.company.test;

import com.company.Stat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jonathan Hamberg on 11/26/2016.
 */
public class StatTest {
    @Test
    public void generateCombination() throws Exception {

        ArrayList<int[]> combinations = Stat.generateCombination(new int[]{1,2,3,4,5}, 4);

        assertEquals("unexpected number of groups", 5, combinations.size());
        assertArrayEquals("unexpected 1st combination group", new int[]{1,2,3,4}, combinations.get(0));
        assertArrayEquals("unexpected 2nd combination group", new int[]{1,2,3,5}, combinations.get(1));
        assertArrayEquals("unexpected 3rd combination group", new int[]{1,2,4,5}, combinations.get(2));
        assertArrayEquals("unexpected 4th combination group", new int[]{1,3,4,5}, combinations.get(3));
        assertArrayEquals("unexpected 5th combination group", new int[]{2,3,4,5}, combinations.get(4));
    }

    @Test
    public void generatePermutation() throws Exception {

    }

}