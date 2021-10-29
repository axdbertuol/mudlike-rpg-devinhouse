package br.axdber.rpggame.enums;

public enum StatusType {

    POISONED("Envenenado", 2, 5, 0),
    BLEEDING("Sangrando", 10, 3, -1),
    BURNED("Queimado", -5, 5, +1),
    FROZEN("Congelado", 0, 5, -1);

    public final String name;
    public final int dmg;
    public final int turns;
    public final int change;

    StatusType(String name, int dmg, int turns, int change) {
        this.name = name;
        this.dmg = dmg;
        this.turns = turns;
        this.change = change;
    }
}
