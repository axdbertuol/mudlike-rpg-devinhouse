package br.axdber.rpggame;

import lombok.Data;

@Data
public class Stats {
    private int maxHealth;
    private int attackPoints;
    private int defencePoints;


    public Stats(
            int maxHealth,
            int attackPoints,
            int defencePoints
    ) {
        this.maxHealth = maxHealth;
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
    }


    public void calculateAttackPoints(int atp) {
        this.setAttackPoints(this.attackPoints + atp);
    }

    public void calculateDefencePoints(int dfp) {
        this.setDefencePoints(this.defencePoints + dfp);
    }

    public void calculateMaxHealth(int mH) {
        this.setMaxHealth(this.maxHealth + mH);
    }
}
