package br.axdber.rpggame;

import java.util.Scanner;

public class BattleManager {
    static Scanner in = new Scanner(System.in);

    public static Action getBattleAction(Character character) {
        System.out.printf("%s's turn\n", character.getName());
        System.out.println("Select an action");
        System.out.println("1. Attack \n2. Defense increase \n3. Attack increase \n4. RUN!");

        int option;
        do {
            option = in.nextInt();
        }
        while (option != 1 && option != 2 && option != 3 && option != 4);
        System.out.println("Option picked " + option);
        switch (option) {
            case 1:
                return new AttackAction();
            case 2:
                return new IncreaseDefenceAction();
            case 3:
                return new IncreaseAttackAction();
            case 4:
                return new EscapeAction();
            default:
                return null;
        }

    }

    public static Action getRandomBattleAction(Character character) {
        System.out.printf("%s's turn\n", character.getName());
        Dice pickActionDice = new Dice(3);
        pickActionDice.roll();
        switch (pickActionDice.getResult()) {
            case 2:
                return new IncreaseDefenceAction();
            default:
                return new AttackAction();
        }


    }
}
