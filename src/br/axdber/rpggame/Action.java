package br.axdber.rpggame;

public interface Action {

    <T extends Character> void act(T... args);
    String toString();
}
