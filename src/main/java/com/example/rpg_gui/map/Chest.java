package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Items.Item;
import com.example.rpg_gui.core.Engine;
import com.example.rpg_gui.core.UImanager;

public class Chest extends Tile implements Interactable {
    private int money;
    private boolean isOpened=false;
    private Item containedItem;

    public Chest(int money) {
        super(TypeTile.ChestTile);
        this.money=money;
        this.containedItem=null;
    }
    public Chest(int money,Item containedItem) {
        super(TypeTile.ChestTile);
        this.money=money;
        this.containedItem=containedItem;
    }

    @Override
    public TypeTile getType() {
        if (this.isOpened) {
            return TypeTile.Floor;
        } else {
            return TypeTile.ChestTile;
        }
    }

    @Override
    public boolean isAllowingMove() {
        return isOpened || super.isAllowingMove();
    }

    public int openChest() {
        if(!isOpened) {
            isOpened=true;
            int reward=money;
            money=0;
            return reward;
        }
        return 0;
    }

    public boolean isOpened() {
        return isOpened;
    }


    @Override
    public void interact(Hero hero, Map map, Engine engine, UImanager uiManager) {
        if (!this.isOpened()) {
            int money = this.openChest();
            hero.earnMoney(money);
        }
        if (this.containedItem != null) {
            System.out.println("From chest you got " + containedItem.getItemName() + "!");
            hero.addItem(containedItem);
            this.containedItem = null;
        }
    }

    @Override
    public boolean IsReactedOnStep() {
        return false;
    }

    @Override
    public boolean isActive() {
        return !isOpened;
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
