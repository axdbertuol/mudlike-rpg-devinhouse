package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

public class TrapAction implements Action {
    private final int TRAP_DMG = 10;


    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character character = args[0];

        Dice dice = Dice.D10();
        dice.roll();
        dice.reportResult();
        // critical miss
        if (dice.getResult() == 1) {
            character.decreaseHealth(TRAP_DMG);
            return;
        } else if (dice.getResult() == 10) {
            // critical save
            return;
        }
        character.decreaseHealth(TRAP_DMG - dice.getResult());

    }

    @Override
    public String toString() {
        return "TrapAction{" +
                "TRAP_DMG=" + TRAP_DMG +
                '}';
    }
}
