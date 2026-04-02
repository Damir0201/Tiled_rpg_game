package com.example.rpg_gui.Items;

import com.example.rpg_gui.Characters.Hero;

public class Potion extends Item{
    public enum potionType {Health, Mana}

    private final potionType typeOfPotion;
    private int restoreAmount;

    public Potion(String name, int price, potionType type, int restoreAmount) {
        super(name, price);
        this.typeOfPotion = type;
        this.restoreAmount = restoreAmount;
    }

    public potionType getPotionType () {
        return typeOfPotion;
    }
    public int getRestoreAmount () {
        return restoreAmount;
    }
    @Override
    public void use (Hero hero){
        System.out.println("You used " + getItemName());
        switch (typeOfPotion) {
            case Health:
                hero.restoreHealth(restoreAmount);
                break;
            case Mana:
                hero.restoreMana(restoreAmount);
                break;
        }
    }


    @Override
    public Item copy() {
        return new Potion(getItemName(), getItemPrice(), getPotionType(), restoreAmount);
    }


    public static final Potion sHealthPotion = new Potion ("Small Health Potion", 10, potionType.Health, 50);
    public static final Potion lHealthPotion = new Potion ("Large Health Potion", 20, potionType.Health, 100);
    public static final Potion sManaPotion = new Potion ("Small Mana Potion", 10, potionType.Mana, 50);
    public static final Potion lManaPotion = new Potion ("Large Mana Potion", 20, potionType.Mana, 100);
}