package br.axdber.rpggame;

import lombok.Data;

@Data
public abstract class Character {

    protected final int MAX_HEALTH = 100;

    protected String name;
    protected String sex;
    protected CombatClass combatClass;
    protected Weapon weapon;
    protected Armor armor;
    protected Action battleAction;


    protected int health;
    protected int attackPoints;
    protected int defencePoints;

    public Character(String name, String sex, CombatClass combatClass) {
        this.name = name;
        this.sex = sex;
        this.combatClass = combatClass;
        this.health = MAX_HEALTH;
        this.weapon = combatClass.getWeapon();
        this.armor = combatClass.getArmor();
        this.attackPoints = combatClass.getAttackPoints() + weapon.getAttackPoints();
        this.defencePoints = combatClass.getDefensePoints() + armor.getDefencePoints();

    }

    protected void decreaseHealth(int damage) {
        if (this.health - damage < 0) {
            this.health = 0;
        } else {
            this.health -= damage;
        }
    }
    protected void increaseHealth(int healAmount) {
        if (this.health + healAmount > MAX_HEALTH) {
            this.health = MAX_HEALTH;
        } else {
            this.health += healAmount;
        }
    }


}
