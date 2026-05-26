package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameEngine;
import com.example.rpg_gui.core.UImanager;
import com.example.rpg_gui.map.*;

public class interactionSystem {
    public static void checkStepOnTile(Hero hero, Map map, GameEngine engine, UImanager uImanager) {
        Position p = hero.getPosition();
        Tile standingTile = map.getTiles()[p.getMyY()][p.getMyX()];

        if (standingTile instanceof Interactable) {
            ((Interactable) standingTile).interact(hero, map, engine, uImanager);
        }
    }

    public static void interact(Hero hero, Map map, GameEngine engine, UImanager uImanager) {
        Position p = hero.getPosition();
        Tile standingTile = map.getTiles()[p.getMyY()][p.getMyX()];
        if(standingTile instanceof Interactable) {
            ((Interactable) standingTile).interact(hero, map, engine, uImanager);
            return;
        }

        int[][] dists = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] dist : dists) {
            int checkX = p.getMyX() + dist[0];
            int checkY = p.getMyY() + dist[1];

            if (checkX >= 0 && checkX < map.getWidth() && checkY >= 0 && checkY < map.getHeight()) {
                Tile currentTile = map.getTiles()[checkY][checkX];
                if (currentTile instanceof Interactable && !(currentTile instanceof Trap)) {
                    ((Interactable) currentTile).interact(hero, map, engine, uImanager);
                    return;
                }
            }
        }
    }
}
