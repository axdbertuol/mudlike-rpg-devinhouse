package br.axdber.rpggame;

public class TrapAction implements Action {
    private final int TRAP_DMG = 10;


    @Override
    public <T extends Character> void act(T... args) {
        Character character = args[0];
        Dice dice = Dice.D10();
        dice.roll();
        // critical miss
        if (dice.getResult() == 1) {
            character.decreaseHealth(TRAP_DMG);
        } else if (dice.getResult() == 10) {
            // critical save
            return;
        }
        character.decreaseHealth(TRAP_DMG - dice.getResult());

    }
}
