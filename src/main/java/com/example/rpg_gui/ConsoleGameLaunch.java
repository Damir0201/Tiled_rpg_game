package com.example.rpg_gui;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Characters.Warrior;
import com.example.rpg_gui.core.ConsoleEngine;
import com.example.rpg_gui.map.generators.Difficulty;

public class ConsoleGameLaunch {
    public static void main(String[] args) {
        System.out.println("Running game");

        Hero conan = new Warrior();

        ConsoleEngine engine = new ConsoleEngine();

        engine.initGame(conan, Difficulty.NORMAL);
    }
}
