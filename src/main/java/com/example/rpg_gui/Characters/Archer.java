package com.example.rpg_gui.Characters;

public class Archer extends Hero{

    public Archer() {
        super("Archer", HeroType.Archer);

        //this.attackDistance = 4;
    }
    public void arrowShot (Enemy... enemies) {
        attack("Arrow Shot", 15, 0, enemies);
    }
    public void fireArrow (Enemy...enemies) {
        attack("Fire Arrow", 20, 25, enemies);
    }
    public void doubleShot (Enemy...enemies) {
        attack("Double Shot", 25, 0, enemies);
    }
    public void iceArrow (Enemy...enemies) {
        attack("Ice Arrow", 30, 30, enemies);
    }
    public void rainOfArrows (Enemy...enemies) {
        attack("Rain of Arrows", 35, 0, enemies);
    }
    public void explosiveArrow (Enemy...enemies) {
        attack("Explosive Arrow", 40, 35, enemies);
    }
}