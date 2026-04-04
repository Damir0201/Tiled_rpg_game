package com.example.rpg_gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.rpg_gui.core.GameEngine;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // VBox для вертикальной верстки
        VBox mainLayout = new VBox();
        Pane gamePane = new Pane();
        Pane uiPane = new Pane();
        mainLayout.getChildren().addAll(gamePane, uiPane);
        GameEngine gameEngine = new GameEngine(gamePane, uiPane);

        gameEngine.initGame();

        Scene scene = new Scene(mainLayout, 800, 740);
        scene.setOnKeyPressed(event -> gameEngine.handleInput(event.getCode()));

        stage.setTitle("RPG Game");
        stage.setScene(scene);
        stage.show();
    }
}