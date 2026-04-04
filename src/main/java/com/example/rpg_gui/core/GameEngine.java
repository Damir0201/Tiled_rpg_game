package com.example.rpg_gui.core;

import com.example.rpg_gui.Characters.Hero.HeroType;
import com.example.rpg_gui.Systems.AcademySystem;
import com.example.rpg_gui.Systems.interactionSystem;
import com.example.rpg_gui.map.generators.DungeonGenerator;
import com.example.rpg_gui.map.generators.HubGenerator;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import com.example.rpg_gui.Systems.combatSystem;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;
import com.example.rpg_gui.map.TypeTile;
import com.example.rpg_gui.Systems.movementSystem;
import com.example.rpg_gui.Characters.Hero;
import java.util.Objects;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class GameEngine {
    private Pane root;
    private Pane uiPane;
    private Map map;
    private Map hubMap;
    private Map dungeonMap;
    private Hero myHero;
    private final int Tile_Size = 32;

    private UImanager uImanager = new UImanager();
    private ImagePattern grassPattern;
    private ImagePattern riverPattern;
    private ImagePattern wallPattern;
    private ImagePattern heroPattern;
    private ImagePattern trapPattern;
    private ImagePattern orcPattern;
    private ImagePattern floorPattern;
    private ImagePattern chestPattern;
    private ImagePattern doorPattern;
    private ImagePattern academyPattern;
    private ImagePattern shopPattern;
    private ImagePattern portalPattern;



    public GameEngine(Pane root, Pane uiPane) {
        this.root = root;
        this.uiPane=uiPane;
        loadResources();
    }

    private void loadResources() {
        // Загружаем всё один раз в конструкторе
        grassPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Grass.png"))));
        wallPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Wall.png"))));
        riverPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/River.png"))));
        trapPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Trap.png"))));
        heroPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/ArmoredW.png"))));
        orcPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Orc.png"))));
        floorPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Floor.png"))));
        chestPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Chest.png"))));
        doorPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Door.png"))));
        academyPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Academy.png"))));
        shopPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Shop.png"))));
        portalPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Portal.png"))));
    }

    public void initGame() {
        this.hubMap = new Map(10, 15, new HubGenerator());
        this.dungeonMap = new Map(20, 25, new DungeonGenerator());
        this.map = hubMap;
        this.myHero = new Hero("Damira", HeroType.Warrior);
        this.myHero.learnAttack("Slash", 1);
        this.myHero.setPosition(new Position(5, 5));
        GameManager.getInstance().setPlayer(myHero);
        AcademySystem.teachSkills(myHero);
        render();
    }

    public void switchMap() {
        if (this.map == hubMap) {
            this.map = dungeonMap;
            myHero.setPosition(new Position(1, 1)); // Точка входа в данж
            System.out.println("you entered to dungeon");
        } else {
            this.map = hubMap;
            myHero.setPosition(new Position(1, 1)); // Возвращаемся к двери в Хабе
            System.out.println("you returned to hub");
        }
    }

    public void handleInput(KeyCode code) {
        switch (code) {
            case W -> movementSystem.move(myHero.getPosition(), map, 0, -1);
            case S -> movementSystem.move(myHero.getPosition(), map, 0, 1);
            case A -> movementSystem.move(myHero.getPosition(), map, -1, 0);
            case D -> movementSystem.move(myHero.getPosition(), map, 1, 0);
            case F -> combatSystem.tryAttack(myHero, map);
            case E ->interactionSystem.interact(myHero, map, this);
            case Q ->{myHero.switchAttack();System.out.println("Chosen attack is: "+myHero.getSelectedAttackName());}
        }
        render();
    }

    public void render(){
        root.getChildren().clear();
        renderWorld();
        uiPane.getChildren().clear();
        uImanager.drawHUD(uiPane, myHero, map);
    }
    public void renderWorld() {

        // 1. РИСУЕМ КАРТУ
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Rectangle rect = new Rectangle(x * Tile_Size, y * Tile_Size, Tile_Size, Tile_Size);
                TypeTile type = map.getTiles()[y][x].getType();

                if (type == TypeTile.Wall) rect.setFill(wallPattern);
                else if (type == TypeTile.Water) rect.setFill(riverPattern);
                else if (type == TypeTile.Trap) rect.setFill(trapPattern);
                else if (type==TypeTile.ChestTile) rect.setFill(chestPattern);
                else if (type==TypeTile.DoorTile) rect.setFill(doorPattern);
                else if (type==TypeTile.AcademyTile) rect.setFill(academyPattern);
                else if (type==TypeTile.ShopTile) rect.setFill(shopPattern);
                else if (type==TypeTile.Grass) rect.setFill(grassPattern);
                else if (type==TypeTile.PortalTile) rect.setFill(portalPattern);
                else rect.setFill(floorPattern);

                root.getChildren().add(rect);
            }
        }
        for (var enemy : map.getEnemies()) {
            if (enemy.isAlive()) {
                Rectangle enemyRect = new Rectangle(enemy.getPosition().getMyX() * Tile_Size, enemy.getPosition().getMyY() * Tile_Size, Tile_Size, Tile_Size);
                enemyRect.setFill(orcPattern);
                root.getChildren().add(enemyRect);
            }
        }
        Rectangle heroRect = new Rectangle(myHero.getPosition().getMyX() * Tile_Size, myHero.getPosition().getMyY() * Tile_Size, Tile_Size, Tile_Size);
        heroRect.setFill(heroPattern);
        root.getChildren().add(heroRect);
    }
}