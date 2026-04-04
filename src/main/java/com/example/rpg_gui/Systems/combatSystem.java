package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;

import java.util.ArrayList;

public class combatSystem {
    public static void tryAttack(Hero hero, Map map) {
        Position hPos= hero.getPosition();
        java.util.ArrayList<Enemy> targets = new java.util.ArrayList<>();

        for (Enemy e:map.getEnemies()) {
            if (!e.isAlive()) continue;
            Position ePos=e.getPosition();
            int dist=Math.abs(hPos.getMyX() - ePos.getMyX())+ Math.abs(hPos.getMyY() - ePos.getMyY());
            if (dist <= hero.getAttackDistance()) {
                targets.add(e);
            }
        }

        if (!targets.isEmpty()) {
            System.out.println("\nYour turn");
            hero.useSelectedAttack(targets.toArray(new Enemy[0]));

            for (Enemy target : targets) {
                if (target.isAlive()) {
                    System.out.println(target.getName() + "'s turn");

                    int distToHero = Math.abs(hPos.getMyX() - target.getPosition().getMyX()) +
                            Math.abs(hPos.getMyY() - target.getPosition().getMyY());
                    if (distToHero <= 1) {
                        target.enemyAttack(hero);
                    }
                }
            }
        }
        else {
            System.out.println("No enemy near to you");
        }
    }
}