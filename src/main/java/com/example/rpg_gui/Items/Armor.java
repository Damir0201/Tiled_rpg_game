package com.example.rpg_gui.Items;

import com.example.rpg_gui.Characters.Hero;

public class Armor extends Item {
    private final int defenceBonus;

    public int getDefenceBonus() {
        return defenceBonus;
    }

    public Armor(String name, int price, int defenceBonus) {
        super(name, price);
        this.defenceBonus = defenceBonus;
    }


    @Override
    public void use(Hero hero) {
        if (hero == null) return;
        hero.setEquippedArmor(this);
    }
    @Override
    public Item copy() {
        return new Armor(getItemName(), getItemPrice(), defenceBonus);
    }
    public static final Armor leatherArmor = new Armor ("Leather Armor", 50, 5);
    public static final Armor ironArmor = new Armor ("Iron Armor", 120, 10);
    public static final Armor stealArmor = new Armor ("Steal Armor", 200, 20);
}
