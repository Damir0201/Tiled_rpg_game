package com.example.rpg_gui.map;

public class Trap extends Tile {
    private final TypeTile type;

    public Trap(TypeTile type) {
        super(TypeTile.Trap);
        this.type=type;
    }
    public TypeTile getType() {
        return type;
    }
}
