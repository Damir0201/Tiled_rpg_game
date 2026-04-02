package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Items.Item;
import com.example.rpg_gui.Items.Key;

import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {
    private ArrayList <Item> items = new ArrayList<Item>();

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


    private void showItems() {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i+1) + ". " + items.get(i).getItemName());
        }
    }


    private void useItem (int i, Hero hero) {
        Item item = items.get(i);
        item.use(hero);
        removeItem(item);
    }


    public void open (Hero hero) {
        Scanner in = new Scanner(System.in);
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        boolean running = true;

        while (running) {
            System.out.println("0. Exit");
            showItems();
            System.out.println("Choose number: ");
            int choice = in.nextInt();

            if (choice == 0) {
                running = false;
            } else if (choice > 0 && choice <= items.size()) {
                useItem(choice - 1, hero);
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

}
