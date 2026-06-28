package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;

import java.util.ArrayList;

public class CombatSystem {
    public static void tryAttack(Hero hero, Map map) {
        Position hPos= hero.getPosition();
        ArrayList<Enemy> targets = new ArrayList<>();

        for (Enemy e:map.getEnemies()) {
            if (!e.isAlive()) continue;
            if (hPos.getManhattanDistance(e.getPosition()) <= hero.getAttackDistance()) {
                targets.add(e);
            }
        }

        if (!targets.isEmpty()) {
            System.out.println("\nYour turn");
            hero.useSelectedAttack(targets.toArray(new Enemy[0]));

            for (Enemy target : targets) {
                if (target.isAlive()) {
                    System.out.println(target.getName() + "'s turn");

                    if (hPos.getManhattanDistance(target.getPosition()) <= 1) {
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