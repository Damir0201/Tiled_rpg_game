package com.example.rpg_gui.core;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Systems.shopController;
import com.example.rpg_gui.Systems.shopSystem;
import com.example.rpg_gui.map.Map;
import com.example.rpg_gui.Systems.inventoryController;
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

    public void drawHUD(Pane root, Hero hero, Map map, double sceneWidth) {
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

        var aliveCount = map.getEnemies().stream().filter(enemy -> enemy.isAlive()).count();
        enemyCount.setText("Enemies: " + aliveCount);

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

        double rightPadding = 15;
        double weaponX = sceneWidth - 300;
        double armorX = weaponX;

        equippedWeaponText.setX(weaponX);
        equippedWeaponText.setY(30);
        equippedWeaponText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        equippedArmorText.setX(armorX);
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

            inventoryController controller = loader.getController();
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

            shopController controller = loader.getController();
            controller.initData(hero, shopSystem.getItemList());

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
}