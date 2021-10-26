package br.axdber.rpggame;

import lombok.Data;

@Data
public class Armor {

    private int defencePoints;
    private ArmorType type;

    public Armor(int defencePoints, ArmorType type) {
        this.defencePoints = defencePoints;
        this.type = type;
    }
}
