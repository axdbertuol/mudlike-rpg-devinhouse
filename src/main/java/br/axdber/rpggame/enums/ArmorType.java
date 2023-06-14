package br.axdber.rpggame.enums;

public enum ArmorType {

    PLATE("Armadura Platinada", 4, 0, 0),
    MAIL("Armadura de Malha", 2, 2, 5),
    LEATHER("Armadura de Couro", 2, 2, 5),
    CLOTH("Robe Majestoso", 1, 1, 15);
    public final String name;
    public final int dfp;
    public final int atp;
    public final int maxHealth;
    ArmorType(String name, int dfp, int atp, int maxHealth) {
        this.name = name;
        this.atp = atp;
        this.dfp = dfp;
        this.maxHealth = maxHealth;
    }
}
