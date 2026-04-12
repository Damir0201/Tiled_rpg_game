package com.example.rpg_gui.map.generators;

public enum Difficulty {
    EASY(5), NORMAL(8), HARD(12);
    public final int monsterCount;

    Difficulty(int count) {
        this.monsterCount = count;
    }
}
