package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.Engine;
import com.example.rpg_gui.core.UImanager;

public class Door extends Tile implements Interactable {
    private boolean isLocked=true;
    private final String lockcode;

    public Door(String lockcode) {
        super(TypeTile.DoorTile);
        this.lockcode=lockcode;
    }

    private String getLockCode() {
        return lockcode;
    }

    private boolean isLocked() {
        return isLocked;
    }

    @Override
    public TypeTile getType() {
        if (!this.isLocked) {
            return TypeTile.Floor;
        } else {
            return TypeTile.DoorTile;
        }
    }

    @Override
    public boolean isAllowingMove() {
        return !isLocked || super.isAllowingMove();
    }

    @Override
    public void interact(Hero hero, Map map, Engine engine, UImanager uiManager) {
        if (hero.getInventory().hasKeyCode(this.lockcode)) {
            System.out.println("Door opened successfully!");
            this.isLocked = false;
        } else {
            System.out.println("To open this door you need a key: " + this.lockcode);
        }
    }

    @Override
    public boolean IsReactedOnStep() {
        return false;
    }

    @Override
    public boolean isActive() {
        return isLocked;
    }

    @Override
    public boolean isSafeForGeneration() {
        return true;
    }

    @Override
    public Interactable asInteractable() {
        return this; // Возвращаем себя в качестве интерактивного объекта
    }
}
