package br.axdber.rpggame;

import br.axdber.rpggame.enums.DifficultyType;
import br.axdber.rpggame.enums.SexType;
import br.axdber.rpggame.interfaces.Action;
import br.axdber.rpggame.interfaces.BattleAble;
import br.axdber.rpggame.interfaces.NonPlayable;



public class Enemy extends Character implements BattleAble, NonPlayable {

    private final int expValue = 100;
    private double difficultyRate;
    private int currentExpValue;

    public Enemy(
            String name,
            SexType sex,
            CombatClass combatClass,
            Weapon weapon,
            Armor armor,
            int level,
            DifficultyType difficultyType) {
        super(name, sex, combatClass, weapon, armor, level);
        this.currentExpValue = (level + 1) * expValue;
        this.difficultyRate = difficultyType == DifficultyType.EASY ? easyDifficultyRate : hardDifficultyRate;
        // set stats according to difficulty type
        calcStatsByDifficulty();
    }

    @Override
    public void takeTurn(Action chosenAction, Character target) {
        if (this.stats.getHealth() > 0) {
            setBattleAction(chosenAction);
        }
        this.battleAction.act(this, target);
    }


    @Override
    public int getExpValue() {
        return currentExpValue;
    }

    @Override
    public void setExpValue(int xp) {
        this.currentExpValue = xp;
    }

    @Override
    public double getDifficultyRate() {
        return this.difficultyRate;
    }

    @Override
    public void setDifficultyRate(double rate) {
        this.difficultyRate = rate;
    }

    @Override
    public void calcStatsByDifficulty() {
        this.stats.calculateMaxHealth((int) (Math.ceil(this.stats.getMaxHealth() * difficultyRate)));
        this.setHealthToMax();
        this.stats.calculateDefencePoints((int) (Math.ceil(this.stats.getDefencePoints() * difficultyRate)));
        this.stats.calculateAttackPoints((int) (Math.ceil(this.stats.getAttackPoints() * difficultyRate)));

    }
}
