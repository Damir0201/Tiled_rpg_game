package com.example.rpg_gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main_menu.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 740);

        stage.setTitle("RPG Game - Menu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}