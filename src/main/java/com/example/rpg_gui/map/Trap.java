package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.Engine;
import com.example.rpg_gui.core.UImanager;

public class Trap extends Tile implements Interactable{
    private boolean used = false;

    public Trap() {
        super(TypeTile.Trap);
    }

    @Override
    public TypeTile getType() {
        if (this.used) {
            return TypeTile.Floor;
        } else {
            return TypeTile.Trap;
        }
    }

    @Override
    public void interact(Hero hero, Map map, Engine engine, UImanager uiManager) {
        System.out.println("Trap triggered!");
        hero.takeDamage(20);
        this.used=true;
    }

    @Override
    public boolean IsReactedOnStep() {
        return true;
    }

    @Override
    public boolean isActive() {
        return !used;
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
