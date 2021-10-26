package br.axdber.rpggame;

public class Battle {
    private Player player;
    private Enemy enemy;
    private Action action;

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        fight();
    }

    private void fight() {
        int i = 0;

        do {
            if (i % 2 == 0) {
                action = BattleManager.getBattleAction(player);
                System.out.println("Player action is " + action);
                player.takeTurn(action, enemy);
                System.out.println("Player has " + player.getHealth() + " health.");
                System.out.println("Enemy has " + enemy.getHealth() + " health.");
            } else {
                action = BattleManager.getRandomBattleAction(enemy);
                System.out.println("Enemy action is " + action);
                enemy.takeTurn(action, player);
                System.out.println("Enemy has " + enemy.getHealth() + " health.");
                System.out.println("Player has " + player.getHealth() + " health.");
            }
            i++;

        } while (player.getHealth() > 0 && enemy.getHealth() > 0);
    }
}
