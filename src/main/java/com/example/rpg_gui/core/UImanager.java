package com.example.rpg_gui.core;
import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.map.Map;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UImanager {
    private Text healthText;
    private Text manaText;
    private Text enemyCount;
    private Rectangle healthBar;
    private Rectangle manaBar;

    public void drawHUD(Pane root, Hero hero, Map map) {
        if(healthText==null) setupUI();
        healthText.setText("Health: "+hero.getHealth() + " / " + hero.getMaxHealth());
        if(manaBar==null) setupUI();
        manaText.setText("Mana: "+hero.getMana() + " / " + hero.getMaxMana());
        var aliveCount = map.getEnemies().stream().filter(enemy -> enemy.isAlive()).count();
        enemyCount.setText("Enemies right now: "+aliveCount);
        double healthPercent = Math.max(0, (double) hero.getHealth()/hero.getMaxHealth());
        double manaPercent = Math.max(0, (double) hero.getMana()/hero.getMaxMana());

        healthBar.setWidth(150 * healthPercent);
        Rectangle uiBG = new Rectangle(5, 5, 200, 120);
        uiBG.setFill(Color.rgb(0, 0, 0, 0.6));
        uiBG.setArcWidth(10);
        uiBG.setArcHeight(10);
        Rectangle healthBG = new Rectangle(15, 35, 150, 12);
        healthBG.setFill(Color.GRAY);
        manaBar.setWidth(120*manaPercent);
        Rectangle manaBG=new Rectangle(14,67,120,12);
        manaBG.setFill(Color.GRAY);
        root.getChildren().addAll(uiBG, healthBG, healthBar, healthText, manaBG, manaBar, manaText, enemyCount);
    }
    private void setupUI() {
        healthText=new Text(15,28,"");
        healthText.setFill(Color.WHITE);
        healthText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        manaText=new Text(15,60,"");
        manaText.setFill((Color.WHITE));
        manaText.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        enemyCount=new Text(14,95,"");
        enemyCount.setFill(Color.GREY);
        enemyCount.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        manaBar=new Rectangle(14,67,120,12);
        manaBar.setFill(Color.BLUE);

        healthBar=new Rectangle(15,35,150,12);
        healthBar.setFill(Color.RED);
    }
}
