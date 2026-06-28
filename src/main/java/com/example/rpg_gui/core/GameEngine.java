package com.example.rpg_gui.core;

import com.example.rpg_gui.Systems.AcademySystem;
import com.example.rpg_gui.Systems.InteractionSystem;
import com.example.rpg_gui.map.generators.Difficulty;
import com.example.rpg_gui.map.generators.DungeonGenerator;
import com.example.rpg_gui.map.generators.HubGenerator;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import com.example.rpg_gui.Systems.CombatSystem;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.map.Position;
import com.example.rpg_gui.map.TypeTile;
import com.example.rpg_gui.Systems.MovementSystem;
import com.example.rpg_gui.Characters.Hero;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class GameEngine implements Engine{
    private final Pane root;
    private final Pane uiPane;
    private Map map;
    private Map hubMap;
    private Map dungeonMap;
    private Hero myHero;
    private final GameState gameState = new GameState();
    private Difficulty selectedDifficulty;

    private final UImanager uImanager = new UImanager();
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
    private ImagePattern bossFloorPattern;



    public GameEngine(Pane root, Pane uiPane) {
        this.root = root;
        this.uiPane = uiPane;
        loadResources();
    }

    private void loadResources() {
        grassPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Grass.png"))));
        wallPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Wall.png"))));
        riverPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/River.png"))));
        trapPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Trap.png"))));
        orcPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Orc.png"))));
        floorPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Floor.png"))));
        chestPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Chest.png"))));
        doorPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Door.png"))));
        academyPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Academy.png"))));
        shopPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Shop.png"))));
        portalPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/Portal.png"))));
        bossFloorPattern = new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/rpg_gui/images/BossRoomFloor.png"))));
    }

    public void initGame(Hero chosenHero, String skinFileName, Difficulty diff) {
        this.selectedDifficulty = diff;
        this.gameState.reset();
        this.hubMap = new Map(10, 15, new HubGenerator(), diff);
        this.dungeonMap = new Map(20, 25, new DungeonGenerator(), diff);
        this.map = hubMap;
        this.myHero = chosenHero;
        this.heroPattern = new ImagePattern(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/com/example/rpg_gui/images/" + skinFileName))));
        this.myHero.setPosition(new Position(5, 5));
        GameManager.getInstance().setPlayer(myHero);
        AcademySystem.teachSkills(myHero);
        render();
    }
    @Override
    public void switchMap() {
        if (this.map == hubMap) {
            this.map = dungeonMap;
            myHero.setPosition(new Position(1, 1));
            System.out.println("you entered to dungeon");
        } else {
            this.map = hubMap;
            myHero.setPosition(new Position(1, 1));
            System.out.println("you returned to hub");
        }
    }

    public void handleInput(KeyCode code) {
        if (gameState.getCurrentState() != GameState.State.PLAYING) return;
        switch (code) {
            case W -> MovementSystem.move(myHero, map, 0, -1, this, uImanager);
            case S -> MovementSystem.move(myHero, map, 0, 1, this, uImanager);
            case A -> MovementSystem.move(myHero, map, -1, 0, this, uImanager);
            case D -> MovementSystem.move(myHero, map, 1, 0, this, uImanager);
            case F -> CombatSystem.tryAttack(myHero, map);
            case E -> InteractionSystem.interact(myHero, map, this, uImanager);
            case Q -> {myHero.switchAttack();System.out.println("Chosen attack is: "+myHero.getSelectedAttackName());}
            case KeyCode.X -> uImanager.showInventory (myHero);
        }
        for (var enemy : map.getEnemies()) {
            if (enemy.isAlive()) {
                enemy.chasingPlayer(myHero.getPosition(), map);
            }
        }
        render();
    }

    @Override
    public void render(){
        if (root.getScene() == null) {
            return;
        }
        Stage primaryStage = (Stage) root.getScene().getWindow();

        gameState.update(myHero, map, dungeonMap, uImanager, primaryStage);
        root.getChildren().clear();
        renderWorld();
        uImanager.drawHUD(uiPane, myHero, map, 800);
    }
    public Difficulty getSelectedDifficulty() {
        return this.selectedDifficulty;
    }

    public void renderWorld() {
        int tile_Size = 32;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Rectangle rect = new Rectangle(x * tile_Size, y * tile_Size, tile_Size, tile_Size);
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
                else if (type==TypeTile.BossRoomFloor) rect.setFill(bossFloorPattern);
                else rect.setFill(floorPattern);

                root.getChildren().add(rect);
            }
        }
        for (var enemy : map.getEnemies()) {
            if (enemy.isAlive()) {
                Rectangle enemyRect = new Rectangle(enemy.getPosition().getMyX() * tile_Size, enemy.getPosition().getMyY() * tile_Size, tile_Size, tile_Size);
                enemyRect.setFill(orcPattern);
                root.getChildren().add(enemyRect);
            }
        }
        Rectangle heroRect = new Rectangle(myHero.getPosition().getMyX() * tile_Size, myHero.getPosition().getMyY() * tile_Size, tile_Size, tile_Size);
        heroRect.setFill(heroPattern);
        root.getChildren().add(heroRect);
    }
}