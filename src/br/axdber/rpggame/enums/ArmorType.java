package br.axdber.rpggame.enums;

public enum ArmorType {

    PLATE("Armadura Platinada"),
    MAIL("Armadura de Malha"),
    LEATHER("Armadura de Couro"),
    CLOTH("Robe Majestoso");
    public final String name;

    ArmorType(String name) {
        this.name = name;
    }
}
