package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameEngine;
import com.example.rpg_gui.core.GameManager;
import com.example.rpg_gui.core.UImanager;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;

public class movementSystem {
    public static void move(Hero hero, Map map, int moveX, int moveY, GameEngine engine, UImanager uImanager) {
        Position position = hero.getPosition();
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
            interactionSystem.checkStepOnTile(hero,map,engine,uImanager);
        }
    }
}