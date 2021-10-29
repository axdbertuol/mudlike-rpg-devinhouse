package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

public class AttackAction implements Action {


    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character attacker = args[0];
        Character defender = args[1];
        Dice dice = Dice.D20();
        dice.roll();
        System.out.println(dice.reportResult());


        // Miss
        if (dice.getResult() == 1) {
            System.out.println(attacker.reportMissedAttack());
            return;
        }

        int damage = dice.getResult() + attacker.stats.getAttackPoints() + attacker.tempStats.getAttackPoints();

        // Critical
        if (dice.getResult() == 20) {

            System.out.println(attacker.reportCriticalAttack());
            System.out.println(attacker.reportAttack(damage, defender.getName()));
            defender.decreaseHealth(damage);
            return;
        }

        // Defended
        int totalDefenderDFP = defender.stats.getDefencePoints() + defender.tempStats.getDefencePoints();
        if (totalDefenderDFP >= damage) {
            System.out.println(attacker.reportAttack(damage, defender.getName()));
            System.out.println(attacker.name + ", seu ataque foi totalmente bloqueado!");
            return;
        }
        damage = Math.max(damage - totalDefenderDFP, 0);
        System.out.println(attacker.reportAttack(damage, defender.getName()));
        defender.decreaseHealth(damage);

    }

    @Override
    public String toString() {
        return "Atacar";
    }
}
