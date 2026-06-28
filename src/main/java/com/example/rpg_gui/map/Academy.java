package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Systems.AcademySystem;
import com.example.rpg_gui.core.Engine;
import com.example.rpg_gui.core.UImanager;

public class Academy extends Tile implements Interactable {
    public Academy() {
        super(TypeTile.AcademyTile);
    }

    @Override
    public void interact(Hero hero, Map map, Engine engine, UImanager uiManager) {
        System.out.println("Entering the Academy...");
        AcademySystem.teachSkills(hero);
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
    public Interactable asInteractable() {
        return this;
    }
}
