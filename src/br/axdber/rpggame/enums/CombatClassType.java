package br.axdber.rpggame.enums;

public enum CombatClassType {
    BARBARIAN("Bárbaro"),
    ARCHER("Arqueiro"),
    WARRIOR("Guerreiro"),
    MAGE("Mago");
    public final String name;

    CombatClassType(String name) {
        this.name = name;
    }
}
