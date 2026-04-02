package com.example.rpg_gui.Characters;

public class Mage extends Hero {
    public Mage() {
        super("Mage", HeroType.Mage);

        this.maxMana = (int) (this.maxMana * 1.2);
        this.health = this.maxHealth;

        //this.attackDistance = 3;
    }

    public void windAttack(Enemy... enemies) {
        attack("Wind Attack", 15, 5, enemies);
    }

    public void fireball(Enemy... enemies) {
        attack("Fireball", 40, 7, enemies);
    }

    public void IceShot(Enemy... enemies) {
        attack("Ice Shot", 25, 9, enemies);
    }

    public void Meteor(Enemy... enemies) {
        attack("Meteor", 30, 11, enemies);
    }

    public void fireStorm(Enemy... enemies) {
        attack("Fire Storm", 35, 13, enemies);
    }

    public void curse(Enemy... enemies) {
        attack("Curse", 40, 15, enemies);
    }
}