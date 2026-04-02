package com.example.rpg_gui.Items;

import com.example.rpg_gui.Characters.Hero;

public class Key extends Item{
    private String keycode;

    public Key (String name, String keycode) {
        super(name);
        this.keycode=keycode;
    }

    public String getKeycode() {
        return keycode;
    }

    @Override
    public Item copy() {
        return new Key(this.getItemName(),this.keycode);
    }

    @Override
    public void use(Hero hero) {
        System.out.println("You cannot use key");
    }
}
