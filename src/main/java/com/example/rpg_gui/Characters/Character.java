package com.example.rpg_gui.Characters;
import com.example.rpg_gui.map.Position;

public abstract class Character {
    protected String name;
    protected int health;
    protected int maxHealth=100;
    protected Position position; // Добавляем сюда

    public Character(String name) {
        this.name = name;
        this.health = maxHealth;
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