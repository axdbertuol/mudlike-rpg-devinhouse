package br.axdber.rpggame;

import br.axdber.rpggame.enums.SexType;
import br.axdber.rpggame.enums.StatusType;
import br.axdber.rpggame.enums.WeaponType;
import br.axdber.rpggame.interfaces.Action;
import lombok.Data;


import java.util.HashMap;

@Data
public abstract class Character {

    private final double EXP_INCREASE_RATE = 1.5;
    private final int MAX_TEMP_STAT_INCREASE = 6;

    protected String name;
    protected SexType sex;
    protected CombatClass combatClass;

    protected Weapon weapon;
    protected Armor armor;
    protected Action battleAction;
    protected StatusType statusToInflict;
    protected Level level;

    protected Stats stats;
    protected Stats tempStats;
    protected HashMap<StatusType, Integer> dmgStatusByTurnsLeft;
    protected HashMap<StatusType, Integer> debuffStatusByTurnsLeft;


    public Character(String name,
                     SexType sex,
                     CombatClass combatClass,
                     Weapon weapon,
                     Armor armor,
                     int lvl) {
        this.name = name;
        this.sex = sex;
        this.combatClass = combatClass;
        this.weapon = weapon;
        this.armor = armor;
        this.level = new Level(0, EXP_INCREASE_RATE);
        this.dmgStatusByTurnsLeft = new HashMap<>();
        this.debuffStatusByTurnsLeft = new HashMap<>();

        // set initial stats;
        int attackPoints = combatClass.getInitAttackPoints() + weapon.getAttackPoints() + armor.getAttackPoints();
        int defencePoints = combatClass.getInitDefencePoints() + armor.getDefencePoints() + weapon.getDefencePoints();
        stats = new Stats(combatClass.getInitMaxHealth(), attackPoints, defencePoints);
        tempStats = new Stats(0, 0, 0);

        // level up stats for every level if lvl > 1
        for (int i = 0; i < lvl; i++) {
            this.levelUp(level.getNeededExpPerLevel().get(i), false, false);
        }



    }

    protected void decreaseHealth(int damage) {
        this.stats.decreaseHealth(damage);
    }

    protected void unequipWeapon() {
        this.stats.calculateAttackPoints(-this.weapon.getAttackPoints());
        this.stats.calculateDefencePoints(-this.weapon.getDefencePoints());
        this.weapon = new Weapon(1, WeaponType.HANDS);
        this.stats.calculateAttackPoints(+weapon.getAttackPoints());

    }

    protected void equipWeapon(Weapon wp) {
        unequipWeapon();
        this.weapon = wp;
        this.stats.calculateAttackPoints(+wp.getAttackPoints());
        this.stats.calculateDefencePoints(+wp.getDefencePoints());

    }

    protected void equipArmor(Armor am) {
        this.stats.calculateDefencePoints(-this.armor.getDefencePoints());
        this.stats.calculateAttackPoints(-this.armor.getAttackPoints());
        this.armor = am;
        this.stats.calculateDefencePoints(+am.getDefencePoints());
        this.stats.calculateAttackPoints(+am.getAttackPoints());

    }


    protected void increaseHealth(int healAmount) {
        this.stats.increaseHealth(healAmount);
    }

    protected void setHealthToMax() {
        this.stats.setHealthToMax();
    }


    protected void levelUp(int xp, boolean shouldAccumulateExp, boolean shouldReport) {
        boolean didLevelUp = level.up(xp, shouldAccumulateExp);
        if (didLevelUp) {
            int mH = (int) (Math.ceil( this.stats.getMaxHealth() * this.combatClass.getHealthIncreaseRate()));
            int atp = (int) (Math.ceil(this.stats.getAttackPoints() * this.combatClass.getAtpIncreaseRate()));
            int dfp = (int) (Math.ceil(this.stats.getDefencePoints() * this.combatClass.getDfpIncreaseRate()));

            if(shouldReport){

                System.out.println(this.name + " has leveled up! He is now level " + level.getValue() + ".");
                System.out.println("Change of stats:");
                System.out.println("ATP = " + this.stats.getAttackPoints() + " -> " + atp);
                System.out.println("DFP = " + this.stats.getDefencePoints() + " -> " + dfp);
                System.out.println("MaxHealth = " + this.stats.getMaxHealth() + " -> " + mH);
            }

            setHealthToMax();
            this.stats.setMaxHealth(mH);
            this.stats.setAttackPoints(atp);
            this.stats.setDefencePoints(dfp);

        } else {
            System.out.println(this.name + " has gained " + xp + " experience.");
        }
    }

    public boolean increaseTempATP(int atp) {
        if (tempStats.getAttackPoints() <= MAX_TEMP_STAT_INCREASE) {
            this.tempStats.calculateAttackPoints(atp);
            return true;
        }
        return false;
    }

