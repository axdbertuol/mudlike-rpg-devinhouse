package br.axdber.rpggame;

public class AttackAction implements Action {


    @Override
    public <T extends Character> void act(T... args) {
        Character attacker = args[0];
        Character defender = args[1];
        Dice dice = Dice.D20();
        dice.roll();

        // Miss
        if (dice.getResult() == 1) {
            return;
        }

        int damage = dice.getResult() + attacker.attackPoints + attacker.getWeapon().getAttackPoints();

        // Critical
        if (dice.getResult() == 20) {
            defender.decreaseHealth(damage);
            return;
        }

        // Defended
        if (defender.getDefencePoints() > damage) {
            return;
        }

        defender.decreaseHealth(damage - defender.getDefencePoints());

    }

    @Override
    public String toString() {
        return "AttackAction{}";
    }
}
