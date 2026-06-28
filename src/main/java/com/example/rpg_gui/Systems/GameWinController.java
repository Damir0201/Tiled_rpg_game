package com.example.rpg_gui.Systems;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class GameWinController {
    @FXML
    private Button startButton;
    private Stage mainStage;
    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    @FXML
    void handleStart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rpg_gui/main_menu.fxml"));
            Parent menuRoot = loader.load();
            Scene menuScene = new Scene(menuRoot, 800, 740);

            if (mainStage != null) {
                mainStage.setScene(menuScene);
                mainStage.setTitle("RPG Game - Menu");
            }

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}