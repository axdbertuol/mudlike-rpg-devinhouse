package br.axdber.rpggame;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private HashMap<CombatClassType, CombatClass> combatClasses;
    private HashMap<WeaponType, Weapon> weaponHashMap;
    private HashMap<ArmorType, Armor> armorHashMap;


    public Game() {
        this.weaponHashMap = new HashMap<>();
        this.armorHashMap = new HashMap<>();
        this.combatClasses = new HashMap<>();

        generateArmors();
        generateWeapons();
        generateCombatClasses();

    }

    public void generateWeapons() {
        for (WeaponType wt : WeaponType.values()) {
            Weapon weapon = new Weapon(3, wt);
            weaponHashMap.put(wt, weapon);
        }
    }

    public void generateArmors() {
        for (ArmorType at : ArmorType.values()) {
            Armor armor = new Armor(3, at);
            armorHashMap.put(at, armor);
        }
    }

    public void generateCombatClasses() {
        Weapon weapon = weaponHashMap.get(WeaponType.BOW);
        Armor armor = armorHashMap.get(ArmorType.MAIL);
        CombatClass combatClass = new CombatClass(3, 3, weapon, armor, CombatClassType.ARCHER);
        combatClasses.put(CombatClassType.ARCHER, combatClass);

        weapon = weaponHashMap.get(WeaponType.AXE);
        armor = armorHashMap.get(ArmorType.LEATHER);
        combatClass = new CombatClass(5, 2, weapon, armor, CombatClassType.BARBARIAN);
        combatClasses.put(CombatClassType.BARBARIAN, combatClass);

        weapon = weaponHashMap.get(WeaponType.SWORD);
        armor = armorHashMap.get(ArmorType.PLATE);
        combatClass = new CombatClass(2, 5, weapon, armor, CombatClassType.WARRIOR);
        combatClasses.put(CombatClassType.WARRIOR, combatClass);
    }

    public static void main(String[] args) {
        Game game = new Game();

        Player player = new Player("Alex", "male", game.combatClasses.get(CombatClassType.ARCHER));
        Enemy enemy = new Enemy("Kim", "male", game.combatClasses.get(CombatClassType.WARRIOR));
        Battle encounter = new Battle(player, enemy);
    }
}