    public boolean increaseTempDFP(int dfp) {
        if (tempStats.getDefencePoints() <= MAX_TEMP_STAT_INCREASE) {
            this.tempStats.calculateDefencePoints(dfp);
            return true;
        }
        return false;
    }


    public void resetTempStats() {
        this.tempStats = new Stats(0, 0, 0);
    }

    public void setDamageStatus(StatusType status) {
        dmgStatusByTurnsLeft.put(status, status.turns);
    }

    public void setDebuffStatus(StatusType status) {
        debuffStatusByTurnsLeft.put(status, status.turns);
    }

    public void dmgStatusTick() {
        if (dmgStatusByTurnsLeft.isEmpty()) {
            return;
        }
        HashMap<StatusType, Integer> toUpdate = (HashMap<StatusType, Integer>) dmgStatusByTurnsLeft.clone();
        for (StatusType st : dmgStatusByTurnsLeft.keySet()) {
            int turnsLeft = dmgStatusByTurnsLeft.get(st) - 1;
            if (turnsLeft == 0) {
                toUpdate.remove(st);
                System.out.println(this.name + " não está mais " + st.name);
                continue;
            }
            int lostHealth = st.dmg + (st.change * (st.turns - turnsLeft));
            this.stats.decreaseHealth(lostHealth);
            System.out.println(this.name + " está " + st.name + " e perdeu " + lostHealth + " de vida");
            toUpdate.put(st, turnsLeft);
        }
        if(toUpdate.values().isEmpty()){
            dmgStatusByTurnsLeft = new HashMap<>();
        }else{
            dmgStatusByTurnsLeft.putAll(toUpdate);
        }


    }

    public void debuffStatusTick() {
        if (debuffStatusByTurnsLeft.isEmpty()) {
            return;
        }
        HashMap<StatusType, Integer> toUpdate = (HashMap<StatusType, Integer>) debuffStatusByTurnsLeft.clone();
        for (StatusType st : debuffStatusByTurnsLeft.keySet()) {
            int turnsLeft = debuffStatusByTurnsLeft.get(st) - 1;
            int dfpLost = st.dmg + st.change;
            if (turnsLeft == 0) {
                toUpdate.remove(st);
                System.out.println(this.name + " não está mais " + st.name);
                continue;
            }
            this.tempStats.calculateDefencePoints(dfpLost);
            System.out.println(this.name + " está " + st.name + " e perdeu " + dfpLost + " de DFP");

            toUpdate.put(st, turnsLeft);
        }
        if(toUpdate.values().isEmpty()){
            debuffStatusByTurnsLeft = new HashMap<>();
        }else{
            debuffStatusByTurnsLeft.putAll(toUpdate);
        }

    }



    public void resetAllStatus() {
        this.dmgStatusByTurnsLeft = new HashMap<>();
        this.debuffStatusByTurnsLeft = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Character{" +
                "EXP_INCREASE_RATE=" + EXP_INCREASE_RATE +
                ", MAX_TEMP_STAT_INCREASE=" + MAX_TEMP_STAT_INCREASE +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", combatClass=" + combatClass +
                ", weapon=" + weapon +
                ", armor=" + armor +
                ", battleAction=" + battleAction +
                ", statusToInflict=" + statusToInflict +
                ", level=" + level +
                ", stats=" + stats +
                ", tempStats=" + tempStats +
                ", dmgStatusByTurnsLeft=" + dmgStatusByTurnsLeft +
                ", debuffStatusByTurnsLeft=" + debuffStatusByTurnsLeft +
                '}';
    }

    // REPORTS

    protected String reportHealth() {
        return this.name + "-> vida está em " + this.stats.getHealth();
    }

    protected String reportMaxHealth() {
        return this.name + " -> vida máxima é " + this.stats.getMaxHealth();
    }

    protected String reportAtp() {
       return this.name + "-> pontos de ataque é " + this.stats.getAttackPoints();
    }

    protected String reportDfp() {
        return this.name + "-> pontos de defesa é " + this.stats.getDefencePoints();
    }

    protected String reportAction() {
        return this.name + " usou a ação de " + battleAction.toString();
    }

    protected String reportAttack(int dmg, String target) {
        return this.name + " atacou " + weapon.attackString() + " e causou " + dmg + " de dano em " + target;
    }

    protected String reportSpecialAttack(int dmg, String target, StatusType type){
       return this.reportAttack(dmg, target) + " deixando-o " + type.name + "!";
    }

    protected String reportCriticalAttack(){
        return "Você acertou um ataque crítico!";
    }
    protected String reportMissedAttack(){
        return "Você errou seu ataque! O inimigo não sofreu dano.";
    }


}
