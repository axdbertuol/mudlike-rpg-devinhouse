package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;
import lombok.Getter;

@Getter
public class Battle {
    private final Player player;
    private final Enemy enemy;
    private Action action;
    private int experience;
    private Character winner;

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.experience = 0;
        fight();
    }

    private void fight() {
        int i = 0;

        do {
            if (i % 2 == 0) {
                player.debuffStatusTick();
                player.dmgStatusTick();
                action = BattleManager.getBattleAction(player);
                player.takeTurn(action, enemy);
                System.out.println(player.reportAction());
                System.out.println(player.reportHealth());
                System.out.println(enemy.reportHealth());
            } else {
                enemy.debuffStatusTick();
                enemy.dmgStatusTick();
                action = BattleManager.getRandomBattleAction(enemy);
                enemy.takeTurn(action, player);
                System.out.println(enemy.reportAction());
                System.out.println(enemy.reportHealth());
                System.out.println(player.reportHealth());
            }
            i++;

        } while (player.stats.getHealth() > 0 && enemy.stats.getHealth() > 0);

        player.resetTempStats();
        player.resetAllStatus();
        enemy.resetTempStats();
        enemy.resetAllStatus();
        if (player.stats.getHealth() <= 0) {
            winner = enemy;
        } else {
            winner = player;
            experience = enemy.getExpValue();
            winner.levelUp(experience, true, true);
        }


    }

    public boolean reportEncounterResult() {
        if (getWinner().equals(enemy)) {
            System.out.println("Você não estava preparado para a força do inimigo, e decide fugir para que possa tentar novamente em uma próxima vez.");
            System.out.println("Game Over");
            return true;
        }
        System.out.println("O inimigo não é páreo para o seu heroísmo, e jaz imóvel aos seus pés.");
        return false;

    }

    @Override
    public String toString() {
        return "Battle{" +
                "experience=" + experience +
                ", winner=" + winner +
                '}';
    }
}
