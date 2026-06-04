package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameEngine;
import com.example.rpg_gui.core.UImanager;

public class Trap extends Tile implements Interactable{
    private boolean used = false;

    public Trap() {
        super(TypeTile.Trap);
    }

    @Override
    public void interact(Hero hero, Map map, GameEngine engine, UImanager uiManager) {
        System.out.println("Trap triggered!");
        hero.takeDamage(20);
        this.used=true;
        this.setType(TypeTile.Floor);
    }

    @Override
    public boolean IsReactedOnStep() {
        return true;
    }

    @Override
    public boolean isActive() {
        return !used;
    }
}
