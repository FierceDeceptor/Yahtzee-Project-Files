package com.company.roll;

import com.company.DiceSet;
import com.company.Matrix;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class RollYahtzee implements RollDice {

    @Override
    public int getScore(DiceSet dice) {
        return 0;
    }

    /**
     *
     * @param dice The dice set that is being used to calculate the probability.
     * @param rollsLeft
     * @return
     */
    @Override
    public Ratio getProbability(DiceSet dice, int rollsLeft) {

        // Find die value with the most dice.
        int largestCount = 0;
        int largestValue = 1;
        for(int i = 1;i <= 6;i++){
            if(dice.getDie(i) > largestCount){
                largestCount = dice.getDie(i);
                largestValue = i;
            }
        }

        // Use this die value to calculate the probability of rolling an Yahtzee using the specified die value.
        return getProbability(dice, rollsLeft, largestValue);
    }

    /**
     *
     * @param dice
     * @param rollsLeft
     * @param dieValue
     * @return
     */
    private Ratio getProbability(DiceSet dice, int rollsLeft, int dieValue){
        // The transition matrix builder.
        Matrix.Builder tBuilder = new Matrix.Builder(5, 5);

        // Set the first row of the transition matrix.
        tBuilder.set(Stat.toValue(120,1296), 0, 0);
        tBuilder.set(Stat.toValue(900,1296), 0, 1);
        tBuilder.set(Stat.toValue(250,1296), 0, 2);
        tBuilder.set(Stat.toValue(25,1296), 0, 3);
        tBuilder.set(Stat.toValue(1,1296), 0, 4);

        // Set the second row of the transition matrix.
        tBuilder.set(Stat.toValue(120,216), 1, 1);
        tBuilder.set(Stat.toValue(80,216), 1, 2);
        tBuilder.set(Stat.toValue(15,216), 1, 3);
        tBuilder.set(Stat.toValue(1,216), 1, 4);

        // Set the third row of the transition matrix.
        tBuilder.set(Stat.toValue(25,36), 2, 2);
        tBuilder.set(Stat.toValue(10,36), 2, 3);
        tBuilder.set(Stat.toValue(1,36), 2, 4);

        // Set the fourth row of the transition matrix.
        tBuilder.set(Stat.toValue(5,6), 3, 3);
        tBuilder.set(Stat.toValue(1,6), 3, 4);

        // Set the fifth row of the transition matrix.
        tBuilder.set(Stat.toValue(1,1), 4, 4);

        // The probability matrix builder.
        Matrix.Builder pBuilder = new Matrix.Builder(5, 1);
        // The last row is a 1.
        pBuilder.set(Stat.toValue(1,1), 4, 0);

        Matrix transition = tBuilder.Build();
        Matrix probability = pBuilder.Build();

        // Multiply the transition matrix by the probability matrix rollsLeft times.
        for(int i = 0; i < rollsLeft;i++ ){
            probability = transition.multiply(probability);
        }

        int numCorrect = dice.getDie(dieValue);
        int probabilityIndex = 0;
        if(numCorrect >= 1){
            probabilityIndex = numCorrect - 1;
        }

        // The first row of the probability vector contains the probability for a Yahtzee.
        return (Ratio)probability.get(probabilityIndex, 0);
    }
}
