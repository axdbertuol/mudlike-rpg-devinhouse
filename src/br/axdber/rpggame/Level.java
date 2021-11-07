package br.axdber.rpggame;

import lombok.Data;


import java.util.HashMap;


@Data
public class Level {


    private final double EXP_INCREASE_RATE;
    private final int FIRST_LVL_EXP = 100;
    private final int MAX_LVL = 9;

    private int experience;
    private int value;
    private HashMap<Integer, Integer> neededExpPerLevel;


    public Level(int value, double EXP_INCREASE_RATE) {
        this.EXP_INCREASE_RATE = EXP_INCREASE_RATE;
        this.value = value;
        calculateNeededExpPerLevel();
        this.experience = 0;
    }


    public void calculateNeededExpPerLevel() {
        this.neededExpPerLevel = new HashMap<>();
        this.neededExpPerLevel.put(0, FIRST_LVL_EXP);
        double lastExpValue = FIRST_LVL_EXP;
        for (int i = 1; i <= MAX_LVL; i++) {
            lastExpValue = lastExpValue * EXP_INCREASE_RATE;
            this.neededExpPerLevel.put(i, (int) lastExpValue);
        }
    }

    public boolean up(int newExp, boolean shouldAccumulateExp) {

        if (experience + newExp >= neededExpPerLevel.get(value)) {
            this.value++;
            this.experience = 0;
            return true;
        }
        // didn't level up, then accumulate
        if(shouldAccumulateExp){
            this.experience += experience;
        }

        return false;
    }




}
