package com.example.rpg_gui.core;

import com.example.rpg_gui.Systems.AcademySystem;
import com.example.rpg_gui.Systems.InteractionSystem;
import com.example.rpg_gui.map.generators.Difficulty;
import com.example.rpg_gui.map.generators.DungeonGenerator;
import com.example.rpg_gui.map.generators.HubGenerator;
import com.example.rpg_gui.Systems.CombatSystem;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;
import com.example.rpg_gui.map.TypeTile;
import com.example.rpg_gui.Systems.MovementSystem;
import com.example.rpg_gui.Characters.Hero;

import java.util.Scanner;

public class ConsoleEngine implements Engine{
    private Map map;
    private Map hubMap;
    private Map dungeonMap;
    private Hero myHero;
    private final GameState gameState = new GameState();
    private Difficulty selectedDifficulty;
    private final UImanager uImanager = new UImanager();

    private boolean isRunning = true;

    public ConsoleEngine() {
    }

    public void initGame(Hero chosenHero, Difficulty diff) {
        this.selectedDifficulty = diff;
        this.gameState.reset();

        this.hubMap = new Map(10, 15, new HubGenerator(), diff);
        this.dungeonMap = new Map(20, 25, new DungeonGenerator(), diff);
        this.map = hubMap;

        this.myHero = chosenHero;
        this.myHero.setPosition(new Position(5, 5));

        GameManager.getInstance().setPlayer(myHero);
        AcademySystem.teachSkills(myHero);

        startConsoleLoop();
    }

    private void startConsoleLoop() {
        Scanner scanner = new Scanner(System.in);
        render();

        while (isRunning) {
            String input = scanner.nextLine().trim().toUpperCase();

            if ("EXIT".equals(input)) {
                isRunning = false;
                System.out.println("Goodbye");
                break;
            }

            handleConsoleInput(input);
        }
        scanner.close();
    }

    public void switchMap() {
        if (this.map == hubMap) {
            this.map = dungeonMap;
            myHero.setPosition(new Position(1, 1));
            System.out.println("You entered to Dungeon");
        } else {
            this.map = hubMap;
            myHero.setPosition(new Position(1, 1));
            System.out.println("You are in hub");
        }
    }

    public void handleConsoleInput(String input) {
        if (gameState.getCurrentState() != GameState.State.PLAYING) {
            System.out.println("Action is unavailable. Game is over or paused.");
            return;
        }

        switch (input) {
            case "W" -> MovementSystem.move(myHero, map, 0, -1, this, uImanager);
            case "S" -> MovementSystem.move(myHero, map, 0, 1, this, uImanager);
            case "A" -> MovementSystem.move(myHero, map, -1, 0, this, uImanager);
            case "D" -> MovementSystem.move(myHero, map, 1, 0, this, uImanager);
            case "F" -> CombatSystem.tryAttack(myHero, map);
            case "E" -> {
                int hX = myHero.getPosition().getMyX();
                int hY = myHero.getPosition().getMyY();
                boolean isNearShop = false;

                for (int y = 0; y < map.getHeight(); y++) {
                    for (int x = 0; x < map.getWidth(); x++) {
                        if (map.getTiles()[y][x].getType() == TypeTile.ShopTile) {
                            if (Math.abs(hX - x) + Math.abs(hY - y) <= 1) {
                                isNearShop = true;
                                break;
                            }
                        }
                    }
                    if (isNearShop) break;
                }

                if (isNearShop) {
                    System.out.println("\n Shop entered");
                    System.out.println("   You have " + myHero.getMoney() + " gold");
                    System.out.println("Shop in gui version");

                }
                else {
                    InteractionSystem.interact(myHero, map, this, uImanager);
                }
            }
            case "Q" -> {
                myHero.switchAttack();
                System.out.println("Chosen skill " + myHero.getSelectedAttackName());
            }
            case "X" -> {
                var currentItems = myHero.getInventory().getItems();
                if(currentItems.isEmpty()){
                    System.out.println("Nothing in your inventory");
                }
                else {
                    for(int i = 0; i < currentItems.size(); i++) {
                        System.out.println("Your item: " + currentItems.get(i).getItemName());
                    }
                }
            }
            default -> {
                System.out.println("Wrong command");
                return;
            }
        }

        for (var enemy : map.getEnemies()) {
            if (enemy.isAlive()) {
                enemy.chasingPlayer(myHero.getPosition(), map);
            }
        }

        render();
    }

    @Override
    public void render() {
        if (myHero != null && !myHero.isAlive()) {
            System.out.println("\nGame lose");
            this.isRunning = false;
            return;
        }
        if (map == dungeonMap && map.aliveEnemies() == 0) {
            System.out.println("\n WIN! ");
            this.isRunning = false;
            return;
        }

        gameState.update(myHero, map, dungeonMap, uImanager, null);

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(" Gamer: " + myHero.getName() + " | HP: " + myHero.getHealth());
        System.out.println(" Difficulty: " + selectedDifficulty + " (" + selectedDifficulty.monsterCount + " Monsters");
        System.out.println(" Your attack: " + myHero.getSelectedAttackName());

        renderWorld();
    }

    public void renderWorld() {
        int height = map.getHeight();
        int width = map.getWidth();

        char[][] grid = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TypeTile type = map.getTiles()[y][x].getType();
                grid[y][x] = type.getSymbol();
            }
        }

        for (var enemy : map.getEnemies()) {
            if (enemy.isAlive()) {
                int eX = enemy.getPosition().getMyX();
                int eY = enemy.getPosition().getMyY();
                if (isValidCoordinate(eX, eY, width, height)) {
                    grid[eY][eX] = 'E';
                }
            }
        }

        int hx = myHero.getPosition().getMyX();
        int hy = myHero.getPosition().getMyY();
        if (isValidCoordinate(hx, hy, width, height)) {
            grid[hy][hx] = 'H';
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
    }

    private boolean isValidCoordinate(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}