package br.axdber.rpggame;


import br.axdber.rpggame.enums.MotivationType;
import br.axdber.rpggame.enums.SexType;
import br.axdber.rpggame.interfaces.Action;
import br.axdber.rpggame.interfaces.BattleAble;
import br.axdber.rpggame.interfaces.Playable;

public class Player extends Character implements BattleAble, Playable {
    protected MotivationType motivation;

    public Player(String name,
                  SexType sex, CombatClass combatClass,
                  Weapon weapon, Armor armor, MotivationType motivation,
                  int lvl) {
        super(name, sex, combatClass, weapon, armor, lvl);
        this.motivation = motivation;
    }

    @Override
    public void takeTurn(Action chosenAction, Character target) {
        // check if player is dead
        if (this.getHealth() > 0) {
            this.setBattleAction(chosenAction);
        }
        this.battleAction.act(this, target);
    }


    @Override
    public MotivationType getMotivationType() {
        return this.motivation;
    }

    @Override
    public void setMotivationType(MotivationType type) {
        this.motivation = type;
    }
}
