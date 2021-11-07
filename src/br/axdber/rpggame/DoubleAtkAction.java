package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

import java.util.Arrays;

public class DoubleAtkAction implements Action {
    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        // This attack can't critically hit
        // Consists of two dice rolls of 16

        Character attacker = args[0];
        Character defender = args[1];
        int[] results = new int[2];
        Dice dice = Dice.D16();
        dice.roll();
        dice.reportResult();
        results[0] = dice.getResult();
        dice.roll();
        dice.reportResult();
        results[1] = dice.getResult();

        // Total Miss
        if (results[0] == 1 && results[1] == 1) {
            System.out.println("Você errou ambos os ataques!");
            return;
        }

        if (results[0] == 1) {
            results[0] = 0;
            System.out.println("Você errou o primeiro ataque!");
        } else if (results[1] == 1) {
            System.out.println("Você errou o segundo o ataque!");
            results[1] = 0;
        }

        int damage = Arrays.stream(results).sum() + attacker.getTotalATP();


        // Defended
       int totalDefenderDFP = defender.getTotalDFP();
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
        return "Ataque Duplo";
    }
}
