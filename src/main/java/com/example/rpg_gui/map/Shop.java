package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.Engine;
import com.example.rpg_gui.core.UImanager;

public class Shop extends Tile implements Interactable {
    public Shop() {
        super(TypeTile.ShopTile);
    }

    @Override
    public void interact(Hero hero, Map map, Engine engine, UImanager uiManager) {
        System.out.println("Opening shop interface...");
        uiManager.showShop(hero);
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
