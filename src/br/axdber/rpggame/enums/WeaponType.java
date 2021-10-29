package br.axdber.rpggame.enums;

public enum WeaponType{
    SWORD("Espada", "num vai-e-vem de movimentos", 0, false),
    AXE("Machado", "num arremate só", 1, false),
    BOW("Arco", "atirando sua flecha", 1, false),
    CROSSBOW("Besta", "atirando seu virote", 0, false),
    STAFF("Cajado", "lançando uma bola de energia", 1, false),
    WAND("Varinha", "lançando uma seta de sombras", 0, false),
    HANDS("Mãos", "socando", 0, true);
    public final String name;
    public final String ataque;
    public final int wordGender; // 0 -> feminino, 1 -> masculino
    public final boolean plural;

    WeaponType(String name, String ataque, int wordGender, boolean plural) {
        this.name = name;
        this.ataque = ataque;
        this.wordGender = wordGender;
        this.plural = plural;
    }
}
