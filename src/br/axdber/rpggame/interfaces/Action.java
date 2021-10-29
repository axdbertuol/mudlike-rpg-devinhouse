package br.axdber.rpggame.interfaces;

import br.axdber.rpggame.Character;

public interface Action {

    <T extends Character> void act(T... args);
    String toString();
}
