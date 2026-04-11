package com.example.rpg_gui.Characters;

public class Mage extends Hero {
    public Mage() {
        super("Mage", HeroType.Archer);

        this.maxMana = (int) (this.maxMana * 1.2);
        this.mana = this.maxMana;

        this.attackDistance = 2;
    }

    @Override
    public void useSelectedAttack(Enemy... enemies) {
        String skill = getSelectedAttackName();
        int damage;
        int manaCost;
        switch (skill) {
            case "Wind Attack" -> { damage = 25; manaCost = 5; }
            case "Fireball" -> { damage = 30; manaCost = 7; }
            case "Ice Shot" -> { damage = 35; manaCost = 7; }
            case "Meteor" -> { damage = 40; manaCost = 9; }
            case "Fire Storm" -> { damage = 45; manaCost = 10; }
            case "Curse" -> { damage = 50; manaCost = 11; }
            default -> { damage = 10; manaCost = 3; }
        }
        this.attack(skill, damage, manaCost, enemies);
    }
}