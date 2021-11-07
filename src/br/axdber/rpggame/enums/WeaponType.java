package br.axdber.rpggame.enums;

public enum WeaponType{
    SWORD("Espada", 2, 1, 5, "num vai-e-vem de movimentos", 0, false),
    AXE("Machado", 4, 0, 0,  "num arremate só", 1, false),
    BOW("Arco", 3, 0, 0, "atirando sua flecha", 1, false),
    CROSSBOW("Besta", 4, -1, 5, "atirando seu virote", 0, false),
    STAFF("Cajado", 3, 1, 0, "lançando uma bola de energia", 1, false),
    WAND("Varinha", 4, -1, 15,"lançando uma seta de sombras", 0, false),
    HANDS("Mãos", 1,1, 20,"socando forte", 0, true);
    public final String name;
    public final String atkString;
    public final int wordGender; // 0 -> feminino, 1 -> masculino
    public final int atp;
    public final int dfp;
    public final int maxHealth;
    public final boolean plural;

    WeaponType(String name, int atp, int dfp, int maxHealth, String atkString, int wordGender, boolean plural) {
        this.name = name;
        this.atp = atp;
        this.dfp = dfp;
        this.maxHealth = maxHealth;
        this.atkString = atkString;
        this.wordGender = wordGender;
        this.plural = plural;
    }
}
