package br.axdber.rpggame;

import br.axdber.rpggame.enums.WeaponType;
import lombok.Getter;

public class Weapon extends Equipment {

    @Getter
    private final WeaponType type;

    public Weapon(WeaponType type){
        this.attackPoints = type.atp;
        this.defencePoints = type.dfp;
        this.maxHealth = type.maxHealth;
        this.type = type;
    }
    public Weapon(int attackPoints, WeaponType type) {
        this.attackPoints = attackPoints;
        this.defencePoints = 0;
        this.maxHealth = type.maxHealth;
        this.type = type;
    }

    public Weapon(int attackPoints, int defencePoints, WeaponType type) {
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
        this.maxHealth = type.maxHealth;
        this.type = type;
    }

    public String attackString(){
        String wordGender = type.wordGender == 0 ? "sua" : "seu";
        wordGender = type.plural ? wordGender + "s" : wordGender;
        return "com " + wordGender + " " + type.name + ", " + type.atkString;
    }
    @Override
    public String toString() {
        return type.name;
    }
}
