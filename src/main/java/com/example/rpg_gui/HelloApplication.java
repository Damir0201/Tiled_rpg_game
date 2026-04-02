package com.example.rpg_gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.example.rpg_gui.core.GameEngine;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 640);
        GameEngine gameEngine = new GameEngine(root);
        gameEngine.initGame();
        scene.setOnKeyPressed(event -> {
            gameEngine.handleInput(event.getCode());
        });
        stage.setTitle("RPG Game");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}