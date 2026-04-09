package com.example.rpg_gui.Characters;

public class Archer extends Hero{

    public Archer() {
        super("Archer", HeroType.Archer);

        this.attackDistance = 3;
    }

    @Override
    public void useSelectedAttack(Enemy... enemies) {
        String skill = getSelectedAttackName();
        int damage = 0;
        int manaCost = 0;
        switch (skill) {
            case "Arrow Shot" -> { damage = 25; manaCost = 0; }
            case "Fire Arrow" -> { damage = 30; manaCost = 15; }
            case "Double Shot" -> { damage = 35; manaCost = 0; }
            case "Ice Arrow" -> { damage = 40; manaCost = 20; }
            case "Rain of Arrows" -> { damage = 45; manaCost = 0; }
            case "Explosive Arrow" -> { damage = 50; manaCost = 25; }
            default -> { damage = 10; manaCost = 3; }
        }
        this.attack(skill, damage, manaCost, enemies);
    }
}