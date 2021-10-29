package br.axdber.rpggame;

import lombok.Data;
import lombok.Getter;

import java.awt.font.NumericShaper;
import java.util.Random;


public class Dice {

    @Getter
    private int result;
    private final int range;
    private static Random rng = new Random();

    public Dice(int range){
        this.range = range;
    }
    public static Dice D20 () {
        return new Dice(20);
    }
    public static Dice D16 () {
        return new Dice(16);
    }
    public static Dice D6 () {
        return new Dice(6);
    }

    public static Dice D10 () {
        return new Dice(10);
    }

    public void roll(){
        this.result = 1 + rng.nextInt(range);
    }

    public String reportResult(){
        return "Você rolou " + result + " de " + range;
    }
}
