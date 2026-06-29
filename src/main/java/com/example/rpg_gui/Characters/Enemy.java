package com.example.rpg_gui.Characters;

import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;

public class Enemy extends Character implements Copyable{
    @Override
    public Enemy[] makeCopy(int amount) {
        Enemy[] copies = new Enemy[amount];

        for (int i = 0; i < amount; i++) {
            copies[i] = new Enemy(this);
        }

        return copies;
    }

    public enum enemyType {DungeonBoss, Monster}
    private final enemyType typeOfEnemy;
    private final int attackPower;
    private final int detectionRange;
    private final int disableChasingRange;
    private Position spawnPosition;

    public enemyType getEnemyType() {
        return typeOfEnemy;
    }
    public Enemy(String name, enemyType typeOfEnemy, int maxHealth, int attackPower, int detect, int lose) {
        super(name);
        this.typeOfEnemy = typeOfEnemy;
        this.attackPower = attackPower;
        this.detectionRange = detect;
        this.disableChasingRange = lose;
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
    }

    public Enemy(Enemy template) {
        super(template.getName());
        this.typeOfEnemy = template.typeOfEnemy;
        this.attackPower = template.attackPower;
        this.detectionRange = template.detectionRange;
        this.disableChasingRange = template.disableChasingRange;
        setMaxHealth(template.getMaxHealth());
        setHealth(template.getMaxHealth());

        if (template.spawnPosition != null) {
            this.spawnPosition = new Position(template.spawnPosition.getMyX(), template.spawnPosition.getMyY());
        }
    }

    public void setInitialPosition(int x, int y) {
        setPosition(new Position(x, y));
        this.spawnPosition = new Position(x, y);
    }

    public void enemyAttack(Hero hero) {
        if (!this.isAlive() || !hero.isAlive()) return;
        System.out.println(getName() + " is attacking:");
        hero.takeDamage(this.attackPower);
    }

    @Override
    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage can not be negative!");
        setHealth(getHealth() - damage);

        if (!isAlive()) {
            System.out.println(getName() + " died!");
        } else {
            System.out.println(getName() + " took " + damage + " damage!");
            System.out.println(getName() + " health: " + getHealth());
        }
    }
    public void restore() {
        setHealth(getMaxHealth());
    }
    public void chasingPlayer(Position hpos, Map currentMap) {
        if (!this.isAlive()) return;

        int distToHero = getPosition().getChebyshevDistance(hpos);
        int targetX, targetY;

        if (distToHero <= detectionRange) {
            targetX = hpos.getMyX();
            targetY = hpos.getMyY();
        } else if (distToHero > disableChasingRange) {
            targetX = spawnPosition.getMyX();
            targetY = spawnPosition.getMyY();
            if (getPosition().getMyX() == targetX && getPosition().getMyY() == targetY) return;
        } else {
            return;
        }

        int enemyPosX = getPosition().getMyX();
        int enemyPosY = getPosition().getMyY();
        if (Math.abs(enemyPosX - targetX) >= Math.abs(enemyPosY - targetY)) {
            if (enemyPosX < targetX) enemyPosX++;
            else if (enemyPosX > targetX) enemyPosX--;
        } else {
            if (enemyPosY < targetY) enemyPosY++;
            else if (enemyPosY > targetY) enemyPosY--;
        }
        if (enemyPosX == hpos.getMyX() && enemyPosY == hpos.getMyY()) {
            System.out.println(getName() + " is close to you");
            return;
        }
        if (currentMap.possibleMove(enemyPosX, enemyPosY)) {
            this.moveTo(enemyPosX, enemyPosY);
        }
    }

    public static final Enemy Boss = new Enemy ("Boss of Dungeon", enemyType.DungeonBoss, 200, 40, 2, 6);
    public static final Enemy Monster = new Enemy ("Monster", enemyType.Monster, 60, 20, 3, 6);
}