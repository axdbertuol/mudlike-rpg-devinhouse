package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

import java.util.ArrayList;
import java.util.Scanner;

public class BattleManager {
    static Scanner in = new Scanner(System.in);

    public static Action getBattleAction(Character character) {
        System.out.printf("%s's turn\n", character.getName());
        System.out.println("Selecione uma ação:");

        System.out.println("0. Escape");
        ArrayList<Action> actions = showActions(character);

        int option;
        do {
            option = in.nextInt();
        }
        while (option < 0 || option > actions.size() );
        System.out.println("Option picked " + option);
        return whichAction(option, actions);


    }


    public static Action getRandomBattleAction(Character character) {
        System.out.printf("%s's turn\n", character.getName());
        int charLvl = character.getLevel().getValue();
        ArrayList<Action> actions = character.getCombatClass().actionsAllowedPerLvl.get(charLvl);
        Dice pickActionDice = new Dice(actions.size());
        pickActionDice.roll();

        return whichAction(pickActionDice.getResult(), actions);


    }

    private static ArrayList<Action> showActions(Character character) {
        int charLvl = character.getLevel().getValue();
        ArrayList<Action> actions = character.getCombatClass().actionsAllowedPerLvl.get(charLvl);

        for (int i = 0; i < actions.size(); i++) {
            System.out.println(i + 1 + ". " + actions.get(i).toString());
        }
        return actions;
    }

    private static Action whichAction(int option, ArrayList<Action> actions) {
        switch (option) {
            case 1 -> {
                return actions.get(0);
            }
            case 2 -> {
                return actions.get(1);
            }
            case 3 -> {
                return actions.size() >= 3 ? actions.get(2) : null;
            }
            case 4 -> {
                return actions.size() >= 4 ? actions.get(3) : null;
            }
            case 5 -> {
                return actions.size() >= 5 ? actions.get(4) : null;
            }
            case 0 -> {
                return new EscapeAction();
            }
            default -> {
                return null;
            }
        }
    }
}
