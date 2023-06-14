package br.axdber.rpggame.interfaces;

import br.axdber.rpggame.Character;

public interface BattleAble {
    void takeTurn(Action chosenAction, Character target);
}
