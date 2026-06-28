package com.example.rpg_gui.map;
import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.map.generators.Difficulty;
import com.example.rpg_gui.map.generators.MapGenerator;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final int height;
    private final int width;
    private final Tile[][] tiles;
    private final List<Enemy> enemies = new ArrayList<>();

    public Map(int height, int width, MapGenerator generator, Difficulty diff) {
        this.height=height;
        this.width=width;
        tiles = new Tile[height][width];
        generator.generate(this.tiles, this.width, this.height, this.enemies, diff);
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public int aliveEnemies() {
        int count = 0;
        for (Enemy e : getEnemies()) {
            if (e.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public boolean possibleMove(int x, int y) {
        if(y<0||y>=height||x<0||x>=width) {
            return false;
        }
        if (!tiles[y][x].isAllowingMove()) {
            return false;
        }
        for (Enemy e : enemies) {
            if (e.isAlive() && e.getPosition().getMyX() == x && e.getPosition().getMyY() == y) {
                return false;
            }
        }

        return true;
    }
}
