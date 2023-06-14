package br.axdber.rpggame.enums;

import java.util.HashMap;
import java.util.Map;

public enum DifficultyType {
    EASY("Fácil"),
    NORMAL("Normal"),
    HARD("Difícil");
    public final String name;
    private static final Map<String, DifficultyType> BY_NAME = new HashMap<>();

    DifficultyType(String name) {
        this.name = name;
    }

    static {
        for (DifficultyType e : values()) {
            BY_NAME.put(e.name, e);
        }
    }
}
