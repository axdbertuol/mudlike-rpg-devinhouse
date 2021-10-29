package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

public class SelfHealAction implements Action {
    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character character = args[0];
        Dice dice = Dice.D10();
        dice.roll();
        character.increaseHealth(dice.getResult());
        System.out.println("Você se curou " + dice.getResult());
    }


    @Override
    public String toString() {
        return "Curar-se";
    }
}
