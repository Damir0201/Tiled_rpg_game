package com.example.rpg_gui.Systems;

import com.example.rpg_gui.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class gameOverController {

    @FXML
    private Button restartButton;

    @FXML
    void handleRestart(ActionEvent event) {
        restartGame();
    }

    private void restartGame() {
        // restart the main menu
    }
}