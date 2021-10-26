package br.axdber.rpggame;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CombatClass {
    @Getter
    private int attackPoints;
    @Getter
    private int defensePoints;
    @Getter
    private Weapon weapon;
    @Getter
    private Armor armor;
    @Getter
    private CombatClassType type;

    public CombatClass(int attackPoints, int defensePoints, Weapon weapon, Armor armor, CombatClassType type) {
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.weapon = weapon;
        this.armor = armor;
        this.type = type;
    }
}
