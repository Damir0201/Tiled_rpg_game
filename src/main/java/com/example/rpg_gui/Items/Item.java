package com.example.rpg_gui.Items;

import com.example.rpg_gui.Characters.Hero;

public abstract class Item {
    private String name;
    private int price;

    public Item (String name, int price) {
        this.name = name;
        this.price = price;
    }
    public Item(String name) {this.name=name;}
    public boolean isConsumable() {return true;}
    public String getItemName () {return name;}
    public int getItemPrice () { return price;}
    public abstract Item copy();
    public abstract void use (Hero hero);
}