package br.axdber.rpggame;

import br.axdber.rpggame.interfaces.Action;

public class EscapeAction implements Action {


    @SafeVarargs
    @Override
    public final <T extends Character> void act(T... args) {
        Character character = args[0];
        character.setHealth(0);
        System.out.println("Você não estava preparado para a força do inimigo, e decide fugir para que possa tentar novamente em uma próxima vez.");
    }

    @Override
    public String toString() {
        return "Escapar";
    }
}
