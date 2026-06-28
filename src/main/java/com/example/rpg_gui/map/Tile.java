package com.example.rpg_gui.map;

public class Tile {
    private TypeTile type;

    public Tile(TypeTile type) {
        this.type=type;
    }
    public TypeTile getType() {
        return type;
    }
    private void setType(TypeTile type) {
        this.type=type;
    }
    public boolean isAllowingMove() {
        return !type.isCollision();
    }
    public boolean isSafeForGeneration() {
        return false;
    }
    public Interactable asInteractable() {
        return null;
    }
}
