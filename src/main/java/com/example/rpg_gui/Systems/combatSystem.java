package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;

public class combatSystem {
    public static void tryAttack(Hero hero, Map map) {
        Position hPos= hero.getPosition();
        Enemy target= null;
        for (Enemy e:map.getEnemies()) {
            if (!e.isAlive()) continue;
            Position ePos=e.getPosition();
            int dist=Math.abs(hPos.getMyX() - ePos.getMyX())+ Math.abs(hPos.getMyY() - ePos.getMyY());
            if (dist<=hero.getAttackDistance()) {
                target = e;
                break;
            }
        }
        if (target != null) {
            System.out.println("\nYour turn");
            hero.attack("Slash", 25, 5, target);
            if (target.isAlive()) {
                System.out.println("Enemy's turn");

                int distToHero = Math.abs(hPos.getMyX() - target.getPosition().getMyX()) +
                        Math.abs(hPos.getMyY() - target.getPosition().getMyY());
                if (distToHero <= 1) {
                    target.enemyAttack(hero);
                } else {
                    System.out.println(target.getName()+ " out of attack zone");
                }
            }
        }
        else {
            System.out.println("No enemy near to you");
        }
    }
}