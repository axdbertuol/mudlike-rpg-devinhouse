package br.axdber.rpggame;

import br.axdber.rpggame.enums.ArmorType;
import br.axdber.rpggame.enums.CombatClassType;
import br.axdber.rpggame.enums.WeaponType;
import br.axdber.rpggame.interfaces.Action;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;


public class CombatClass {

    protected Stats stats;
    protected Rates rates;
    @Getter
    protected final CombatClassType type;
    @Getter
    protected final ArrayList<WeaponType> weaponsAllowed;
    @Getter
    protected final ArrayList<ArmorType> armorsAllowed;
    @Getter
    protected HashMap<Integer, ArrayList<Action>> actionsAllowedPerLvl;

    public CombatClass(Stats stats, Rates rates, CombatClassType type) {
        this.stats = stats;
        this.rates = rates;
        this.type = type;
        this.weaponsAllowed = new ArrayList<>();
        this.armorsAllowed = new ArrayList<>();
        this.actionsAllowedPerLvl = new HashMap<>();
    }

    protected double getAtpIncreaseRate(){
        return rates.getAtpIncreaseRate();
    }

    protected double getDfpIncreaseRate(){
        return rates.getDfpIncreaseRate();
    }

    protected double getHealthIncreaseRate(){
        return rates.getHealthIncreaseRate();
    }
    protected int getInitMaxHealth(){
        return stats.getMaxHealth();
    }
    protected int getInitAttackPoints(){
        return stats.getAttackPoints();
    }
    protected int getInitDefencePoints(){
        return stats.getDefencePoints();
    }

    @Override
    public String toString() {
        return "CombatClass{" +
                "atpIncreaseRate=" + getAtpIncreaseRate() +
                ", dfpIncreaseRate=" + getDfpIncreaseRate() +
                ", healthIncreaseRate=" + getHealthIncreaseRate() +
                ", initMaxHealth=" + getInitMaxHealth() +
                ", initAttackPoints=" + getInitAttackPoints() +
                ", initDefensePoints=" + getInitDefencePoints() +
                ", type=" + type +
                ", weaponsAllowed=" + weaponsAllowed +
                ", armorsAllowed=" + armorsAllowed +
                '}';
    }
}
