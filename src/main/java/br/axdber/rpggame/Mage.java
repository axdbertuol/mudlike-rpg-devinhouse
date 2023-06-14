package br.axdber.rpggame;

import br.axdber.rpggame.enums.ArmorType;
import br.axdber.rpggame.enums.CombatClassType;
import br.axdber.rpggame.enums.WeaponType;
import br.axdber.rpggame.interfaces.Action;
import lombok.Getter;

import java.util.ArrayList;

public class Mage extends CombatClass {

    @Getter
    private static final int INIT_ATP = 1;
    @Getter
    private static final int INIT_DFP = 3;
    @Getter
    private static final int INIT_MAX_HEALTH = 89;
    @Getter
    private static final double ATP_INCREASE_RATE = 1.1;
    @Getter
    private static final double DFP_INCREASE_RATE = 1.1;
    @Getter
    private static final double HEALTH_INCREASE_RATE = 1.1;

    public Mage() {
        super(new Stats(
                        INIT_MAX_HEALTH,
                        INIT_ATP,
                        INIT_DFP
                ),
                new Rates(ATP_INCREASE_RATE,
                        DFP_INCREASE_RATE,
                        HEALTH_INCREASE_RATE),
                CombatClassType.MAGE);

        weaponsAllowed.add(WeaponType.STAFF);
        weaponsAllowed.add(WeaponType.WAND);
        weaponsAllowed.add(WeaponType.HANDS);

        armorsAllowed.add(ArmorType.PLATE);
        armorsAllowed.add(ArmorType.CLOTH);

        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new AttackAction());
        actions.add(new BurningAction());
        actions.add(new FreezeAction());
        actionsAllowedPerLvl.put(0, actions);
        actions = (ArrayList<Action>) actions.clone();
        actions.add(new IncreaseAttackAction());
        actions.add(new PoisonAction());
        for (int i = 1; i < 10; i++) {
            actionsAllowedPerLvl.put(i, actions);
        }
    }

}
