package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;

public class academySystem {
    public static void teachSkills(Hero hero) {
        int level = hero.getLevel();
        switch (hero.getHero()) {
            case Warrior:
                if (level >= 1) {
                    hero.learnAttack("Slash", 1);
                    hero.learnAttack("Fire Sword", 1);
                }
                if (level >= 2) hero.learnAttack("Double Slash", 2);
                if (level >= 3) hero.learnAttack("Ice Sword", 3);
                if (level >= 4) hero.learnAttack("Earth Breaker", 4);
                if (level >= 5) hero.learnAttack("Poison Sword", 5);
                break;
            case Archer:
                if (level >= 1) {
                    hero.learnAttack("Arrow Shot", 1);
                    hero.learnAttack("Fire Arrow", 1);
                }
                if (level >= 2) hero.learnAttack("Double Shot", 2);
                if (level >= 3) hero.learnAttack("Ice Arrow", 3);
                if (level >= 4) hero.learnAttack("Rain of Arrows", 4);
                if (level >= 5) hero.learnAttack("Explosive Arrow", 5);
                break;
            case Mage:
                if (level >= 1) {
                    hero.learnAttack("Wind Attack", 1);
                    hero.learnAttack("Fireball", 1);
                }
                if (level >= 2) hero.learnAttack("Ice Shot", 2);
                if (level >= 3) hero.learnAttack("Meteor", 3);
                if (level >= 4) hero.learnAttack("Fire Storm", 4);
                if (level >= 5) hero.learnAttack("Curse", 5);
                break;
        }
    }
}
