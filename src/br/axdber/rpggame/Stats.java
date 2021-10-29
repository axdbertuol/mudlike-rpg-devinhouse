package br.axdber.rpggame;

import lombok.Data;

@Data
public class Stats {
    private int maxHealth;
    private int attackPoints;
    private int defencePoints;
    private int health;


    public Stats(
            int maxHealth,
            int attackPoints,
            int defencePoints
    ) {
        this.maxHealth = maxHealth;
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
        this.health = maxHealth;
    }

    public void decreaseHealth(int damage) {
        this.setHealth(Math.max(this.health - damage, 0));
    }
    public void increaseHealth(int heal) {
        this.setHealth(Math.min(this.health + heal, this.maxHealth));
    }

    public void calculateAttackPoints(int atp){
        this.setAttackPoints(this.attackPoints + atp);
    }
    public void calculateDefencePoints(int dfp){
        this.setDefencePoints(this.defencePoints + dfp);
    }
    public void calculateMaxHealth(int mH){
        this.setMaxHealth(this.maxHealth + mH);
    }
    public void setHealthToMax(){
        this.setHealth(maxHealth);
    }
}
