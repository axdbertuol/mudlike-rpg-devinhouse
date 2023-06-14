package br.axdber.rpggame;

import br.axdber.rpggame.enums.SexType;
import br.axdber.rpggame.enums.StatusType;
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

    protected int health;
    protected Stats totalStats;

    protected Stats characterStats;
    protected Stats equipmentStats;
    protected Stats tempStats;
    // dmgStatus -> POISON, BLEED
    protected HashMap<StatusType, Integer> dmgStatusByTurnsLeft;
    // debuffs -> FROZEN, BURNED
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
        int attackPoints = weapon.getAttackPoints() + armor.getAttackPoints();
        int defencePoints = armor.getDefencePoints() + weapon.getDefencePoints();
        int maxHealth = armor.getMaxHealth() + weapon.getMaxHealth();
        equipmentStats = new Stats(maxHealth, attackPoints, defencePoints);
        characterStats = new Stats(combatClass.getInitMaxHealth(), combatClass.getInitAttackPoints(), combatClass.getInitDefencePoints());
        attackPoints += characterStats.getAttackPoints();
        defencePoints += characterStats.getDefencePoints();
        maxHealth += characterStats.getMaxHealth();
        tempStats = new Stats(0, 0, 0);
        totalStats = new Stats(maxHealth, attackPoints, defencePoints);
        this.health = totalStats.getMaxHealth();
        // level up stats for every level if lvl > 1
        for (int i = 0; i < lvl; i++) {
            this.levelUp(level.getNeededExpPerLevel().get(i), false, false);
        }


    }

    protected void decreaseHealth(int damage) {
        this.setHealth(Math.max(this.health - damage, 0));
    }

    protected void unequipWeapon() {
        this.equipmentStats.calculateAttackPoints(-this.weapon.getAttackPoints());
        this.equipmentStats.calculateDefencePoints(-this.weapon.getDefencePoints());
        this.equipmentStats.calculateMaxHealth(-this.weapon.getMaxHealth());
        this.weapon = null;
        updateTotalStats();
    }

    protected void equipWeapon(Weapon wp) {
        unequipWeapon();
        this.weapon = wp;
        this.equipmentStats.calculateAttackPoints(+wp.getAttackPoints());
        this.equipmentStats.calculateDefencePoints(+wp.getDefencePoints());
        this.equipmentStats.calculateMaxHealth(+wp.getMaxHealth());
        updateTotalStats();

    }

    protected void equipArmor(Armor am) {
        this.equipmentStats.calculateDefencePoints(-this.armor.getDefencePoints());
        this.equipmentStats.calculateAttackPoints(-this.armor.getAttackPoints());
        this.equipmentStats.calculateMaxHealth(-this.armor.getMaxHealth());
        this.armor = am;
        this.equipmentStats.calculateDefencePoints(+am.getDefencePoints());
        this.equipmentStats.calculateAttackPoints(+am.getAttackPoints());
        this.equipmentStats.calculateMaxHealth(+am.getMaxHealth());

        updateTotalStats();
    }

    protected void updateTotalStats() {
        this.totalStats.setMaxHealth(getTotalMaxHealth());
        this.totalStats.setAttackPoints(getTotalATP());
        this.totalStats.setDefencePoints(getTotalDFP());
    }


    protected void increaseHealth(int healAmount) {
        this.setHealth(Math.min(this.health + healAmount, this.totalStats.getMaxHealth()));
    }

    protected void setHealthToMax() {
        this.health = this.totalStats.getMaxHealth();
    }

    protected int getTotalATP() {
        return this.characterStats.getAttackPoints() + this.equipmentStats.getAttackPoints() + this.tempStats.getAttackPoints();
    }

    protected int getTotalDFP() {
        return this.characterStats.getDefencePoints() + this.equipmentStats.getDefencePoints() + this.tempStats.getDefencePoints();
    }

    protected int getTotalMaxHealth() {
        return this.characterStats.getMaxHealth() + this.equipmentStats.getMaxHealth() + this.tempStats.getMaxHealth();

    }

    protected void levelUp(int xp, boolean shouldAccumulateExp, boolean shouldReport) {
        boolean didLevelUp = level.up(xp, shouldAccumulateExp);
        if (didLevelUp) {
            int mH = (int) (Math.ceil(this.characterStats.getMaxHealth() * this.combatClass.getHealthIncreaseRate()));
            int atp = (int) (Math.ceil(this.characterStats.getAttackPoints() * this.combatClass.getAtpIncreaseRate()));
            int dfp = (int) (Math.ceil(this.characterStats.getDefencePoints() * this.combatClass.getDfpIncreaseRate()));

            if (shouldReport) {
                System.out.println(this.name + " has leveled up! He is now level " + level.getValue() + ".");
                System.out.println("Change of stats:");
                System.out.println("ATP = " + this.characterStats.getAttackPoints() + " -> " + atp);
                System.out.println("DFP = " + this.characterStats.getDefencePoints() + " -> " + dfp);
                System.out.println("MaxHealth = " + this.characterStats.getMaxHealth() + " -> " + mH);
            }

            this.characterStats.setMaxHealth(mH);
            this.characterStats.setAttackPoints(atp);
            this.characterStats.setDefencePoints(dfp);

            updateTotalStats();
            setHealthToMax();

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
            this.decreaseHealth(lostHealth);
            System.out.println(this.name + " está " + st.name + " e perdeu " + lostHealth + " de vida");
            toUpdate.put(st, turnsLeft);
        }
        if (toUpdate.values().isEmpty()) {
            dmgStatusByTurnsLeft = new HashMap<>();
        } else {
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
        if (toUpdate.values().isEmpty()) {
            debuffStatusByTurnsLeft = new HashMap<>();
        } else {
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
                ", stats=" + characterStats +
                ", tempStats=" + tempStats +
                ", dmgStatusByTurnsLeft=" + dmgStatusByTurnsLeft +
                ", debuffStatusByTurnsLeft=" + debuffStatusByTurnsLeft +
                '}';
    }

    // REPORTS

    protected String reportHealth() {
        return this.name + "-> vida está em " + this.getHealth();
    }

    protected String reportMaxHealth() {
        return this.name + " -> vida máxima é " + this.totalStats.getMaxHealth();
    }

    protected String reportAtp() {
        return this.name + "-> pontos de ataque é " + this.totalStats.getAttackPoints();
    }

    protected String reportDfp() {
        return this.name + "-> pontos de defesa é " + this.totalStats.getDefencePoints();
    }

    protected String reportAction() {
        return this.name + " usou a ação de " + battleAction.toString();
    }

    protected String reportAttack(int dmg, String target) {
        return this.name + " atacou " + weapon.attackString() + " e causou " + dmg + " de dano em " + target;
    }

    protected String reportSpecialAttack(int dmg, String target, StatusType type) {
        return this.reportAttack(dmg, target) + " deixando-o " + type.name + "!";
    }

    protected String reportCriticalAttack() {
        return "Você acertou um ataque crítico!";
    }

    protected String reportMissedAttack() {
        return "Você errou seu ataque! O inimigo não sofreu dano.";
    }


}
