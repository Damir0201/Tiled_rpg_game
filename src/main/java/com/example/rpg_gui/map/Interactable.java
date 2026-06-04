package com.example.rpg_gui.map;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.core.GameEngine;
import com.example.rpg_gui.core.UImanager;

public interface Interactable {
    void interact(Hero hero, Map map, GameEngine engine, UImanager uiManager);
    boolean IsReactedOnStep();
    boolean isActive();
}
