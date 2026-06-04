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
            Interactable reactedOnStepObj = (Interactable) standingTile;
            if (reactedOnStepObj.isActive() && reactedOnStepObj.IsReactedOnStep()) {
                reactedOnStepObj.interact(hero, map, engine, uImanager);
            }
        }
    }

    public static void interact(Hero hero, Map map, GameEngine engine, UImanager uImanager) {
        if (tryInteractWhenStandOn(hero, map, engine, uImanager)) {
            return;
        }
        InteractWithAdjacent(hero, map, engine, uImanager);
    }
    private static boolean tryInteractWhenStandOn(Hero hero, Map map, GameEngine engine, UImanager uImanager) {
        Position p = hero.getPosition();
        Tile standingTile = map.getTiles()[p.getMyY()][p.getMyX()];
        if(standingTile instanceof Interactable) {
            Interactable reactedObject = (Interactable) standingTile;
            if (reactedObject.isActive() && !reactedObject.IsReactedOnStep()) {
                reactedObject.interact(hero, map, engine, uImanager);
                return true;
            }
        }
        return false;
    }
    private static void InteractWithAdjacent(Hero hero, Map map, GameEngine engine, UImanager uImanager) {
        Position p = hero.getPosition();
        int[][] dists = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] dist : dists) {
            int checkX = p.getMyX() + dist[0];
            int checkY = p.getMyY() + dist[1];

            if (checkX >= 0 && checkX < map.getWidth() && checkY >= 0 && checkY < map.getHeight()) {
                Tile currentTile = map.getTiles()[checkY][checkX];
                if (currentTile instanceof Interactable) {
                    Interactable adjacentObj = (Interactable) currentTile;
                    if (adjacentObj.isActive() && !adjacentObj.IsReactedOnStep()) {
                        adjacentObj.interact(hero, map, engine, uImanager);
                        return;
                    }
                }
            }
        }
    }
}
