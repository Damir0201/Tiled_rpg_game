package com.example.rpg_gui.Characters;

import com.example.rpg_gui.map.Position;

public interface MovementForAll {
    void moveTo(int newX, int newY);

    Position getCurrentPosition();
}