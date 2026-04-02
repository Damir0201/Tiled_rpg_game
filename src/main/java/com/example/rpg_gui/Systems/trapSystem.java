package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameManager;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;
import com.example.rpg_gui.map.Trap;
import com.example.rpg_gui.map.TypeTile;

public class trapSystem {
    public static void checkTrap(Position hPos, Map map) {
        if (map.getTiles()[hPos.getMyY()][hPos.getMyX()] instanceof Trap) {
            Hero hero = GameManager.getInstance().getPlayer();
            if (hero != null) {
                System.out.println("Trap!");
                hero.takeDamage(20);
                map.getTiles()[hPos.getMyY()][hPos.getMyX()] = new com.example.rpg_gui.map.Tile(TypeTile.Floor);
            }
        }
    }
}