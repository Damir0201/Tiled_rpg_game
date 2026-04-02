package com.example.rpg_gui.map;

public class Door extends Tile{
    private boolean isLocked=true;
    private final String lockcode;

    public Door(String lockcode) {
        super(TypeTile.DoorTile);
        this.lockcode=lockcode;
    }

    public String getLockcode() {
        return lockcode;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
