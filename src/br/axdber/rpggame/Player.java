package br.axdber.rpggame;


public class Player extends Character implements Playable {

    public Player(String name, String sex, CombatClass combatClass) {
        super(name, sex, combatClass);
    }

    @Override
    public void takeTurn(Action chosenAction, Character target) {
        // check if player is dead
        if (health > 0) {
            this.setBattleAction(chosenAction);
        } else {
//            setAction(new FaintedAction());
        }
        this.battleAction.act(this, target);
    }




}
