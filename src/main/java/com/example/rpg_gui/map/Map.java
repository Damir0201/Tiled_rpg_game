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
    public int aliveEnemies () {
        return getEnemies().stream().filter(e -> e.isAlive()).toList().size();
    }

    public void printMap(Position playerPos) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (playerPos.getMyX() == x && playerPos.getMyY() == y) {
                    System.out.print("P ");
                    continue;
                }
                Enemy foundEnemy = null;
                for (Enemy enemy : enemies) {
                    if (enemy.isAlive() && enemy.getPosition().getMyX() == x && enemy.getPosition().getMyY() == y) {
                        foundEnemy = enemy;
                        break;
                    }
                }
                if (foundEnemy != null) {
                    String symbol = (foundEnemy.getEnemyType() == Enemy.enemyType.DungeonBoss) ? "B " : "E ";
                    System.out.print(symbol);
                } else {
                    System.out.print(tiles[y][x].getType().getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }
    public boolean possibleMove(int x, int y) {
        if(y<0||y>=height||x<0||x>=width) {
            return false;
        }
        return tiles[y][x].isAllowingMove();
    }
}
