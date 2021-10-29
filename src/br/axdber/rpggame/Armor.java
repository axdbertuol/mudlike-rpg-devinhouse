package br.axdber.rpggame;

import br.axdber.rpggame.enums.ArmorType;
import lombok.Data;
import lombok.Getter;

public class Armor extends Equipment {

    @Getter
    private final ArmorType type;

    public Armor(int defencePoints, ArmorType type) {
        this.defencePoints = defencePoints;
        this.attackPoints = 0;
        this.type = type;
    }
    public Armor(int defencePoints, int attackPoints, ArmorType type) {
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.name+ "{" +
                "attackPoints=" + attackPoints +
                ", defencePoints=" + defencePoints +
                '}';
    }
}
