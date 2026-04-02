package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Items.*;
import com.example.rpg_gui.Characters.Hero;

import java.util.ArrayList;
import java.util.Scanner;

public class shopSystem {
    private static final ArrayList<Item> itemList = new ArrayList<Item>();
    private Scanner in = new Scanner (System.in);

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
    private void showItemList() {
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            System.out.println((i+1) + ". " + item.getItemName() + "\t" + "Price:\t\t" + item.getItemPrice());
        }
    }


    private boolean checkMoney (Hero hero, Item item) {
        if (hero.getMoney() < item.getItemPrice()) {
            System.out.println("You do not have enough money.");
            return false;
        }
        return true;
    }


    private boolean checkHero (Hero hero, Item item) {
        if (item instanceof Weapon weapon) {
            if (weapon.getAllowedHero() != hero.getHero()) {
                System.out.println("You can not use this weapon!");
                return false;
            }
        }
        return true;
    }
    private void buyItem(Hero hero, int index) {
        if (index < 0 || index >= itemList.size()) {
            System.out.println("Invalid item.");
            return;
        }
        Item item = itemList.get(index);
        if (!checkMoney(hero, item)) return;
        if (!checkHero(hero, item)) return;

        hero.spendMoney(item.getItemPrice());
        Item newItem = item.copy();

        System.out.println("You bought " + newItem.getItemName());
        hero.addItem(newItem);
    }
    public void openShop (Hero hero) {
        boolean running = true;
        while (running) {
            System.out.println("Your money: " + hero.getMoney());
            System.out.println("0 - Exit shop");
            showItemList();
            System.out.println("Choose item number to buy");

            int choice = in.nextInt();
            if (choice == 0) {
                running = false;
            } else {
                buyItem(hero, choice -1);
            }
        }
    }

    public static final shopSystem shop = new shopSystem();
}
