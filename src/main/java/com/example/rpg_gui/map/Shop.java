package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameEngine;
import com.example.rpg_gui.core.UImanager;

public class Shop extends Tile implements Interactable {
    public Shop() {
        super(TypeTile.ShopTile);
    }

    @Override
    public void interact(Hero hero, Map map, GameEngine engine, UImanager uiManager) {
        System.out.println("Opening shop interface...");
        uiManager.showShop(hero);
    }
}
