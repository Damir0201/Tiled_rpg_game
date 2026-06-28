package com.example.rpg_gui.Characters;
import com.example.rpg_gui.map.Position;

public abstract class Character implements MovementForAll {
    protected String name;
    protected int health;
    protected int maxHealth=100;
    protected Position position;

    public Character(String name) {
        this.name = name;
        this.health = maxHealth;
    }
    @Override
    public void moveTo(int newX, int newY) {
        this.position.setMyX(newX);
        this.position.setMyY(newY);
    }

    public boolean isAlive() {
        return health >0;
    }

    public abstract void takeDamage(int damage);
    public int getHealth() {
        return health;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public String getName() { return name; }
}