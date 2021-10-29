package br.axdber.rpggame;


import br.axdber.rpggame.enums.ArmorType;
import br.axdber.rpggame.enums.CombatClassType;
import br.axdber.rpggame.enums.WeaponType;
import br.axdber.rpggame.interfaces.Action;
import lombok.Getter;

import java.util.ArrayList;


public class Barbarian extends CombatClass {

    @Getter
    private static final int INIT_ATP = 5;
    @Getter
    private static final int INIT_DFP = 2;
    @Getter
    private static final int INIT_MAX_HEALTH = 90;
    @Getter
    private static final double ATP_INCREASE_RATE = 1.2;
    @Getter
    private static final double DFP_INCREASE_RATE = 1.1;
    @Getter
    private static final double HEALTH_INCREASE_RATE = 1.2;


    public Barbarian() {
        super(
                new Stats(
                        INIT_MAX_HEALTH,
                        INIT_ATP,
                        INIT_DFP
                ),
                new Rates(ATP_INCREASE_RATE,
                        DFP_INCREASE_RATE,
                        HEALTH_INCREASE_RATE),
                CombatClassType.BARBARIAN
        );
        weaponsAllowed.add(WeaponType.AXE);
        weaponsAllowed.add(WeaponType.SWORD);
        weaponsAllowed.add(WeaponType.HANDS);

        armorsAllowed.add(ArmorType.LEATHER);
        armorsAllowed.add(ArmorType.MAIL);

        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new AttackAction());
        actions.add(new SelfHealAction());
        actions.add(new IncreaseAttackAction());
        actions.add(new BleedingAction());
        actionsAllowedPerLvl.put(0, actions);
        actions = (ArrayList<Action>) actions.clone();
        actions.add(new DoubleAtkAction());
        actionsAllowedPerLvl.put(1, actions);

    }
}
