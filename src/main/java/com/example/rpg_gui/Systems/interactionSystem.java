package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Items.Item;
import com.example.rpg_gui.core.GameEngine;
import com.example.rpg_gui.core.UImanager;
import com.example.rpg_gui.map.*;

public class interactionSystem {

    public static void interact(Hero hero, Map map, GameEngine engine, UImanager uImanager) {
        Position p = hero.getPosition();
        TypeTile type = map.getTiles()[p.getMyY()][p.getMyX()].getType();
        if (type == TypeTile.PortalTile) {
            engine.switchMap();
            return;
        }

        int[][] dists = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] dist : dists) {
            int checkX = p.getMyX() + dist[0];
            int checkY = p.getMyY() + dist[1];

            if (checkX >= 0 && checkX < map.getWidth() && checkY >= 0 && checkY < map.getHeight()) {
                Tile currentTile = map.getTiles()[checkY][checkX];

                if (currentTile instanceof Chest) {
                    Chest chest = (Chest) currentTile;
                    if (!chest.isOpened()) {
                        int money = chest.openChest();
                        hero.earnMoney(money);
                        currentTile.setType(TypeTile.Floor);
                    }
                    Item item = chest.getContainedItem();
                    if (item != null) {
                        System.out.println("From chest you got " + item.getItemName() + "!");
                        hero.getInventory().addItem(item);
                    }
                }
                if (currentTile instanceof Door) {
                    Door door = (Door) currentTile;

                    if (door.isLocked()) {
                        if (hero.getInventory().hasKeyCode(door.getLockcode())) {
                            System.out.println("Door opened");
                            map.getTiles()[checkY][checkX] = new Tile(TypeTile.Floor);
                        } else {
                            System.out.println("To open this door you need: " + door.getLockcode());
                        }
                    }
                }
                if (currentTile.getType() == TypeTile.AcademyTile) {
                    academySystem.teachSkills(hero);
                    return;
                }
                if (currentTile.getType() == TypeTile.ShopTile) {
                    System.out.println("Opening shop...");
                    uImanager.showShop(hero);
                    return;
                }
            }
        }
    }
}
