package br.axdber.rpggame;

public class Enemy extends Character implements Playable {
    public Enemy(String name, String sex, CombatClass combatClass) {
        super(name, sex, combatClass);
    }

    @Override
    public void takeTurn(Action chosenAction, Character target) {
        if (health > 0) {
            setBattleAction(chosenAction);
        } else {
//            setAction(new FaintedAction());
        }
        this.battleAction.act(this, target);
    }


}
