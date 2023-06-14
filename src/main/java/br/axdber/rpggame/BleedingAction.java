package br.axdber.rpggame;

import br.axdber.rpggame.enums.StatusType;
import br.axdber.rpggame.interfaces.Action;

public class BleedingAction implements Action {


    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character inflicter = args[0];
        Character inflicted = args[1];
        inflicted.setDamageStatus(StatusType.BLEEDING);
        System.out.println(inflicter.reportSpecialAttack(0, inflicted.name, StatusType.BLEEDING));
    }

    @Override
    public String toString() {
        return "Sangrar";
    }
}
