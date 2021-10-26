package br.axdber.rpggame;

import lombok.Data;

@Data
public class Weapon {

    private int attackPoints;
    private WeaponType type;

    public Weapon(int attackPoints, WeaponType type) {
        this.attackPoints = attackPoints;
        this.type = type;
    }


}
