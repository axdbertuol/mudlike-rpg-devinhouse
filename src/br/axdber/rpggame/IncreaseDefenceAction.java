package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

public class IncreaseDefenceAction implements Action {

    private final int DEF_INCREASE_VALUE = 2;


    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character character = args[0];
        boolean didIncrease = character.increaseTempDFP(DEF_INCREASE_VALUE);
        if (!didIncrease) {
            System.out.println(character.name + " não pode mais aumentar a defesa!");
        }
    }

    @Override
    public String toString() {
        return "Concentrar DFP +" + DEF_INCREASE_VALUE;
    }
}
