package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Items.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.ArrayList;

public class shopController {
    @FXML private ListView<String> shopListView;
    @FXML private Label moneyLabel;

    private Hero hero;
    private final ArrayList<Item> shopItems = new ArrayList<>();

    public void initData(Hero hero, ArrayList<Item> items) {
        this.hero = hero;
        this.shopItems.clear();
        this.shopItems.addAll(items);
        updateUI();
    }

    private void updateUI() {
        if (hero != null) {
            moneyLabel.setText("Balance: " + hero.getMoney());
        }

        ArrayList<String> names = new ArrayList<>();
        for (Item item : shopItems) {
            names.add(item.getItemName() + " (" + item.getItemPrice() + " coins)");
        }
        shopListView.setItems(FXCollections.observableArrayList(names));
    }

    @FXML
    private void handleBuy() {
        int index = shopListView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            boolean success = shopSystem.buyItem(hero, index);
            if (success) {
                updateUI();
            } else {
                System.out.println("Purchase failed!");
            }
        }
    }
}