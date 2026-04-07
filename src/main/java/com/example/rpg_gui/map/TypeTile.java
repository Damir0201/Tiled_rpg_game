package com.example.rpg_gui.map;

public enum TypeTile {
    Floor('.',false),
    Wall('#',true),
    Trap('T',false),
    Water('_',true),
    Grass('G',false),
    ChestTile('C',true),
    AcademyTile('A',true),
    ShopTile('S',true),
    DoorTile('D',true),
    PortalTile('P', false);

    private final char symbol;
    private final boolean collision;

    TypeTile(char symbol, boolean collision) {
        this.collision=collision;
        this.symbol=symbol;
    }
    public char getSymbol() {

        return symbol;
    }
    public boolean isCollision() {
        return collision;
    }
}
