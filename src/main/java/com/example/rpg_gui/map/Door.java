package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameEngine;
import com.example.rpg_gui.core.UImanager;

public class Door extends Tile implements Interactable {
    private boolean isLocked=true;
    private final String lockcode;

    public Door(String lockcode) {
        super(TypeTile.DoorTile);
        this.lockcode=lockcode;
    }

    private final String getLockcode() {
        return lockcode;
    }

    private final boolean isLocked() {
        return isLocked;
    }

    @Override
    public void interact(Hero hero, Map map, GameEngine engine, UImanager uiManager) {
        if (hero.getInventory().hasKeyCode(this.lockcode)) {
            System.out.println("Door opened successfully!");
            this.isLocked = false;
            this.setType(TypeTile.Floor);
        } else {
            System.out.println("To open this door you need a key: " + this.lockcode);
        }
    }
}
