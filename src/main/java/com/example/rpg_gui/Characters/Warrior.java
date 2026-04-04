package com.example.rpg_gui.Characters;

public class Warrior extends Hero {

    public Warrior() {
        super("Warrior", HeroType.Warrior);

        this.maxHealth = (int)(this.maxHealth *1.2);
        this.health = maxHealth;
        this.attackDistance = 1;
    }

    @Override
    public void useSelectedAttack(Enemy... enemies) {
        String skill = getSelectedAttackName();
        int damage = 0;
        int manaCost = 0;
        switch (skill) {
            case "Slash" -> { damage = 25; manaCost = 0; }
            case "Fire Sword" -> { damage = 30; manaCost = 15; }
            case "Ice Sword" -> { damage = 35; manaCost = 20; }
            case "Double Slash" -> { damage = 40; manaCost = 0; }
            case "Earth Breaker" -> { damage = 45; manaCost = 0; }
            case "Poison Sword" -> { damage = 50; manaCost = 25; }
            default -> { damage = 10; manaCost = 0; }
        }
        this.attack(skill, damage, manaCost, enemies);
    }
}