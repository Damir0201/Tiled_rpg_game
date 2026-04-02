package com.example.rpg_gui.map;

import com.example.rpg_gui.Items.Item;
import com.example.rpg_gui.Items.Key;

public class Chest extends Tile{
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
    public int openChest() {
        if(!isOpened) {
            isOpened=true;
            int reward=money;
            money=0;
            this.setType(TypeTile.Floor);
            return reward;
        }
        return 0;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public Item getContainedItem() {
        return containedItem;
    }
}
