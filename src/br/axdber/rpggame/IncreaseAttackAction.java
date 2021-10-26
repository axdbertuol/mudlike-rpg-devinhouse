package br.axdber.rpggame;

public class IncreaseAttackAction implements Action{

    private final int ATK_INCREASE_VALUE = 3;


    @Override
    public <T extends Character> void act(T... args) {
        Character character = args[0];
        character.setAttackPoints(character.getAttackPoints() + ATK_INCREASE_VALUE);
    }

    @Override
    public String toString() {
        return "IncreaseAttackAction{" +
                "ATK_INCREASE_VALUE +" + ATK_INCREASE_VALUE +
                '}';
    }
}
