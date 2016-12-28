package com.company.roll;

import com.company.DiceSet;
import com.company.Matrix;
import com.company.Ratio;
import com.company.Stat;

/**
 * Created by Jonathan Hamberg on 11/2/2016.
 */
public class RollFullHouse implements Roll {

    /**
     * This method returns the score of a DiceSet scored as a FullHouse.  If the dice set is a full house it returns 25
     * if the DiceSet does not contain a Full House then 0 is returned.
     * @param dice The set of dice that is being scored.
     * @return The score of the dice set.
     */
    @Override
    public int getScore(DiceSet dice) {
        if(dice.sets(3) == 1 && dice.sets(2) == 1){
            return 25;
        }
        return 0;
    }

    /**
     * This method uses a Markov Chain to calculate the probability of rolling a yahtzee.
     * There are seven states which are listed below.
     * #1 X X X X X - not possible
     * #2 A B X X X - 5 singles
     * #3 A A B X X - 1 pair & 3 singles
     * #4 A A A X X - all A
     * #5 A A B B X - 2 pairs & 1 single
     * #6 A A A B X - 1 triple & 2 singles
     * #7 A A A B B - 1 triple & 1 pair
     * @param dice The dice set that is being used to calculate the probability.
     * @param rollsLeft This affects the probability because the dice can be re-rolled.
     * @return
     */
    @Override
    public Ratio getAverageProbability(DiceSet dice, int rollsLeft) {
        // The transition matrix builder.
        Matrix.Builder tBuilder = new Matrix.Builder(7, 7);

        // Set the first row of the transition matrix.
        tBuilder.set(Stat.toValue(0,7776), 0, 0);
        tBuilder.set(Stat.toValue(720, 7776), 0, 1);
        tBuilder.set(Stat.toValue(3600,7776), 0, 2);
        tBuilder.set(Stat.toValue(6,7776), 0, 3);
        tBuilder.set(Stat.toValue(1800,7776), 0, 4);
        tBuilder.set(Stat.toValue(1350,7776), 0, 5);
        tBuilder.set(Stat.toValue(300,7776), 0, 6);

        // Set the second row of the transition matrix.
        tBuilder.set(Stat.toValue(64,216), 1, 1);
        tBuilder.set(Stat.toValue(96,216), 1, 2);
        tBuilder.set(Stat.toValue(0,216), 1, 3);
        tBuilder.set(Stat.toValue(24,216), 1, 4);
        tBuilder.set(Stat.toValue(26,216), 1, 5);
        tBuilder.set(Stat.toValue(6, 216), 1, 6);

        // Set the third row of the transition matrix.
        tBuilder.set(Stat.toValue(16,36), 2, 2);
        tBuilder.set(Stat.toValue(0,36), 2, 3);
        tBuilder.set(Stat.toValue(8,36), 2, 4);
        tBuilder.set(Stat.toValue(9,36), 2, 5);
        tBuilder.set(Stat.toValue(3,36), 2, 6);


        // Set the fourth row of the transition matrix.
        tBuilder.set(Stat.toValue(25,36), 3, 3);
        tBuilder.set(Stat.toValue(0,36), 3, 4);
        tBuilder.set(Stat.toValue(10,36), 3, 5);
        tBuilder.set(Stat.toValue(1,36), 3, 6);

        // Set the fifth row of the transition matrix.
        tBuilder.set(Stat.toValue(4,6), 4, 4);
        tBuilder.set(Stat.toValue(0,6), 4, 5);
        tBuilder.set(Stat.toValue(2,6), 4, 6);

        // Set the sixth row of the transition matrix.
        tBuilder.set(Stat.toValue(5,6), 5, 5);
        tBuilder.set(Stat.toValue(1,6), 5, 6);

        // Set the seventh row of the transition matrix.
        tBuilder.set(Stat.toValue(1,1), 6,6);


        // The probability matrix builder.
        Matrix.Builder pBuilder = new Matrix.Builder(7, 1);
        // The last row is a 1.
        pBuilder.set(Stat.toValue(1,1), 6, 0);

        Matrix transition = tBuilder.Build();
        Matrix probability = pBuilder.Build();

        // Multiply the transition matrix by the probability matrix rollsLeft times.
        for(int i = 0; i < rollsLeft;i++ ){
            probability = transition.multiply(probability);
        }

        // Determine which state the roll is currently in.
        int stateIndex = 0;

        if(dice.size() == 0){ // State #1.
            stateIndex = 0;
        }
        else if(dice.sets(1) == 5){ // State #2.
            stateIndex = 1;
        }
        else if(dice.sets(2) == 1 && dice.sets(1) == 3){ // State #3
            stateIndex = 2;
        }
        else if(dice.sets(5) == 1){ // State #4
            stateIndex = 3;
        }
        else if(dice.sets(2) == 2 &&  dice.sets(1) == 1){ // State #5
            stateIndex = 4;
        }
        else if(dice.sets(3) == 1 && dice.sets(1) == 2){ // State #6
            stateIndex = 5;
        }
        else if(dice.sets(3) == 1 && dice.sets(2) == 1){ // State #7
            stateIndex = 6;
        }

        // The first row of the probability vector contains the probability for a Yahtzee.
        return (Ratio)probability.get(stateIndex, 0);
    }

    /**
     * This method returns the weighted score based off the probabilities of the roll.
     * The score is always the same so return a constant 25.
     * @param dice The dice set that is being scored.
     * @return The weighted score based off the probability of the roll.
     */
    @Override
    public double getAverageScore(DiceSet dice) {
        return 25;
    }

    /**
     * Returns the text name of the roll.
     * @return The text name of the roll.
     */
    public String toString(){
        return "Full house";
    }
}
