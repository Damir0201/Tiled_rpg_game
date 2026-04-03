package com.example.rpg_gui.map.generators;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.map.Tile;

import java.util.List;

public interface MapGenerator {
    void generate(Tile[][] tiles, int width, int height, List<Enemy> enemies);
}
