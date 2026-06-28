package com.example.rpg_gui;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Characters.Warrior;
import com.example.rpg_gui.core.ConsoleEngine;
import com.example.rpg_gui.map.generators.Difficulty;

public class ConsoleGameLaunch {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК КОНСОЛЬНОЙ RPG ===");

        // 1. Создаем твоего героя (передай нужные параметры в конструктор твоего класса Hero)
        // Например: Имя, Здоровье, Атака (подставь свои актуальные параметры)
        Hero conan = new Warrior();

        // 2. Создаем экземпляр консольного движка
        ConsoleEngine engine = new ConsoleEngine();

        // 3. Запускаем игру!
        // Передаем героя, имя скина (для консоли это просто заглушка) и сложность
        engine.initGame(conan, Difficulty.NORMAL);
    }
}
