package br.axdber.rpggame;

import br.axdber.rpggame.enums.ArmorType;
import br.axdber.rpggame.enums.CombatClassType;
import br.axdber.rpggame.enums.WeaponType;
import br.axdber.rpggame.interfaces.Action;
import lombok.Getter;

import java.util.ArrayList;

public class Archer extends CombatClass {
    @Getter
    private static final int INIT_ATP = 3;
    @Getter
    private static final int INIT_DFP = 3;
    @Getter
    private static final int INIT_MAX_HEALTH = 90;
    @Getter
    private static final double ATP_INCREASE_RATE = 1.2;
    @Getter
    private static final double DFP_INCREASE_RATE = 1.15;
    @Getter
    private static final double HEALTH_INCREASE_RATE = 1.1;

    public Archer() {
        super(
                new Stats(
                        INIT_MAX_HEALTH,
                        INIT_ATP,
                        INIT_DFP
                ),
                new Rates(ATP_INCREASE_RATE,
                        DFP_INCREASE_RATE,
                        HEALTH_INCREASE_RATE),
                CombatClassType.ARCHER
        );
        weaponsAllowed.add(WeaponType.BOW);
        weaponsAllowed.add(WeaponType.CROSSBOW);
        weaponsAllowed.add(WeaponType.HANDS);

        armorsAllowed.add(ArmorType.LEATHER);
        armorsAllowed.add(ArmorType.MAIL);

        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new AttackAction());
        actions.add(new IncreaseAttackAction());
        actions.add(new IncreaseDefenceAction());
        actions.add(new PoisonAction());
        actionsAllowedPerLvl.put(0, actions);
        actions = (ArrayList<Action>) actions.clone();
        actions.add(new DoubleAtkAction());
        actionsAllowedPerLvl.put(1, actions);
    }
}
