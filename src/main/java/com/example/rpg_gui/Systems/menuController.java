package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.*;
import com.example.rpg_gui.core.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class menuController {
    @FXML private ToggleGroup heroGroup;
    @FXML private ToggleGroup skinGroup;
    @FXML private ToggleGroup modeGroup;

    @FXML
    private void handleStartGame() {
        ToggleButton selectedHeroBtn = (ToggleButton) heroGroup.getSelectedToggle();
        String heroType = (selectedHeroBtn != null) ? selectedHeroBtn.getText() : "Warrior";

        Hero player = switch (heroType) {
            case "Mage" -> new Mage();
            case "Archer" -> new Archer();
            default -> new Warrior();
        };

        ToggleButton selectedSkinBtn = (ToggleButton) skinGroup.getSelectedToggle();
        String skinName = (selectedSkinBtn != null) ? selectedSkinBtn.getText() : "Boy";
        String skinFile = skinName.equals("Girl") ? "HeroGirl.png" : "HeroBoy.png";

        ToggleButton selectedModeBtn = (ToggleButton) modeGroup.getSelectedToggle();
        String difficulty = (selectedModeBtn != null) ? selectedModeBtn.getText() : "Normal";

        System.out.println("Starting game with: " + heroType + ", Skin: " + skinName + ", Mode: " + difficulty);

        VBox mainLayout = new VBox();
        Pane gamePane = new Pane();
        Pane uiPane = new Pane();
        mainLayout.getChildren().addAll(gamePane, uiPane);


        GameEngine gameEngine = new GameEngine(gamePane, uiPane);
        gameEngine.initGame(player, skinFile);

        Stage stage = (Stage) ((javafx.scene.Node) skinGroup.getToggles().get(0)).getScene().getWindow();
        Scene gameScene = new Scene(mainLayout, 800, 740);


        gameScene.setOnKeyPressed(event -> gameEngine.handleInput(event.getCode()));

        stage.setScene(gameScene);
        stage.setTitle("RPG Game - Active Session");
    }
}