package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameManager;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;

public class movementSystem {
    public static void move(Position position, Map map, int moveX, int moveY) {
        int newX= position.getMyX()+moveX;
        int newY= position.getMyY()+moveY;
        Enemy targetEnemy = null;
        for (Enemy enemy : map.getEnemies()) {
            if (enemy.isAlive() && enemy.getPosition().getMyX()== newX && enemy.getPosition().getMyY()== newY) {
                targetEnemy = enemy;
                break;
            }
        }
        if (targetEnemy != null) {
            System.out.println("Enemy is near to you");
            return;
        }
        if (map.possibleMove(newX, newY)) {
            position.setMyX(newX);
            position.setMyY(newY);
            trapSystem.checkTrap(position, map);
        }
    }
}