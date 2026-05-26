package com.example.rpg_gui.map;

public class Tile {
    private TypeTile type;

    public Tile(TypeTile type) {
        this.type=type;
    }
    public TypeTile getType() {
        return type;
    }
    protected void setType(TypeTile type) {
        this.type=type;
    }
    public boolean isAllowingMove() {
        return !type.isCollision();
    }
}
