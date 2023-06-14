package br.axdber.rpggame;

import br.axdber.rpggame.enums.StatusType;
import br.axdber.rpggame.interfaces.Action;

public class FreezeAction implements Action {


    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character inflicter = args[0];
        Character inflicted = args[1];
        Dice dice = Dice.D6();
        dice.roll();
        dice.reportResult();
        inflicted.decreaseHealth(dice.getResult());
        inflicter.reportAttack(dice.getResult(), inflicted.name);
        inflicted.setDebuffStatus(StatusType.FROZEN);
        System.out.println(inflicter.reportSpecialAttack(dice.getResult(), inflicted.name, StatusType.FROZEN));

    }

    @Override
    public String toString() {
        return "Congelar";
    }
}
