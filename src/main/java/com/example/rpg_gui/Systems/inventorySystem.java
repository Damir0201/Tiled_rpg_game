package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Items.Item;
import com.example.rpg_gui.Items.Key;

import java.util.ArrayList;

public class inventorySystem {
    private ArrayList <Item> items = new ArrayList<Item>();
    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem (Item item) {
        items.add(item);
        System.out.println(item.getItemName() + " added to inventory.");
    }

    public boolean hasKeyCode(String code) {
        for(Item item:items) {
            if(item instanceof Key) {
                Key key=(Key) item;
                if(key.getKeycode().equals(code)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeItem (Item item) {
        items.remove(item);
        System.out.println(item.getItemName() + " removed from inventory.");
    }

    public void useItem (int i, Hero hero) {
        Item item = items.get(i);
        item.use(hero);
        if (!(item instanceof Key)) {
            removeItem(item);
        }
    }

}
