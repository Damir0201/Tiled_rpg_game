package com.example.rpg_gui.map.generators;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.map.*;

import java.util.List;

public class HubGenerator implements MapGenerator{

    @Override
    public void generate(Tile[][] tiles, int width, int height, List<Enemy> enemies, Difficulty diff) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    tiles[y][x] = new Tile(TypeTile.Wall);
                } else {
                    tiles[y][x] = new Tile(TypeTile.Grass);
                }
            }
        }
        tiles[2][2] = new Academy();
        tiles[2][width - 3] = new Shop();
        tiles[height - 2][width / 2] = new Portal();
        generateRiver(tiles, width, height);
    }
    public void generateRiver(Tile[][] tiles, int width, int height) {
        for(int y = 6; y<Math.min(8,height); y++ ) {
            for(int x = 4; x<Math.min(10,width); x++) {
                tiles[y][x] = new Tile(TypeTile.Water);
            }
        }
    }
}