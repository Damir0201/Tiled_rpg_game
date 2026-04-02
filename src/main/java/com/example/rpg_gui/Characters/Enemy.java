package com.example.rpg_gui.Characters;

public class Enemy extends Character {
    public enum enemyType {DungeonBoss, Monster}
    private final enemyType typeOfEnemy;
    private int attackPower;
    public enemyType getEnemyType() {
        return typeOfEnemy;
    }
    public Enemy (String name, enemyType typeOfEnemy, int attackPower) {
        super (name);
        this.typeOfEnemy = typeOfEnemy;
        this.attackPower = attackPower;
        if (this.typeOfEnemy == enemyType.DungeonBoss) {
            this.maxHealth = 200;
            this.health = this.maxHealth;
        }
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


    public static final Enemy BossA = new Enemy ("Boss A", enemyType.DungeonBoss, 30);
    public static final Enemy BossB = new Enemy ("Boss B", enemyType.DungeonBoss, 20);
    public static final Enemy MonsterC = new Enemy ("Monster C", enemyType.Monster, 15);
    public static final Enemy MonsterD = new Enemy ("Monster D", enemyType.Monster, 15);
    public static final Enemy MonsterE = new Enemy ("Monster E", enemyType.Monster, 15);
    public static final Enemy MonsterF = new Enemy ("Monster F", enemyType.Monster, 10);
    public static final Enemy MonsterG = new Enemy ("Monster G", enemyType.Monster, 10);
    public static final Enemy MonsterH = new Enemy ("Monster H", enemyType.Monster, 10);
    public static final Enemy MonsterI = new Enemy ("Monster I", enemyType.Monster, 10);
    public static final Enemy MonsterJ = new Enemy ("Monster J", enemyType.Monster, 10);
    public static final Enemy MonsterK = new Enemy ("Monster K", enemyType.Monster, 5);
    public static final Enemy MonsterL = new Enemy ("Monster L", enemyType.Monster, 5);
}