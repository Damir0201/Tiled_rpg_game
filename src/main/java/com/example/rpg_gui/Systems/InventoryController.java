package com.example.rpg_gui.Systems;

import com.example.rpg_gui.Characters.Hero;
import com.example.rpg_gui.Items.Item;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class InventoryController {
    @FXML
    private ListView<String> inventoryListView;
    private Hero hero;

    public void initData(Hero hero) {
        this.hero = hero;
        refreshUI();
    }

    private void refreshUI() {
        inventoryListView.getItems().clear();

        for (Item item : hero.getInventory().getItems()) {
            inventoryListView.getItems().add(item.getItemName());
        }
    }

    @FXML
    private void handleUseItem() {
        int selectedIndex = inventoryListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            hero.getInventory().useItem(selectedIndex, hero);
            refreshUI();
        } else {
            System.out.println("Choose an item!");
        }
    }
}