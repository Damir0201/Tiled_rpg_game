package com.example.rpg_gui.map.generators;

import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Items.Key;
import com.example.rpg_gui.map.*;

import java.util.List;
import java.util.Random;

public class DungeonGenerator implements MapGenerator{
    @Override
    public void generate(Tile[][] tiles, int width, int height, List<Enemy> enemies, Difficulty diff) {
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
        roomLocator(tiles, width, height, enemies, Enemy.BossA);
        if(diff==Difficulty.HARD) {
            roomLocator(tiles, width, height, enemies, Enemy.BossB);
        }
        spawnEnemies(random, tiles, width, height, enemies, diff.monsterCount);
        for (int i = 0; i < 5; i++) {
            generateTrap(random, tiles, width, height);
        }
        tiles[height-2][width/2] = new Tile(TypeTile.PortalTile);
    }
    public void generateChests(String bossKeyCode, Tile[][] tiles, int width, int height) {
        Random rand = new Random();
        boolean keyPlaced = false;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].getType() == TypeTile.Floor) {
                    if (rand.nextInt(100) < 3) {
                        if (!keyPlaced) {
                            Key bossKey = new Key("Boss Key", bossKeyCode);
                            tiles[y][x] = new Chest(100, bossKey);
                            keyPlaced = true;
                        } else {
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
    public void roomLocator(Tile[][] tiles, int width, int height, List <Enemy> enemies, Enemy boss) {
        Random rand = new Random();
        int startY, startX;
        int size = 4;

        do{
            startX= rand.nextInt(width-size-2)+1;
            startY= rand.nextInt(height-size-2)+1;
        } while (tiles[startY][startX].getType()==TypeTile.Wall && startY>1);

        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                if (y == startY || y == startY + size - 1 || x == startX || x == startX + size - 1) {
                    if (y == startY + size - 1 && x == startX + size / 2) {
                        tiles[y][x] = new Door("Boss door");
                    } else {
                        tiles[y][x] = new Tile(TypeTile.Wall);
                    }
                } else {
                    tiles[y][x] = new Tile(TypeTile.Grass);
                }
            }
        }
        boss.restore();
        boss.setInitialPosition(startX+1,startY+1);
        enemies.add(boss);
    }
    public void spawnEnemies(Random random,Tile[][] tiles, int width, int height, List <Enemy> enemies, int limit) {
        Enemy[] allMonsters = {
                Enemy.MonsterC, Enemy.MonsterD, Enemy.MonsterE, Enemy.MonsterF,
                Enemy.MonsterG, Enemy.MonsterH, Enemy.MonsterI, Enemy.MonsterJ,
                Enemy.MonsterK, Enemy.MonsterL
        };
        for (int i = 0; i < limit && i < allMonsters.length; i++) {
            int x, y;
            do {
                y = random.nextInt(height);
                x = random.nextInt(width);
            } while (tiles[y][x].getType() != TypeTile.Floor);

            Enemy name = allMonsters[i];
            name.restore();
            name.setInitialPosition(x, y);
            enemies.add(name);
        }
    }
}