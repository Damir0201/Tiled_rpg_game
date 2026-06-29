package com.example.rpg_gui.core;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Systems.*;
import com.example.rpg_gui.map.Map;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class UImanager {
    private Text healthText;
    private Text manaText;
    private Text enemyCount;
    private Text equippedWeaponText;
    private Text equippedArmorText;

    private Rectangle healthBar;
    private Rectangle manaBar;

    private boolean uiInitialized = false;

    public void drawHUD(Pane root, Hero hero, Map map, int sceneWidth) {
        if (!uiInitialized) {
            setupUI();

            Rectangle uiBG = new Rectangle(5, 5, sceneWidth - 10, 100);
            uiBG.setFill(Color.rgb(0, 0, 0, 0.6));
            uiBG.setArcWidth(10);
            uiBG.setArcHeight(10);

            Rectangle healthBG = new Rectangle(15, 35, 150, 12);
            healthBG.setFill(Color.GRAY);

            Rectangle manaBG = new Rectangle(15, 67, 120, 12);
            manaBG.setFill(Color.GRAY);

            root.getChildren().addAll(
                    uiBG,
                    healthBG, healthBar, healthText,
                    manaBG, manaBar, manaText,
                    enemyCount,
                    equippedWeaponText,
                    equippedArmorText
            );

            uiInitialized = true;
        }

        healthText.setText("Health: " + hero.getHealth() + " / " + hero.getMaxHealth());
        manaText.setText("Mana: " + hero.getMana() + " / " + hero.getMaxMana());

        enemyCount.setText("Enemies: " + map.aliveEnemies());

        double healthPercent = Math.max(0, (double) hero.getHealth() / hero.getMaxHealth());
        double manaPercent = Math.max(0, (double) hero.getMana() / hero.getMaxMana());
        healthBar.setWidth(150 * healthPercent);
        manaBar.setWidth(120 * manaPercent);

        healthText.setX(15);
        healthText.setY(28);

        manaText.setX(15);
        manaText.setY(60);

        enemyCount.setX(15);
        enemyCount.setY(90);

        int x = sceneWidth - 300;

        equippedWeaponText.setX(x);
        equippedWeaponText.setY(30);
        equippedWeaponText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        equippedArmorText.setX(x);
        equippedArmorText.setY(60);
        equippedArmorText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        if (hero.getEquippedWeapon() != null) {
            equippedWeaponText.setText("Weapon: " + hero.getEquippedWeapon().getItemName()
                    + " (+" + hero.getEquippedWeapon().getBonusDamage() + " damage)");
        } else {
            equippedWeaponText.setText("Weapon: None");
        }

        if (hero.getEquippedArmor() != null) {
            equippedArmorText.setText("Armor: " + hero.getEquippedArmor().getItemName()
                    + " (+" + hero.getEquippedArmor().getDefenceBonus() + " defence)");
        } else {
            equippedArmorText.setText("Armor: None");
        }
    }

    private void setupUI() {
        healthText = new Text(15, 28, "");
        healthText.setFill(Color.WHITE);
        healthText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        manaText = new Text(15, 60, "");
        manaText.setFill(Color.WHITE);
        manaText.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        enemyCount = new Text(14, 95, "");
        enemyCount.setFill(Color.GREY);
        enemyCount.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        equippedWeaponText = new Text(15, 120, "");
        equippedWeaponText.setFill(Color.WHITE);
        equippedWeaponText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        equippedArmorText = new Text(15, 140, "");
        equippedArmorText.setFill(Color.WHITE);
        equippedArmorText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        healthBar = new Rectangle(15, 35, 150, 12);
        healthBar.setFill(Color.RED);

        manaBar = new Rectangle(14, 67, 120, 12);
        manaBar.setFill(Color.BLUE);
    }

    public void showInventory(Hero hero) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rpg_gui/inventory.fxml"));
            Parent root = loader.load();

            InventoryController controller = loader.getController();
            controller.initData(hero);

            Stage stage = new Stage();
            stage.setTitle("Inventory - " + hero.getName());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: cannot find inventory.fxml!");
        }
    }

    public void showShop(Hero hero) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rpg_gui/shop.fxml"));
            Parent root = loader.load();

            ShopController controller = loader.getController();
            controller.initData(hero, ShopSystem.getItemList());

            Stage stage = new Stage();
            stage.setTitle("Shop");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: cannot find shop.fxml!");
        }
    }

    public void showGameOver(GameState state, Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rpg_gui/game_over.fxml"));
            Parent root = loader.load();
            GameOverController controller = loader.getController();
            controller.setMainStage(primaryStage);
            Stage stage = new Stage();
            stage.setTitle("Game Over");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: cannot find game_over.fxml!");
        }
    }

    public void showVictory(GameState state, Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/rpg_gui/game_win.fxml"));
            Parent root = loader.load();
            GameWinController controller = loader.getController();
            controller.setMainStage(primaryStage);
            Stage stage = new Stage();
            stage.setTitle("Victory!");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: cannot find game_win.fxml!");
        }
    }
}