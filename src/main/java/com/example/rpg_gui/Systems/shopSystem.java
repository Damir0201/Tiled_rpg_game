package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Items.*;
import com.example.rpg_gui.Characters.Hero;
import java.util.ArrayList;

public class shopSystem {
    private static final ArrayList<Item> itemList = new ArrayList<Item>();

    static {
        itemList.add(Potion.sHealthPotion);
        itemList.add(Potion.lHealthPotion);
        itemList.add(Potion.sManaPotion);
        itemList.add(Potion.lManaPotion);

        itemList.add(Weapon.woodenSword);
        itemList.add(Weapon.woodenBow);
        itemList.add(Weapon.woodenStaff);
        itemList.add(Weapon.ironSword);
        itemList.add(Weapon.ironBow);
        itemList.add(Weapon.ironStaff);
        itemList.add(Weapon.goldSword);
        itemList.add(Weapon.goldBow);
        itemList.add(Weapon.goldStaff);

        itemList.add(Armor.leatherArmor);
        itemList.add(Armor.ironArmor);
        itemList.add(Armor.stealArmor);
    }


    public static ArrayList<Item> getItemList() {
        return itemList;
    }


    public static boolean buyItem(Hero hero, int index) {
        if (index < 0 || index >= itemList.size()) {return false;}

        Item item = itemList.get(index);

        if (hero.getMoney() < item.getItemPrice()) {
            System.out.println("You do not have enough money!");
            return false;
        }

        if (item instanceof Weapon weapon) {
            if (weapon.getAllowedHero() != hero.getHero()) {
                System.out.println("Wrong hero class!");
                return false;
            }
        }

        hero.spendMoney(item.getItemPrice());
        System.out.println("You bought " + item.getItemName());
        hero.addItem(item.copy());
        return true;
    }
}
