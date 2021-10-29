package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

public class IncreaseAttackAction implements Action {

    private final int ATK_INCREASE_VALUE = 2;


    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character character = args[0];
        boolean didIncrease = character.increaseTempATP(ATK_INCREASE_VALUE);
        if (!didIncrease) {
            System.out.println(character.name + " não pode mais aumentar o ataque!");
        }
    }



    @Override
    public String toString() {
        return "Concentrar ATP +" + ATK_INCREASE_VALUE;
    }
}
