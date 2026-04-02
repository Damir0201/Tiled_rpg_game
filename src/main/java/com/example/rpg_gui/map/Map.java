package com.example.rpg_gui.map;
import com.example.rpg_gui.Characters.Enemy;
import com.example.rpg_gui.Items.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private final int height;
    private final int width;
    private final Tile[][] tiles;
    private final List<Enemy> enemies = new ArrayList<>();

    public Map(int height, int width) {
        this.height=height;
        this.width=width;
        tiles = new Tile[height][width];
    }
    public void generateMap() {
        for (int y=0; y<this.height; y++) {
            for (int x=0;x<this.width; x++) {
                if(y==0 || y==height-1 || x==0 || x==width-1) {
                    tiles[y][x] = new Tile(TypeTile.Wall);
                }
                else {
                    tiles[y][x] = new Tile(TypeTile.Floor);
                }
            }
        }
        roomLocator();
        generateRiver();
        Random random=new Random();
        for(int i=0;i<6;i++) {
            generateTrap(random);
        }
        generateChests("Boss DOOR");

    }
    public void generateHub() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    tiles[y][x] = new Tile(TypeTile.Wall);
                } else {
                    tiles[y][x] = new Tile(TypeTile.Grass);
                }
            }
        }

        // Ставим клетку Академии (например, слева сверху)
        tiles[2][2] = new Tile(TypeTile.AcademyTile);

        // Ставим клетку Магазина (например, справа сверху)
        tiles[2][width - 3] = new Tile(TypeTile.ShopTile);

        // Выход в основной мир (дверь снизу)
        tiles[height - 1][width / 2] = new Door("TO_WORLD");
    }

    public void generateRiver() {
        for(int y = 10; y<Math.min(12,height); y++ ) {
            for(int x = 6; x<Math.min(18,width); x++) {
                tiles[y][x] = new Tile(TypeTile.Water);
            }
        }
    }
    public void generateChests(String bossKeyCode) {
        Random rand = new Random();
        boolean keyPlaced = false; // Чтобы ключ был только ОДИН

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Проверяем, что клетка — это пол (чтобы не ставить сундук в стену)
                if (tiles[y][x].getType() == TypeTile.Floor) {

                    // Шанс 5% что появится сундук
                    if (rand.nextInt(100) < 5) {

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
    public void generateTrap(Random random) {
        int y;
        int x;
        do {
            y= random.nextInt(height);
            x= random.nextInt(width);
        } while(tiles[y][x].getType()!=TypeTile.Floor);
        tiles[y][x]=new Trap(TypeTile.Trap);
    }
    public void roomLocator() {
        int startY = 3;
        int startX = 4;
        int size = 4;

        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                // Строим стены по краям
                if (y == startY || y == startY + size - 1 || x == startX || x == startX + size - 1) {
                    // В одной из стен делаем дверь
                    if (y == startY + size - 1 && x == startX + size / 2) {
                        tiles[y][x] = new Door("Boss DOOR"); // Код совпадает с ключом из сундука
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
    public void spawnEnemies(Random random) {
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
