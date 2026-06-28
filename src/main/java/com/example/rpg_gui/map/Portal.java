package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.Engine;
import com.example.rpg_gui.core.UImanager;

public class Portal extends Tile implements Interactable {
    public Portal() {
        super(TypeTile.PortalTile);
    }

    @Override
    public void interact(Hero hero, Map map, Engine engine, UImanager uiManager) {
        System.out.println("Teleporting to dungeon");
        engine.switchMap();
    }

    @Override
    public boolean IsReactedOnStep() {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isSafeForGeneration() {
        return true;
    }

    @Override
    public Interactable asInteractable() {
        return this;
    }
}
