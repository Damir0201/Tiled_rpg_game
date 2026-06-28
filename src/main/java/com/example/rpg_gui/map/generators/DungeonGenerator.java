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
        tiles[height-2][width/2] = new Portal();
        Random random=new Random();
        generateChests("Boss door", tiles, width, height);
        int room1X, room1Y;
        do {
            room1X=random.nextInt(width - 5 - 2) + 1;
            room1Y=random.nextInt(height - 5 - 2) + 1;
        } while (isOverlappingPortal(tiles, room1X, room1Y));
        roomLocator(tiles, room1X, room1Y, enemies, Enemy.BossA);
        if (diff == Difficulty.HARD) {
            int room2X, room2Y;
            do {
                room2X = random.nextInt(width - 5 - 2) + 1;
                room2Y = random.nextInt(height - 5 - 2) + 1;
            } while ((Math.abs(room2X - room1X) < 7 && Math.abs(room2Y - room1Y) < 7) || isOverlappingPortal(tiles, room2X, room2Y));

            roomLocator(tiles, room2X, room2Y, enemies, Enemy.BossB);
        }
        spawnEnemies(random, tiles, width, height, enemies, diff.monsterCount);
        for (int i = 0; i < 5; i++) {
            generateTrap(random, tiles, width, height);
        }
    }
    public void generateChests(String bossKeyCode, Tile[][] tiles, int width, int height) {
        Random rand = new Random();
        boolean keyPlaced = false;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].getType() == TypeTile.Floor) {
                    if (isSafeZone(tiles, x, y)) {
                        continue;
                    }
                    if (rand.nextInt(100) < 3) {
                        if (!keyPlaced) {
                            Key bossKey = new Key("Boss Key", bossKeyCode);
                            tiles[y][x] = new Chest(100, bossKey);
                            keyPlaced = true;
                        } else {
                            int randomMoney = rand.nextInt(21) + 10;
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
        } while(tiles[y][x].getType()!=TypeTile.Floor || isSafeZone(tiles, x, y));
        tiles[y][x]=new Trap();
    }
    public void roomLocator(Tile[][] tiles, int startX, int startY, List <Enemy> enemies, Enemy boss) {
        int size = 5;

        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                if (y == startY || y == startY + size - 1 || x == startX || x == startX + size - 1) {
                    if (y == startY + size - 1 && x == startX + size / 2) {
                        tiles[y][x] = new Door("Boss door");
                    } else {
                        tiles[y][x] = new Tile(TypeTile.Wall);
                    }
                } else {
                    tiles[y][x] = new Tile(TypeTile.BossRoomFloor);
                }
            }
        }
        Enemy uniqueBoss = boss.makeCopy(); // Полиморфное клонирование через интерфейс!
        uniqueBoss.setInitialPosition(startX + 2, startY + 2);
        enemies.add(uniqueBoss);
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
            } while (tiles[y][x].getType() != TypeTile.Floor || isSafeZone(tiles, x, y));

            Enemy uniqueMonster = allMonsters[i].makeCopy(); // Просим шаблон размножиться
            uniqueMonster.setInitialPosition(x, y);
            enemies.add(uniqueMonster);
        }
    }
    // 🌟 ИДЕАЛЬНОЕ ООП: Никаких instanceof, только полиморфные вызовы методов
    public boolean isSafeZone(Tile[][] tiles, int x, int y) {
        // 1. Стартовая зона игрока (1, 1)
        int spawnX = 1;
        int spawnY = 1;
        int safeRadius = 2;
        if (Math.abs(x - spawnX) + Math.abs(y - spawnY) <= safeRadius) {
            return true;
        }

        // 2. Проверяем саму клетку — она сама знает, безопасна ли она
        if (tiles[y][x].isSafeForGeneration()) {
            return true;
        }

        // 3. Защита клетки ПЕРЕД дверью (на шаг ниже двери)
        if (y > 0 && tiles[y - 1][x].isSafeForGeneration() && tiles[y - 1][x].getType() == TypeTile.DoorTile) {
            return true;
        }

        // 4. Защита креста вокруг портала
        if ((y > 0 && tiles[y - 1][x].isSafeForGeneration() && tiles[y - 1][x].getType() == TypeTile.PortalTile) ||
                (y < tiles.length - 1 && tiles[y + 1][x].isSafeForGeneration() && tiles[y + 1][x].getType() == TypeTile.PortalTile) ||
                (x > 0 && tiles[y][x - 1].isSafeForGeneration() && tiles[y][x - 1].getType() == TypeTile.PortalTile) ||
                (x < tiles[0].length - 1 && tiles[y][x + 1].isSafeForGeneration() && tiles[y][x + 1].getType() == TypeTile.PortalTile)) {
            return true;
        }

        return false;
    }

    // 🌟 ИДЕАЛЬНОЕ ООП: Проверка наложения комнат на портал без instanceof
    private boolean isOverlappingPortal(Tile[][] tiles, int startX, int startY) {
        int size = 5;
        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                // Если плитка защищена от генерации и это Портал
                if (tiles[y][x].isSafeForGeneration() && tiles[y][x].getType() == TypeTile.PortalTile) {
                    return true;
                }
            }
        }
        return false;
    }
}