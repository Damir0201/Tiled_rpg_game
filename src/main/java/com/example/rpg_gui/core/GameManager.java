package com.example.rpg_gui.core;

import com.example.rpg_gui.Characters.Hero;

public class GameManager {
    private static GameManager instance;
    private Hero player;

    private GameManager() {}
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    public Hero getPlayer() {
        return player; }
    public void setPlayer(Hero player) {
        this.player = player; }
}