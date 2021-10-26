package br.axdber.rpggame;

import lombok.Data;
import lombok.Getter;

import java.util.Random;


public class Dice {

    @Getter
    private int result;
    private int range;
    private static Random rng = new Random();

    public Dice(int range){
        this.range = range;
    }
    public static Dice D20 () {
        return new Dice(20);
    }

    public static Dice D10 () {
        return new Dice(10);
    }

    public void roll(){
        this.result = rng.nextInt(range);
    }
}
