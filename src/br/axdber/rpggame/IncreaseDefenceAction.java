package br.axdber.rpggame;

public class IncreaseDefenceAction implements Action{

    private final int DEF_INCREASE_VALUE = 2;


    @Override
    public <T extends Character> void act(T... args) {
        Character character = args[0];
        character.setDefencePoints(character.getDefencePoints() + DEF_INCREASE_VALUE);
    }

    @Override
    public String toString() {
        return "IncreaseDefenceAction{" +
                "DEF_INCREASE_VALUE +" + DEF_INCREASE_VALUE +
                '}';
    }
}
