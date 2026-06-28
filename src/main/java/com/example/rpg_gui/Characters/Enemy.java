package com.example.rpg_gui.Characters;

import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;

public class Enemy extends Character implements Copyable{
    @Override
    public Enemy makeCopy() {
        return new Enemy(this);
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
    public Enemy (String name, enemyType typeOfEnemy, int maxHealth, int attackPower, int detect, int lose) {
        super(name);
        this.typeOfEnemy = typeOfEnemy;
        this.attackPower = attackPower;
        this.detectionRange = detect;
        this.disableChasingRange = lose;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    // 2. Конструктор копирования: берет шаблон и создает его точную уникальную копию
    public Enemy(Enemy template) {
        super(template.name);
        this.typeOfEnemy = template.typeOfEnemy;
        this.attackPower = template.attackPower;
        this.detectionRange = template.detectionRange;
        this.disableChasingRange = template.disableChasingRange;
        this.maxHealth = template.maxHealth;
        this.health = template.maxHealth; // Новый монстр рождается полностью здоровым
        if (template.spawnPosition != null) {
            // Создаем абсолютно новый объект позиции, чтобы у каждого клона была своя точка
            this.spawnPosition = new Position(template.spawnPosition.getMyX(), template.spawnPosition.getMyY());
        } else {
            this.spawnPosition = null;
        }
    }

    public void setInitialPosition(int x, int y){
        this.position=new Position(x,y);
        this.spawnPosition=new Position(x,y);
    }

    public void enemyAttack (Hero hero) {
        if (!this.isAlive() || !hero.isAlive()) return;
        System.out.println(this.name + " is attacking:");
        hero.takeDamage(this.attackPower);
    }

    @Override
    public void takeDamage(int damage){
        if (damage < 0) {
            throw new IllegalArgumentException("Damage can not be negative!");
        }
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            System.out.println(this.name + " died!");
        } else {
            System.out.println(this.name + " took " + damage + " damage!");
            System.out.println(this.name + " health: " + this.health);
        }
    }
    public void restore() {
        this.health = this.maxHealth;
    }
    public void chasingPlayer(Position hpos, Map currentMap) {
        if(!this.isAlive()) return;
        int distToHero = this.position.getChebyshevDistance(hpos);
        int targetX, targetY;

        if (distToHero <= detectionRange) {
            targetX = hpos.getMyX();
            targetY = hpos.getMyY();
        } else if (distToHero > disableChasingRange) {
            targetX = spawnPosition.getMyX();
            targetY = spawnPosition.getMyY();
            if (this.position.getMyX() == targetX && this.position.getMyY() == targetY) return;
        } else {
            return;
        }

        int enemyPosX = this.position.getMyX();
        int enemyPosY = this.position.getMyY();
        int nextX = enemyPosX;
        int nextY = enemyPosY;

        if (Math.abs(enemyPosX - targetX) >= Math.abs(enemyPosY - targetY)) {
            if (enemyPosX < targetX) nextX++;
            else if (enemyPosX > targetX) nextX--;
        } else {
            if (enemyPosY < targetY) nextY++;
            else if (enemyPosY > targetY) nextY--;
        }

        if (nextX == hpos.getMyX() && nextY == hpos.getMyY()) {
            System.out.println(this.name + " is close to you!");
        } else if (currentMap.possibleMove(nextX, nextY)) {
            this.moveTo(nextX, nextY);
        }
    }

    public static final Enemy BossA = new Enemy ("Boss A", enemyType.DungeonBoss, 200, 30, 2, 6);
    public static final Enemy BossB = new Enemy ("Boss B", enemyType.DungeonBoss, 150, 20, 2, 6);
    public static final Enemy MonsterC = new Enemy ("Monster C", enemyType.Monster, 30, 15, 3, 6);
    public static final Enemy MonsterD = new Enemy ("Monster D", enemyType.Monster, 30, 15, 3, 6);
    public static final Enemy MonsterE = new Enemy ("Monster E", enemyType.Monster, 30, 15, 3, 6);
    public static final Enemy MonsterF = new Enemy ("Monster F", enemyType.Monster, 25, 10, 3, 6);
    public static final Enemy MonsterG = new Enemy ("Monster G", enemyType.Monster, 25, 10, 3, 6);
    public static final Enemy MonsterH = new Enemy ("Monster H", enemyType.Monster, 25, 10, 3, 6);
    public static final Enemy MonsterI = new Enemy ("Monster I", enemyType.Monster, 20, 10, 3, 6);
    public static final Enemy MonsterJ = new Enemy ("Monster J", enemyType.Monster, 20, 10, 3, 6);
    public static final Enemy MonsterK = new Enemy ("Monster K", enemyType.Monster, 15, 5, 3, 6);
    public static final Enemy MonsterL = new Enemy ("Monster L", enemyType.Monster, 15, 5, 3, 6);
}