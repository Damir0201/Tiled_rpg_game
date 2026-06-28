package com.example.rpg_gui.Characters;

import com.example.rpg_gui.map.Position;

public abstract class Character implements MovementForAll {
    // 1. Поля теперь private — никто «снаружи» не сломает их напрямую
    private String name;
    private int health;
    private int maxHealth = 100;
    private Position position;

    public Character(String name) {
        this.name = name;
        this.health = maxHealth;
    }

    @Override
    public void moveTo(int newX, int newY) {
        // Проверяем, существует ли объект позиции, чтобы не было ошибки
        if (this.position != null) {
            this.position.setMyX(newX);
            this.position.setMyY(newY);
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    // 2. Метод для безопасного изменения здоровья (с валидацией)
    public void setHealth(int health) {
        if (health < 0) {
            this.health = 0;
        } else if (health > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
    }

    // 3. Метод для изменения максимального здоровья
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        // Если новое макс. здоровье меньше текущего, корректируем здоровье
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public abstract void takeDamage(int damage);

    // 4. Геттеры для получения данных
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public String getName() { return name; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}