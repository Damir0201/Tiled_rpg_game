package com.example.rpg_gui.map.generators;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Items.Key;
import com.example.rpg_gui.map.*;

import java.util.List;
import java.util.Random;

public class DungeonGenerator implements MapGenerator{
    @Override
    public void generate(Tile[][] tiles, int width, int height, List<Enemy> enemies) {
        for (int y=0; y<height; y++) {
            for (int x=0;x<width; x++) {
                if(y==0 || y==height-1 || x==0 || x==width-1) {
                    tiles[y][x] = new Tile(TypeTile.Wall);
                }
                else {
                    tiles[y][x] = new Tile(TypeTile.Floor);
                }
            }
        }
        Random random=new Random();
        generateChests("Boss door", tiles, width, height);
        roomLocator(tiles, width, height, enemies);
        spawnEnemies(random, tiles, width, height, enemies);
        for (int i = 0; i < 5; i++) {
            generateTrap(random, tiles, width, height);
        }
        tiles[height - 2][width / 2] = new Tile(TypeTile.PortalTile);
    }
    public void generateChests(String bossKeyCode, Tile[][] tiles, int width, int height) {
        Random rand = new Random();
        boolean keyPlaced = false;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].getType() == TypeTile.Floor) {

                    // Шанс 5% что появится сундук
                    if (rand.nextInt(100) < 3) {

                        if (!keyPlaced) {
                            Key bossKey = new Key("Boss Key", bossKeyCode);
                            tiles[y][x] = new Chest(100, bossKey);
                            keyPlaced = true;
                        } else {
                            // Все остальные сундуки — просто с монетами (от 10 до 50)
                            int randomMoney = rand.nextInt(41) + 10;
                            tiles[y][x] = new Chest(randomMoney);
                        }
                    }
                }
            }
        }
    }
    public void generateTrap(Random random, Tile[][] tiles, int width, int height) {
        int y;
        int x;
        do {
            y= random.nextInt(height);
            x= random.nextInt(width);
        } while(tiles[y][x].getType()!=TypeTile.Floor);
        tiles[y][x]=new Trap(TypeTile.Trap);
    }
    public void roomLocator(Tile[][] tiles, int width, int height, List <Enemy> enemies) {
        int startY = 3;
        int startX = 4;
        int size = 4;

        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                // Строим стены по краям
                if (y == startY || y == startY + size - 1 || x == startX || x == startX + size - 1) {
                    // В одной из стен делаем дверь
                    if (y == startY + size - 1 && x == startX + size / 2) {
                        tiles[y][x] = new Door("Boss door"); // Код совпадает с ключом из сундука
                    } else {
                        tiles[y][x] = new Tile(TypeTile.Wall);
                    }
                } else {
                    tiles[y][x] = new Tile(TypeTile.Floor);
                }
            }
        }
        Enemy boss = Enemy.BossA;
        boss.setPosition(new Position(startX + 1, startY + 1));
        enemies.add(boss);
    }
    public void spawnEnemies(Random random,Tile[][] tiles, int width, int height, List <Enemy> enemies) {
        Enemy[] names = {Enemy.MonsterC, Enemy.MonsterD, Enemy.MonsterE,Enemy.MonsterF,Enemy.MonsterG,Enemy.MonsterK,Enemy.MonsterL};

        for (Enemy name : names) {
            int x, y;
            do {
                y = random.nextInt(height);
                x = random.nextInt(width);
            } while (tiles[y][x].getType() != TypeTile.Floor);
            name.setPosition(new Position(x, y));
            enemies.add(name);
        }
    }
}