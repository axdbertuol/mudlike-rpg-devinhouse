package br.axdber.rpggame;

import br.axdber.rpggame.enums.StatusType;
import br.axdber.rpggame.interfaces.Action;

public class PoisonAction implements Action {
    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character inflicter = args[0];
        Character inflicted = args[1];
        inflicted.setDamageStatus(StatusType.POISONED);
        System.out.println(inflicter.reportSpecialAttack(0, inflicted.name, StatusType.POISONED));
    }

    @Override
    public String toString() {
        return "Envenenar";
    }
}
