package com.example.rpg_gui.Characters;

public class Warrior extends Hero {

    public Warrior() {
        super("Warrior", HeroType.Warrior);

        this.maxHealth = (int)(this.maxHealth *1.2);
        this.health = maxHealth;

        //this.attackDistance = 1;
    }

    public void slash (Enemy... enemies) {
        attack("Slash", 15, 0, enemies);
    }
    public void fireSword (Enemy... enemies) {
        attack("Fire Sword", 20, 25, enemies);
    }
    public void doubleSlash (Enemy... enemies) {
        attack("Double Slash", 25, 0, enemies);
    }
    public void iceSword (Enemy... enemies) {
        attack("Ice Sword", 30, 30, enemies);
    }
    public void earthBreaker (Enemy... enemies) {
        attack("Earth Breaker", 35, 0, enemies);
    }
    public void poisonSword (Enemy... enemies) {
        attack("Poison Sword", 40, 35, enemies);
    }
}